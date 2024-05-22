package balancerZeppeliner;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Weight extends Rectangle {

    private int vægt;
    private Texture texture;
    private float x,y;

    private double width, height;

    public Weight(int vægt, Texture texture, float x, float y) {
        this.vægt = vægt;
        this.texture = texture;
        this.x = x;
        this.y = y;
        setSize(texture.getWidth(), texture.getHeight());
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

    public int getVægt() {
        return vægt;
    }

    public void setVægt(int vægt) {
        this.vægt = vægt;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean overlaps(Zeppeliner zeppeliner) {
        return this.overlaps(zeppeliner);
    }
}
