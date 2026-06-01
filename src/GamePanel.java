import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private final Timer gameTimer;
    private final KeyboardHandler input;
    private final PlayerCar player;
    private final List<Obstacle> obstacles;

    private int score = 0;
    private int highScore = 0;
    private int spawnTimer = 0;
    private final int SPAWN_INTERVAL = 90;
    private boolean isGameOver = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);

        this.input = new KeyboardHandler();
        this.player = new PlayerCar(new Vector2D(400, 480));
        this.obstacles = new ArrayList<>();

        this.setFocusable(true);
        this.addKeyListener(this.input);

        int FPS = 60;
        int DELAY = 1000 / FPS;
        this.gameTimer = new Timer(DELAY, (ActionListener) this);
        this.gameTimer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            updateGameLogic();
        } else {
            if (input.isPressed(KeyEvent.VK_ENTER)) {
                restartGame();
            }
        }
        this.repaint();
    }

    private void updateGameLogic() {

        player.update(input);
        score++;
        spawnTimer++;
        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0;
            double randomX = 50 + (Math.random() * 650);
            if (Math.random() > 0.5) {
                obstacles.add(new OilSlick(randomX, -50));
            } else {
                obstacles.add(new WallBarrier(randomX, -50));
            }
        }
        Iterator<Obstacle> iterator= obstacles.iterator();
        Rectangle playerBox = player.getBounds();
        while (iterator.hasNext()) {
            Obstacle obs = iterator.next();
            obs.y += 5;
            if (playerBox.intersects(obs.getBounds())) {
                obs.onCollision(player);
                if (obs.isWall) {
                    isGameOver = true;
                    if (score > highScore) {
                        highScore = score;
                    }
                }
            }
            if (obs.y > 600) {
                iterator.remove();
            }
        }
    }

    private void restartGame() {
        this.score = 0;
        this.spawnTimer = 0;
        this.isGameOver = false;

        this.player.getPosition().setX(400);
        this.player.getPosition().setY(480);
        this.player.setSpeed(0);
        this.obstacles.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Obstacle obs : obstacles) {
            obs.draw(g2d);
        }
        double carX = player.getPosition().getX();
        double carY = player.getPosition().getY();
        double carAngle = player.getAngle();

        g2d.translate(carX, carY);
        g2d.rotate(carAngle);

        int width = 70;
        int height = 40;

        g2d.setColor(Color.RED);
        g2d.fillRect( -width / 2, -height / 2, width, height);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(width /2 - 5, -15, 5, 10);
        g2d.fillRect(width / 2 - 5, 5, 5, 10);

        g2d.setTransform(new java.awt.geom.AffineTransform());

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2d.drawString("SCORE: " + score, 20, 30);
        g2d.drawString("HIGH SCORE: " + highScore, 20, 55);

        if (isGameOver) {
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.fillRect(0, 0, 800, 600);

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            g2d.drawString("GAME OVER", 260, 260);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.drawString("Final Score: " + score, 320, 310);
            g2d.setFont(new Font("Arial", Font.ITALIC, 16));
            g2d.drawString("Press [Enter] to DRIFT Again", 285, 360);
        }
    }
}
