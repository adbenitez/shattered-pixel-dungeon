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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.services.news.News;
import com.shatteredpixel.shatteredpixeldungeon.services.news.NewsImpl;
import com.shatteredpixel.shatteredpixeldungeon.services.updates.UpdateImpl;
import com.shatteredpixel.shatteredpixeldungeon.services.updates.Updates;
import com.watabou.noosa.Game;
import com.watabou.utils.FileUtils;

/**
 * GWT entry point for the HTML5 / browser build of Shattered Pixel Dungeon.
 *
 * This class is referenced by GdxDefinition.gwt.xml as the module entry-point
 * and is compiled to JavaScript by the GWT compiler.  The libGDX GWT backend
 * ({@code gdx-backend-gwt}) picks it up via the {@link GwtApplication} base
 * class which itself implements {@code com.google.gwt.core.client.EntryPoint}.
 */
public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Responsive: fill the browser window; the game already scales to fit.
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(true);
        config.padVertical = 0;
        config.padHorizontal = 0;
        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        // Populate the manual class registry used by the GWT Reflection
        // super-source (bypasses libGDX's IReflectionCache2Generated).
        ClassRegistry.registerAll();

        // Version info is not available at build time in the HTML build.
        Game.version = "HTML";
        Game.versionCode = 0;

        // HTML builds do not support automatic update checks or news.
        if (UpdateImpl.supportsUpdates()) {
            Updates.service = UpdateImpl.getUpdateService();
        }
        if (NewsImpl.supportsNews()) {
            News.service = NewsImpl.getNewsService();
        }

        // Game data is stored in browser Local Storage via libGDX's Local file type.
        FileUtils.setDefaultFileProperties(Files.FileType.Local, "");

        return new ShatteredPixelDungeon(new HtmlPlatformSupport());
    }
}
