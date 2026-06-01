import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerCar {
    private Vector2D position;
    private double speed = 0;
    private double maxSpeed = 8.0;
    private double acceleration = 0.2;
    private double friction = 0.05;
    private double angle = Math.toRadians(-90);
    private double rotationSpeed = Math.toRadians(3);

    public PlayerCar(Vector2D position) {
        this.position = position;
    }

    public void update(KeyboardHandler input) {
        if (input.isPressed(KeyEvent.VK_LEFT)) angle -= rotationSpeed;
        if (input.isPressed(KeyEvent.VK_RIGHT)) angle += rotationSpeed;

        if (input.isPressed(KeyEvent.VK_UP)) {
            speed += acceleration;
            if (speed > maxSpeed) speed = maxSpeed;
        } else {
            speed -= friction;
            if (speed < 0) speed = 0;
        }
        double deltaX = speed * Math.cos(angle);
        double deltaY = speed * Math.sin(angle);

        position.add(deltaX, deltaY);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getAngle() {
        return angle;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)position.getX() - 20, (int) position.getY() - 35, 40, 70);
    }

}
