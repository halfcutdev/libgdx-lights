package com.halfcut.lighting.lights;

import com.badlogic.gdx.math.Vector2;

public class Light {

    private Vector2 position;
    private float   innerRadius;
    private float   outerRadius;
    private boolean flickering;

    public Light(float x, float y, float innerRadius, float outerRadius, boolean flickering) {
        this.position    = new Vector2(x, y);
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.flickering  = flickering;
    }

    public float getInnerRadius() {
        return innerRadius;
    }

    public float getOuterRadius() {
        return outerRadius;
    }

    public void setInnerRadius(float innerRadius) {
        this.innerRadius = innerRadius;
    }

    public void setOuterRadius(float outerRadius) {
        this.outerRadius = outerRadius;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public boolean isFlickering() {
        return flickering;
    }

}
