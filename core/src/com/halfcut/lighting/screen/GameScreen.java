package com.halfcut.lighting.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.halfcut.lighting.App;
import com.halfcut.lighting.assets.Assets;
import com.halfcut.lighting.lights.Light;
import com.halfcut.lighting.lights.LightSystem;
import com.halfcut.lighting.util.Palette;
import com.halfcut.lighting.util.Util;

import static com.halfcut.lighting.App.HEIGHT;
import static com.halfcut.lighting.App.WIDTH;

/**
 * @author halfcutdev
 * @since 22/12/2017
 */
public class GameScreen extends Screen {

    // Floating box example.
    private float boxSize  = 20;
    private float boxX     = (WIDTH - 20) / 2;
    private float boxY     = (HEIGHT - 20) / 2;
    private float boxTheta = 0.0f;

    private TextureRegion circle;
    private TextureRegion rectangle;

    private LightSystem lighting;
    private Light mouseLight;

    public GameScreen(App app) {
        super(app);
        circle    = Assets.get().getAtlas().findRegion("shape_circle");
        rectangle = Assets.get().getAtlas().findRegion("shape_rectangle");

        // Mouse light.
        lighting = new LightSystem();
        mouseLight = new Light(Gdx.input.getX(), Gdx.input.getY(), 20, 30, false);
        lighting.addLight(mouseLight);
    }

    @Override
    public void update(float delta) {
        // Rotate the box.
        boxTheta -= 1.5f * delta;

        // Update mouseLight's position
        Vector2 mousePos = Util.unproject(Gdx.input.getX(), Gdx.input.getY(), camera);
        mouseLight.setPosition(mousePos);
    }

    @Override
    public void draw(SpriteBatch sb, ShapeRenderer sr) {
        sb.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);

        sb.begin();
            // Draw background.
            sb.setColor(Palette.INK);
            sb.draw(rectangle, 0, 0, WIDTH, HEIGHT);

            // Draw box.
            sb.setColor(Palette.GLADE);
            sb.draw(rectangle, boxX, boxY, 0.5f * boxSize, 0.5f * boxSize, boxSize, boxSize, 1, 1, boxTheta);
        sb.end();

        lighting.update(camera);
    }

}
