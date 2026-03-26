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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.watabou.utils.PlatformSupport;

import java.util.HashMap;

public class HtmlPlatformSupport extends PlatformSupport {

	@Override
	public void updateDisplaySize() {
		// no-op for HTML
	}

	@Override
	public boolean supportsFullScreen() {
		return true;
	}

	@Override
	public void updateSystemUI() {
		// no-op for HTML
	}

	@Override
	public boolean connectedToUnmeteredNetwork() {
		return true;
	}

	@Override
	public boolean supportsVibration() {
		return false;
	}

	/* FONT SUPPORT */

	private static HashMap<Integer, BitmapFont> htmlFonts;

	@Override
	public void setupFontGenerators(int pageSize, boolean systemfont) {
		if (htmlFonts != null && this.pageSize == pageSize && this.systemfont == systemfont) {
			return;
		}
		this.pageSize = pageSize;
		this.systemfont = systemfont;
		resetGenerators(false);
		htmlFonts = new HashMap<>();
	}

	@Override
	protected BitmapFont getGeneratorForString(String input) {
		return null;
	}

	@Override
	public BitmapFont getFont(int size, String text, boolean flipped, boolean border) {
		if (htmlFonts == null) {
			setupFontGenerators(512, false);
		}
		int key = flipped ? -size : size;
		if (!htmlFonts.containsKey(key)) {
			BitmapFont font = new BitmapFont();
			font.getData().setScale(size / 15f, flipped ? size / 15f : -(size / 15f));
			htmlFonts.put(key, font);
		}
		return htmlFonts.get(key);
	}

	@Override
	public void resetGenerators(boolean setupAfter) {
		if (htmlFonts != null) {
			for (BitmapFont f : htmlFonts.values()) {
				f.dispose();
			}
			htmlFonts.clear();
			htmlFonts = null;
		}
		if (setupAfter) setupFontGenerators(pageSize, systemfont);
	}

	@Override
	public void reloadGenerators() {
		// no-op for HTML
	}

	private static final String SPLIT_PATTERN =
			"(?<=\n)|(?=\n)|(?<=_)|(?=_)|(?<=\\*\\*)|(?=\\*\\*)";

	private static final String SPLIT_PATTERN_MULTILINE =
			"(?<= )|(?= )|(?<=\n)|(?=\n)|(?<=_)|(?=_)|(?<=\\*\\*)|(?=\\*\\*)";

	@Override
	public String[] splitforTextBlock(String text, boolean multiline) {
		if (multiline) {
			return text.split(SPLIT_PATTERN_MULTILINE);
		} else {
			return text.split(SPLIT_PATTERN);
		}
	}
}
