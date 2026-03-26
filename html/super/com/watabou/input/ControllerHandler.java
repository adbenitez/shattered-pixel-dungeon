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

/*
 * GWT super-source: replaces ControllerHandler for the HTML build.
 * gdx-controllers does not have a working GWT/HTML5 backend for version 2.2.x,
 * so all controller functionality is disabled.  The class still implements
 * ControllerListener so that Game.java's Controllers.addListener() call
 * compiles without error (controllers are never actually enabled because
 * controllersSupported() always returns false).
 */

package com.watabou.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.watabou.utils.PointF;

public class ControllerHandler implements ControllerListener {

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

    public static boolean controllersSupported() {
        return false;
    }

    public static boolean vibrationSupported() {
        return false;
    }

    public static void vibrate( int millis ) {
        // no-op
    }

    public static boolean isControllerConnected() {
        return false;
    }

    // ControllerListener methods – no-ops
    @Override public void connected(Controller controller) {}
    @Override public void disconnected(Controller controller) {}
    @Override public boolean buttonDown(Controller controller, int buttonCode) { return false; }
    @Override public boolean buttonUp(Controller controller, int buttonCode) { return false; }
    @Override public boolean axisMoved(Controller controller, int axisCode, float value) { return false; }

    private static boolean controllerPointerActive = false;
    private static PointF controllerPointerPos = new PointF();

    public static PointF leftStickPosition  = new PointF();
    public static PointF rightStickPosition = new PointF();

    public static void setControllerPointer( boolean active ) {
        controllerPointerActive = active;
    }

    public static boolean controllerPointerActive() {
        return false;
    }

    public static PointF getControllerPointerPos() {
        return controllerPointerPos.clone();
    }

    public static void updateControllerPointer( PointF pos, boolean sendEvent ) {
        // no-op
    }

    public static int buttonToKey( Controller controller, int btnCode ) {
        return 0; // Input.Keys.UNKNOWN
    }

    public static boolean icControllerKey( int keyCode ) {
        return false;
    }

    public static String customButtonName( int keyCode ) {
        return null;
    }
}
