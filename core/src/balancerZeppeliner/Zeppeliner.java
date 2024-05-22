package balancerZeppeliner;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Zeppeliner extends Rectangle {

        private Texture texture;
        private float x, y;
        private float rotation;

        public Zeppeliner(Texture texture, float x, float y) {
            this.texture = texture;
            this.x = x;
            this.y = y;
            this.rotation = 0;
        }

        public Texture getTexture() {
            return texture;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public float getRotation() {
            return rotation;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        public void rotate(float angle) {
            this.rotation += angle;
        }

        public void resetRotation() {
            this.rotation = 0;
        }

}
