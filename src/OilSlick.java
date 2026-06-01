import java.awt.*;

public class OilSlick extends Obstacle{
    public OilSlick(double x, double y) {
        super(x, y, 60, 30);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval((int) x, (int) y, width, height);
    }

    @Override
    public void onCollision(PlayerCar player) {
        System.out.println("Slipped on Oil! Speed dropped!");
        player.setSpeed(1.5);
    }
}

