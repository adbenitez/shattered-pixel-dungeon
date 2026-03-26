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

package com.shatteredpixel.shatteredpixeldungeon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.shatteredpixel.shatteredpixeldungeon.html.HtmlPlatformSupport;
import com.watabou.utils.FileUtils;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Use the full browser window; padding is handled by CSS
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(true);
        config.padVertical = 0;
        config.padHorizontal = 0;
        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        // Wrap ShatteredPixelDungeon so that we can initialise FileUtils
        // (which requires Gdx to be ready) before the rest of create() runs.
        return new ShatteredPixelDungeon(new HtmlPlatformSupport()) {
            @Override
            public void create() {
                // Map file I/O to the browser's localStorage (GWT Local type).
                // Saving is effectively disabled by the Bundle super-source,
                // but this prevents NPEs from a null defaultFileType.
                FileUtils.setDefaultFileProperties(Files.FileType.Local, "");
                super.create();
            }
        };
    }
}
