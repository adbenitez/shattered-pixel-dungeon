/*
 * GWT super-source stub for com.badlogic.gdx.controllers.ControllerListener.
 * Controllers are not supported in the HTML build.
 */
package com.badlogic.gdx.controllers;

public interface ControllerListener {
    void connected(Controller controller);
    void disconnected(Controller controller);
    boolean buttonDown(Controller controller, int buttonCode);
    boolean buttonUp(Controller controller, int buttonCode);
    boolean axisMoved(Controller controller, int axisCode, float value);
}
