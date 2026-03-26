/*
 * GWT super-source stub for com.badlogic.gdx.controllers.Controller.
 * Controllers are not supported in the HTML build.
 */
package com.badlogic.gdx.controllers;

public interface Controller {
    String getName();
    boolean canVibrate();
    boolean isVibrating();
    void startVibration(int duration, float strength);
    void cancelVibration();
    ControllerMapping getMapping();
    boolean getButton(int buttonCode);
    float getAxis(int axisCode);
    ControllerListener getListener();
    void addListener(ControllerListener listener);
    void removeListener(ControllerListener listener);
    boolean supportsPlayerIndex();
    int getPlayerIndex();
    void setPlayerIndex(int index);
    int getMinButtonIndex();
    int getMaxButtonIndex();
    int getAxisCount();
}
