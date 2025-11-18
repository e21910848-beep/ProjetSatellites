package models;

import views.Controller;

public class OceanBounds {
    public int left;
    public int right;
    public int top;        // Top of ocean (where surface starts)
    public int bottom;     // Bottom of ocean
    public int surfaceY;   // Y coordinate of surface line
    public Controller controller;

    public OceanBounds(int left, int right, int top, int bottom, int surfaceY, Controller controller) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.surfaceY = surfaceY;
        this.controller = controller;
    }
}
