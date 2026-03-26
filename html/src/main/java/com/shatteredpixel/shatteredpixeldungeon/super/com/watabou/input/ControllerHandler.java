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

package com.watabou.input;

import com.badlogic.gdx.Input;
import com.watabou.utils.PointF;

/**
 * GWT super-source stub for ControllerHandler.
 * Controllers are not supported in HTML builds.
 */
public class ControllerHandler {

	public enum ControllerType {
		XBOX,
		PLAYSTATION,
		NINTENDO,
		OTHER
	}

	public static ControllerType lastUsedType = ControllerType.OTHER;
	public static boolean controllerActive = false;

	public static final int CONTROLLER_POINTER_ID = 19;
	public static int DPAD_KEY_OFFSET = 1000;

	public static PointF leftStickPosition = new PointF();
	public static PointF rightStickPosition = new PointF();

	public static boolean controllersSupported() {
		return false;
	}

	public static boolean vibrationSupported() {
		return false;
	}

	public static void vibrate(int millis) {
	}

	public static boolean isControllerConnected() {
		return false;
	}

	private static boolean controllerPointerActive = false;
	private static PointF controllerPointerPos = new PointF();

	public static void setControllerPointer(boolean active) {
	}

	public static boolean controllerPointerActive() {
		return false;
	}

	public static PointF getControllerPointerPos() {
		return controllerPointerPos.clone();
	}

	public static void updateControllerPointer(PointF pos, boolean sendEvent) {
	}

	public static boolean icControllerKey(int keyCode) {
		if (keyCode == 0) {
			return true;
		}
		if (keyCode >= Input.Keys.BUTTON_A && keyCode <= Input.Keys.BUTTON_MODE) {
			return true;
		}
		if (keyCode >= Input.Keys.DPAD_UP + DPAD_KEY_OFFSET
				&& keyCode <= Input.Keys.DPAD_RIGHT + DPAD_KEY_OFFSET) {
			return true;
		}
		return false;
	}

	public static int buttonToKey(Object controller, int btnCode) {
		return Input.Keys.UNKNOWN;
	}

	public static String customButtonName(int keyCode) {
		return null;
	}
}
