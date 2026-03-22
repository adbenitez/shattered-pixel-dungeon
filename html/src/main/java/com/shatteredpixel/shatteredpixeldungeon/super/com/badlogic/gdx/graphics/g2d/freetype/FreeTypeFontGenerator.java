/*
 * GWT super-source: minimal FreeTypeFontGenerator stub for the HTML5 build.
 *
 * The libGDX FreeType extension (gdx-freetype) is a native extension and is
 * NOT available in the GWT / HTML5 build.  PlatformSupport.java references
 * FreeTypeFontGenerator in its API surface (abstract method return type and
 * static field type).  This stub satisfies the GWT compiler so the
 * PlatformSupport class can be compiled to JavaScript.
 *
 * At runtime none of these methods will ever be called from the HTML build:
 *   - HtmlPlatformSupport.getGeneratorForString() always returns null
 *   - HtmlPlatformSupport.getFont() is fully overridden and never calls the
 *     parent implementation that would use FreeTypeFontGenerator
 *   - HtmlPlatformSupport.setupFontGenerators() does not create any
 *     FreeTypeFontGenerator instances
 */
package com.badlogic.gdx.graphics.g2d.freetype;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Stub replacement for {@code com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator}
 * used only in the GWT / HTML5 build.
 */
public class FreeTypeFontGenerator implements Disposable {

    // -----------------------------------------------------------------------
    // Inner types required by PlatformSupport.getFont()
    // -----------------------------------------------------------------------

    public enum Hinting {
        None, Slight, Medium, Full, AutoSlight, AutoMedium, AutoFull
    }

    public static class FreeTypeFontParameter {
        public int size = 16;
        public boolean flip = false;
        public float borderWidth = 0f;
        public int renderCount = 2;
        public Hinting hinting = Hinting.AutoMedium;
        public int spaceX = 0;
        public boolean incremental = false;
        public String characters = "";
        public Object packer = null; // PixmapPacker – not needed in stub
    }

    // -----------------------------------------------------------------------
    // Constructor (FileHandle variant used by DesktopPlatformSupport)
    // -----------------------------------------------------------------------

    public FreeTypeFontGenerator(FileHandle fontFile) {
        // No-op in the HTML build – this constructor will never be called.
    }

    // -----------------------------------------------------------------------
    // generateFont – returns a dummy BitmapFont; never called in HTML build
    // -----------------------------------------------------------------------

    public BitmapFont generateFont(FreeTypeFontParameter parameter) {
        return new BitmapFont();
    }

    // -----------------------------------------------------------------------
    // Disposable
    // -----------------------------------------------------------------------

    @Override
    public void dispose() {
        // No native resources to release.
    }
}
