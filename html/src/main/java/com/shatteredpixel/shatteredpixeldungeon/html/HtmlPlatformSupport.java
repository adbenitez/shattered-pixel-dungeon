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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.watabou.utils.PlatformSupport;

import java.util.HashMap;
import java.util.regex.Pattern;

public class HtmlPlatformSupport extends PlatformSupport {

    @Override
    public void updateDisplaySize() {
        // Handled automatically by the GWT/browser backend
    }

    @Override
    public void updateSystemUI() {
        // No-op for HTML; fullscreen is managed via browser APIs
    }

    @Override
    public boolean connectedToUnmeteredNetwork() {
        // Assume the user is happy to fetch data in a browser context
        return true;
    }

    @Override
    public boolean supportsVibration() {
        return false;
    }

    // Font support - only the pixel font is used in the HTML build
    private static FreeTypeFontGenerator pixelFontGenerator;

    @Override
    public void setupFontGenerators(int pageSize, boolean systemfont) {
        if (fonts != null && this.pageSize == pageSize && this.systemfont == systemfont) {
            return;
        }
        this.pageSize = pageSize;
        this.systemfont = systemfont;

        resetGenerators(false);
        fonts = new HashMap<>();

        pixelFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel_font.ttf"));
        fonts.put(pixelFontGenerator, new HashMap<>());

        packer = new PixmapPacker(pageSize, pageSize, Pixmap.Format.RGBA8888, 1, false);
    }

    @Override
    protected FreeTypeFontGenerator getGeneratorForString(String input) {
        return pixelFontGenerator;
    }

    // Splitters do not need CJK ranges since the HTML build only ships the pixel font
    private static final Pattern splitter = Pattern.compile(
            "(?<=\n)|(?=\n)|(?<=_)|(?=_)|(?<=\\*\\*)|(?=\\*\\*)");

    private static final Pattern splitterMultiline = Pattern.compile(
            "(?<= )|(?= )|(?<=\n)|(?=\n)|(?<=_)|(?=_)|(?<=\\*\\*)|(?=\\*\\*)");

    @Override
    public String[] splitforTextBlock(String text, boolean multiline) {
        if (multiline) {
            return splitterMultiline.split(text);
        } else {
            return splitter.split(text);
        }
    }
}
