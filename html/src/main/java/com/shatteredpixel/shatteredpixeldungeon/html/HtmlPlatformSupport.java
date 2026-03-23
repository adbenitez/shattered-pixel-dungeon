/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.html;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.watabou.noosa.Game;
import com.watabou.utils.PlatformSupport;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * HTML5 / GWT implementation of {@link PlatformSupport}.
 *
 * <h3>Font handling</h3>
 * The libGDX {@code gdx-freetype} extension is <em>not</em> available in the
 * GWT build because it relies on native C code.  Instead, this class loads
 * pre-rendered bitmap fonts from {@code .fnt} / {@code .png} file pairs
 * stored in the assets directory.  The expected files are:
 * <ul>
 *   <li>{@code fonts/pixel_font_<size>.fnt} and matching {@code .png}</li>
 *   <li>{@code fonts/droid_sans_<size>.fnt} and matching {@code .png}</li>
 * </ul>
 * If a pre-rendered file for a requested size is not present the nearest
 * available size is scaled to fit (libGDX BitmapFont supports integer scaling).
 * As a last resort the built-in libGDX font is returned so the game is still
 * playable even without pre-rendered assets.
 *
 * <p>Pre-rendered font files can be generated from the TTF sources using
 * libGDX Hiero or the {@code gdx-tools} bitmap-font generator.  The
 * recommended sizes to pre-render are: 6, 7, 8, 9, 10, 12, 14, 16, 18, 20.
 */
public class HtmlPlatformSupport extends PlatformSupport {

    // -----------------------------------------------------------------------
    // Sizes pre-rendered and bundled with the HTML build.
    // Add entries here as you generate more .fnt files.
    private static final int[] PRERENDERED_SIZES = { 6, 7, 8, 9, 10, 12, 14, 16, 18, 20 };

    // Cache: size -> BitmapFont (pixel font, flipped)
    private static final HashMap<Integer, BitmapFont> pixelFontsFlipped   = new HashMap<>();
    // Cache: size -> BitmapFont (pixel font, upright)
    private static final HashMap<Integer, BitmapFont> pixelFontsUpright   = new HashMap<>();
    // Cache: size -> BitmapFont (droid-sans, flipped)
    private static final HashMap<Integer, BitmapFont> asianFontsFlipped   = new HashMap<>();
    // Cache: size -> BitmapFont (droid-sans, upright)
    private static final HashMap<Integer, BitmapFont> asianFontsUpright   = new HashMap<>();

    // Fallback: libGDX built-in font (always available in GWT)
    private static BitmapFont fallbackFont;

    // -----------------------------------------------------------------------
    @Override
    public void updateDisplaySize() {
        // In a browser the canvas already fills the page; just signal a scene
        // reset so the game re-layouts to the new viewport dimensions.
        com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon.seamlessResetScene();
    }

    @Override
    public boolean supportsFullScreen() {
        return Gdx.graphics.supportsDisplayModeChange();
    }

