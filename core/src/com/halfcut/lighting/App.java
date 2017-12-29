package com.halfcut.lighting;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.halfcut.lighting.screen.LoadingScreen;

import static com.halfcut.lighting.lights.LightSystem.AMBIENT_COLOUR;
import static com.halfcut.lighting.lights.LightSystem.AMBIENT_INTENSITY;

public class App extends Game {

	static final public String TITLE = "libGDX lighting";
	static final public int WIDTH  = 200;
	static final public int HEIGHT = 150;
	static final public int SCALE  = 2;
	static public boolean DEV_MODE;

	public enum Mode { DESKTOP, HTML }
	static public Mode mode;

	public SpriteBatch   sb;
	public ShapeRenderer sr;

	public SpriteBatch   sbHUD;
	public ShapeRenderer srHUD;

	private FrameBuffer fbo;
	private SpriteBatch sbFBO;

	// Lighting shader.
	private ShaderProgram shader;

	@Override
	public void create () {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);

		sbHUD = new SpriteBatch();
		srHUD = new ShapeRenderer();
		sr.setAutoShapeType(true);

		initFBO();
		initShaders();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
	    sb.setShader(shader);

		// Draw the game to the frame buffer.
        fbo.begin();
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			super.render();
		fbo.end();
        fbo.getColorBufferTexture().bind(0);

		// Render the frame buffer to the screen.
		float x = (Gdx.graphics.getWidth()  - fbo.getWidth()  * SCALE) / 2;
		float y = (Gdx.graphics.getHeight() - fbo.getHeight() * SCALE) / 2;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sbFBO.begin();
			sbFBO.draw(fbo.getColorBufferTexture(), x, y, WIDTH * SCALE, HEIGHT * SCALE, 0, 0, 1f, 1f);
		sbFBO.end();
	}

	@Override
	public void dispose () {
		sb.dispose();
		sr.dispose();
		fbo.dispose();
		sbFBO.dispose();
		shader.dispose();
	}

	private void initFBO() {
		if(fbo != null) fbo.dispose();
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, WIDTH, HEIGHT, true);
		fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		if(sbFBO != null) sbFBO.dispose();
		sbFBO = new SpriteBatch();
	}

	private void initShaders() {
        ShaderProgram.pedantic = false;
        String vert = Gdx.files.internal("shaders/vert.glsl").readString();
        String frag = Gdx.files.internal("shaders/frag.glsl").readString();
        shader = new ShaderProgram(vert, frag);

         // Check shader compiled.
        if (!shader.isCompiled())
            throw new GdxRuntimeException("Could not compile shader: "+shader.getLog());
        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());

        shader.begin();
            shader.setUniformf("u_ambientColor", AMBIENT_COLOUR.x, AMBIENT_COLOUR.y, AMBIENT_COLOUR.z, AMBIENT_INTENSITY);
            shader.setUniformf("u_resolution", WIDTH, HEIGHT);
            shader.setUniformi("u_lightmap", 1);
        shader.end();
    }

}
