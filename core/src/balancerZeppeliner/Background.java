package balancerZeppeliner;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Background extends Rectangle {

    private Texture texture;
    private float x,y;

    private double width, height;

    public Background(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        setSize(texture.getWidth(), texture.getHeight()); // Set size based on texture dimensions
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }


    public void setX(float x) {
        this.x = x;
    }


    public void setY(float y) {
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
