package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Constants.PPM;

public class Player extends GameEntity {


    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        // e5
        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    // e5
    private void checkUserInput() {
        velX = 0;
        velY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            velX += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            velX -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            velY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velY -= 1;


        body.setLinearVelocity(velX * speed, velY * speed);

    }
}