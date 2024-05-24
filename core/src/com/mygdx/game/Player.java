package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Constants.PPM;

public class Player extends GameEntity {

    Texture texture;
    Sprite sprite;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        texture = new Texture(Gdx.files.internal("playerImgTest.png"));
        sprite = new Sprite(texture);
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        // e5
        checkUserInput();
    }

    public void checkCoordinate(SpriteBatch batch){
        if (body.getPosition().x >= 106 && body.getPosition().x <= 108 && body.getPosition().y >= -13 && body.getPosition().y <= -12) {
            System.out.println("Player er på ting");
            BitmapFont font = new BitmapFont();
            font.draw(batch, "Klik Enter for at spille minigame!", 3445, -440);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){

            }

        }
    }
    @Override
    public void render(SpriteBatch batch) {
        // Tegn person og flyt den sammen med body
        float posX = body.getPosition().x * PPM;
        float posY = body.getPosition().y * PPM;
        float rotation = (float)Math.toDegrees(body.getAngle());
        // ved rendering flyttes både body og sprite
        sprite.setPosition(posX, posY);
        sprite.setRotation(rotation);
        sprite.draw(batch);
        checkCoordinate(batch);

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            System.out.println("Player coordinate: X: " + x + " Y: " + y);

        body.setLinearVelocity(velX * speed, velY * speed);

    }
}