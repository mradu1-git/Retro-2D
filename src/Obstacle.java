import java.awt.*;

public abstract class Obstacle {
    protected double x, y;
    protected int width, height;
    protected boolean isWall = false;

    public Obstacle(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public abstract void draw(Graphics2D g);
    public abstract void onCollision(PlayerCar player);
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
