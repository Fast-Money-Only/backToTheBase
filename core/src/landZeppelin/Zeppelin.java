package landZeppelin;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Zeppelin {
    private Rectangle bounds;
    private Vector2 velocity;
    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float windX;
    private float windY;

    public Zeppelin(float x, float y, float width, float height, float maxSpeed, float acceleration, float deceleration) {
        this.bounds = new Rectangle(x, y, width, height);
        this.velocity = new Vector2(0, 0);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
    }

    public void setWind(float windX, float windY) {
        this.windX = windX;
        this.windY = windY;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void update(float delta, boolean moveLeft, boolean moveRight, boolean moveUp, boolean moveDown) {
        if (moveLeft) {
            velocity.x = Math.max(velocity.x - acceleration * delta, -maxSpeed);
        } else if (moveRight) {
            velocity.x = Math.min(velocity.x + acceleration * delta, maxSpeed);
        } else {
            velocity.x = applyDeceleration(velocity.x, delta);
        }

        if (moveUp) {
            velocity.y = Math.min(velocity.y + acceleration * delta, maxSpeed);
        } else if (moveDown) {
            velocity.y = Math.max(velocity.y - acceleration * delta, -maxSpeed);
        } else {
            velocity.y = applyDeceleration(velocity.y, delta);
        }

        // Apply wind effect
        velocity.x += windX * delta;
        velocity.y += windY * delta;

        // Update position
        bounds.x += velocity.x * delta;
        bounds.y += velocity.y * delta;
    }

    private float applyDeceleration(float velocity, float delta) {
        if (velocity > 0) {
            velocity = Math.max(velocity - deceleration * delta, 0);
        } else if (velocity < 0) {
            velocity = Math.min(velocity + deceleration * delta, 0);
        }
        return velocity;
    }

    public boolean isLandingSuccessful() {
        // Assume a successful landing requires x position > 300 and y position < 10, and speed is below a threshold
        return bounds.x < 300 && bounds.y < 10 && velocity.len() < 10;
    }

    public boolean isLandingTooFast() {
        return velocity.len() >= 10;
    }

    public boolean isLandingTooEarly() {
        return bounds.x <= 300;
    }
}
