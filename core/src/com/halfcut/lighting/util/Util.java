package com.halfcut.lighting.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * @author halfcutdev
 * @since 07/09/2017
 */
public class Util {

    static public Vector2 unproject(float x, float y, OrthographicCamera camera) {
        Vector3 world = camera.unproject(new Vector3(x, y, 0));
        return new Vector2(world.x, world.y);
    }

    static public Vector2 project(float x, float y, OrthographicCamera camera) {
        Vector3 screen = camera.project(new Vector3(x, y, 0));
        return new Vector2(screen.x, screen.y);
    }

}
