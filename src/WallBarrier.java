import java.awt.*;

public class WallBarrier extends Obstacle {
    public WallBarrier(double x, double y) {
        super(x, y, 80, 20);
        this.isWall = true;
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect((int)x, (int)y, width, height);
    }
    @Override
    public void onCollision(PlayerCar player) {
        System.out.println("CRASH! Stopped completely!");
        player.setSpeed(0);
    }
}
