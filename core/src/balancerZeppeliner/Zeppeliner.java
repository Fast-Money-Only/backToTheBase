package balancerZeppeliner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Zeppeliner extends Rectangle {

    private Texture texture;
    private float x, y;
    private float rotation;


    private Sprite sprite;

    public Zeppeliner(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        this.sprite.setOriginCenter(); // Set the origin for rotation to the center of the sprite
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public double getX() {
        return sprite.getX();
    }

    public double getY() {
        return sprite.getY();
    }

    public float getRotation() {
        return sprite.getRotation();
    }

    public void setRotation(float rotation) {
        sprite.setRotation(rotation);
    }

    public void rotate(float angle) {
        sprite.rotate(angle);
    }

    public void resetRotation() {
        sprite.setRotation(0);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
