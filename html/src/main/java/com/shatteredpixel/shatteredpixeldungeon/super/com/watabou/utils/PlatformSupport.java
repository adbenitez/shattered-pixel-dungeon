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

package com.watabou.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.watabou.input.ControllerHandler;
import com.watabou.noosa.Game;

/**
 * GWT super-source for PlatformSupport.
 * Removes FreeTypeFontGenerator dependency which is not GWT-compatible.
 * Font generation is handled by the HTML-specific implementation.
 */
public abstract class PlatformSupport {

	public abstract void updateDisplaySize();

	public boolean supportsFullScreen() {
		return true;
	}

	public static final int INSET_ALL = 3;
	public static final int INSET_LRG = 2;
	public static final int INSET_BLK = 1;

	public RectF getSafeInsets(int level) {
		return new RectF(
				Gdx.graphics.getSafeInsetLeft(),
				Gdx.graphics.getSafeInsetTop(),
				Gdx.graphics.getSafeInsetRight(),
				Gdx.graphics.getSafeInsetBottom()
		);
	}

	public RectF getDisplayCutout() {
		return new RectF();
	}

	public abstract void updateSystemUI();

	public abstract boolean connectedToUnmeteredNetwork();

	public abstract boolean supportsVibration();

	public void vibrate(int millis) {
		if (ControllerHandler.isControllerConnected()) {
			ControllerHandler.vibrate(millis);
		} else {
			Gdx.input.vibrate(millis);
		}
	}

	public void setHonorSilentSwitch(boolean value) {
	}

	public boolean openURI(String uri) {
		return Gdx.net.openURI(uri);
	}

	public void setOnscreenKeyboardVisible(boolean value, boolean multiline) {
		Gdx.input.setOnscreenKeyboardVisible(value, Input.OnscreenKeyboardType.Default);
	}

	// Font support - FreeType replaced with BitmapFont for GWT compatibility

	protected int pageSize;
	protected boolean systemfont;

	public abstract void setupFontGenerators(int pageSize, boolean systemFont);

	protected abstract BitmapFont getGeneratorForString(String input);

	public abstract String[] splitforTextBlock(String text, boolean multiline);

	public void resetGenerators() {
		resetGenerators(true);
	}

	public void resetGenerators(boolean setupAfter) {
		if (setupAfter) setupFontGenerators(pageSize, systemfont);
	}

	public void reloadGenerators() {
	}

	public BitmapFont getFont(int size, String text, boolean flipped, boolean border) {
		return null;
	}
}
