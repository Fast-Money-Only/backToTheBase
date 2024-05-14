package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.Constants.PPM;
import static com.mygdx.game.Constants.map0;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    // e2
    private IsometricTiledMapRenderer isometricTiledMapRenderer;
    private IsoMapHelper isoMapHelper; // e2

    // e4
    // game objects
    private Player player;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false);
        // e4 i stedet for linjen ovenfor
        //this.world = new World(new Vector2(0,-9.81f), false);
        // e5 linje nedenfor
        //this.world = new World(new Vector2(0,-25f), false);         // e5 så hoppes der bedre
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.isoMapHelper = new IsoMapHelper(this);  // e2, e3: parameter
        this.isometricTiledMapRenderer = isoMapHelper.setupMap(map0); // e2

    }

    private void update() {
        world.step(1/60f,6,2);

        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        isometricTiledMapRenderer.setView(camera); // e2
        player.update(); // e5

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        // e4
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
        position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        camera.position.set(position);
        // slet gml camera.position i næste linje
        // e4 slut
        //camera.position.set(new Vector3(0,0,0));  // e1
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0,0,0.3f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        isometricTiledMapRenderer.render(); // e2

        batch.begin();

        batch.end();
        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

    }

    // e3
    public World getWorld() {
        return world;
    }

    // e4
    public void setPlayer(Player player) {
        this.player = player;
    }

}
