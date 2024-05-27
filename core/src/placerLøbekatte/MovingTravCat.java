package placerLøbekatte;

import com.badlogic.gdx.math.Rectangle;

public class MovingTravCat {
    Rectangle rect;
    float speed;
    boolean movingRight;

    public MovingTravCat(Rectangle rect, float speed) {
        this.rect = rect;
        this.speed = speed;
        this.movingRight = true;
    }


    public void update(float deltaTime) {
        if (movingRight) {
            rect.x += speed * deltaTime;
            if (rect.x + rect.width >= 800) {
                rect.x = 800 - rect.width;
                movingRight = false;
            }
        } else {
            rect.x -= speed * deltaTime;
            if (rect.x <= 0) {
                rect.x = 0;
                movingRight = true;
            }
        }
    }

    public Rectangle getRect() {
        return rect;
    }
}