    @Override
    public void updateSystemUI() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (SPDSettings.fullscreen()) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    Gdx.graphics.setWindowedMode(Game.width, Game.height);
                }
            }
        });
    }

    @Override
    public boolean connectedToUnmeteredNetwork() {
        // No reliable way to check metered vs. unmetered in a browser context.
        return true;
    }

    @Override
    public boolean supportsVibration() {
        return Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator);
    }

    // -----------------------------------------------------------------------
    // Font support
    // -----------------------------------------------------------------------

    @Override
    public void setupFontGenerators(int pageSize, boolean systemfont) {
        if (fonts != null && this.pageSize == pageSize && this.systemfont == systemfont) {
            return;
        }
        this.pageSize  = pageSize;
        this.systemfont = systemfont;

        // We do not use FreeTypeFontGenerator in the HTML build, so we only
        // reset the cache and pre-load the bitmap fonts.
        disposeFontCache();

        // fonts map is keyed by FreeTypeFontGenerator in the base class, but
        // in the HTML build we never use that map.  We keep it non-null so
        // that the base class getFont() won't NPE if it is ever called
        // through a path we haven't overridden.
        fonts = new HashMap<>();
    }

    /** Disposes all cached BitmapFont instances. */
    private void disposeFontCache() {
        disposeFontMap(pixelFontsFlipped);
        disposeFontMap(pixelFontsUpright);
        disposeFontMap(asianFontsFlipped);
        disposeFontMap(asianFontsUpright);
        if (fallbackFont != null) {
            fallbackFont.dispose();
            fallbackFont = null;
        }
    }

    private static void disposeFontMap(HashMap<Integer, BitmapFont> map) {
        for (BitmapFont f : map.values()) {
            f.dispose();
        }
        map.clear();
    }

    /**
     * Returns the best available BitmapFont for the requested size.
     *
     * <p>The lookup strategy is:
     * <ol>
     *   <li>Return from cache if already loaded.</li>
     *   <li>Try to load a pre-rendered .fnt file at the exact size.</li>
     *   <li>Return the built-in libGDX font as a last resort.</li>
     * </ol>
     */
    @Override
    public BitmapFont getFont(int size, String text, boolean flipped, boolean border) {
        boolean useAsian = isAsianText(text);
        HashMap<Integer, BitmapFont> cache = useAsian
                ? (flipped ? asianFontsFlipped  : asianFontsUpright)
                : (flipped ? pixelFontsFlipped  : pixelFontsUpright);

        if (cache.containsKey(size)) {
            return cache.get(size);
        }

        String baseName = useAsian ? "droid_sans" : "pixel_font";
        BitmapFont font = loadBitmapFont(baseName, size, flipped);
        cache.put(size, font);
        return font;
    }

    /**
     * Attempts to load a pre-rendered bitmap font at the exact size from the
     * assets directory ({@code fonts/<baseName>_<size>.fnt}).  Falls back to
     * the nearest bundled size, and ultimately to the built-in libGDX font.
     */
    private BitmapFont loadBitmapFont(String baseName, int size, boolean flipped) {
        // Try exact size first
        String path = "fonts/" + baseName + "_" + size + ".fnt";
        if (Gdx.files.internal(path).exists()) {
            BitmapFont font = new BitmapFont(Gdx.files.internal(path), flipped);
            font.getData().markupEnabled = false;
            return font;
        }

        // Try the nearest pre-rendered size
        int best = findNearestPrerenderedSize(size);
        path = "fonts/" + baseName + "_" + best + ".fnt";
        if (Gdx.files.internal(path).exists()) {
            BitmapFont font = new BitmapFont(Gdx.files.internal(path), flipped);
            font.getData().markupEnabled = false;
            // Scale to the requested size
            float scale = (float) size / best;
            font.getData().setScale(scale);
            return font;
        }

        // Fallback to the built-in libGDX font
        return getFallbackFont(flipped);
    }

    private BitmapFont getFallbackFont(boolean flipped) {
        if (fallbackFont == null) {
            fallbackFont = new BitmapFont(flipped);
            fallbackFont.getData().markupEnabled = false;
        }
        return fallbackFont;
    }

    private static int findNearestPrerenderedSize(int target) {
        int best = PRERENDERED_SIZES[0];
        int bestDelta = Math.abs(target - best);
        for (int s : PRERENDERED_SIZES) {
            int delta = Math.abs(target - s);
            if (delta < bestDelta) {
                bestDelta = delta;
                best = s;
            }
        }
        return best;
    }

    // -----------------------------------------------------------------------
    // FreeTypeFontGenerator stubs (not used in the HTML build but required
    // to satisfy the abstract contract of PlatformSupport).
    // -----------------------------------------------------------------------

    @Override
    protected FreeTypeFontGenerator getGeneratorForString(String input) {
        // Not called – getFont() is fully overridden above.
        return null;
    }

    // -----------------------------------------------------------------------
    // Text splitting (same logic as DesktopPlatformSupport)
    // -----------------------------------------------------------------------

    // Stored as strings so we can use String.split() – GWT's Pattern does not
    // expose Pattern.split(String) and Matcher does not have reset(String).
    private static final Pattern asianPattern = Pattern.compile(
            "\\p{InHangul_Syllables}|" +
            "\\p{InCJK_Unified_Ideographs}|\\p{InCJK_Symbols_and_Punctuation}|" +
            "\\p{InHalfwidth_and_Fullwidth_Forms}|" +
            "\\p{InHiragana}|\\p{InKatakana}");

    private static final String regularSplitterRegex =
            "(?<=\n)|(?=\n)|(?<=_)|(?=_)|(?<=\\*\\*)|(?=\\*\\*)|" +
            "(?<=\\p{InHiragana})|(?=\\p{InHiragana})|" +
            "(?<=\\p{InKatakana})|(?=\\p{InKatakana})|" +
            "(?<=\\p{InCJK_Unified_Ideographs})|(?=\\p{InCJK_Unified_Ideographs})|" +
            "(?<=\\p{InCJK_Symbols_and_Punctuation})|(?=\\p{InCJK_Symbols_and_Punctuation})";

    private static final String regularSplitterMultilineRegex =
            "(?<= )|(?= )|(?<=\n)|(?=\n)|(?<=_)|(?=_)|(?<=\\*\\*)|(?=\\*\\*)|" +
            "(?<=\\p{InHiragana})|(?=\\p{InHiragana})|" +
            "(?<=\\p{InKatakana})|(?=\\p{InKatakana})|" +
            "(?<=\\p{InCJK_Unified_Ideographs})|(?=\\p{InCJK_Unified_Ideographs})|" +
            "(?<=\\p{InCJK_Symbols_and_Punctuation})|(?=\\p{InCJK_Symbols_and_Punctuation})";

    private boolean isAsianText(String text) {
        return text != null && asianPattern.matcher(text).find();
    }

    @Override
    public String[] splitforTextBlock(String text, boolean multiline) {
        if (multiline) {
            return text.split(regularSplitterMultilineRegex);
        } else {
            return text.split(regularSplitterRegex);
        }
    }
}
