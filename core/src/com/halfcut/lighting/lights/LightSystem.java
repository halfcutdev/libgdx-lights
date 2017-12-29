package com.halfcut.lighting.lights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.halfcut.lighting.App;

import java.util.ArrayList;
import java.util.List;

public class LightSystem {

    static final public float   AMBIENT_INTENSITY = 0.5f;
    static final public Vector3 AMBIENT_COLOUR    = new Vector3(0.5f, 0.5f, 0.5f);

    private ShapeRenderer sr  = new ShapeRenderer();
    private FrameBuffer   fbo = new FrameBuffer(Pixmap.Format.RGBA8888, App.WIDTH, App.HEIGHT, false);
    private List<Light>   lights = new ArrayList<Light>();

    public void update(OrthographicCamera camera) {
        fbo.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Filled);

                // Draw outer radius.
                sr.setColor(Color.GRAY);
                for(Light light : lights) {
                    sr.circle(light.getX(), light.getY(), light.getOuterRadius());
                }
                // Draw inner radius.
                sr.setColor(Color.WHITE);
                for(Light light : lights) {
                    sr.circle(light.getX(), light.getY(), light.getInnerRadius());
                }

            sr.end();
        fbo.end();

        getLightMap().getColorBufferTexture().bind(1);
    }

    public void addLight(Light light) {
        this.lights.add(light);
    }

    public void removeLight(Light light) {
        this.lights.remove(light);
    }

    public FrameBuffer getLightMap() {
        return fbo;
    }

    public void dispose() {
        fbo.dispose();
    }

}
