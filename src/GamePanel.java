import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class GamePanel extends JPanel implements ActionListener {
    private final Timer gameTimer;
    private final KeyboardHandler input;
    private final PlayerCar player;
    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);

        this.input = new KeyboardHandler();
        this.player = new PlayerCar(new Vector2D(400, 300));
        this.setFocusable(true);
        this.addKeyListener(this.input);

        int FPS = 60;
        int DELAY = 1000 / FPS;
        this.gameTimer = new Timer(DELAY, (ActionListener) this);
        this.gameTimer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        updateGameLogic();
        this.repaint();
    }

    private void updateGameLogic() {
        player.update(input);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRect(375, 450, 50, 80);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double carX = player.getPosition().getX();
        double carY = player.getPosition().getY();
        double carAngle = player.getAngle();

        long standardTransform = (long) g2d.getTransform().hashCode();
        g2d.translate(carX, carY);
        g2d.rotate(carAngle);

        int width = 40;
        int height = 70;

        g2d.setColor(Color.RED);
        g2d.fillRect( -width / 2, -height / 2, width, height);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(-15, -height / 2 - 5, 10, 5);
        g2d.fillRect(5, -height / 2 - 5, 10, 5);

        g2d.setTransform(new java.awt.geom.AffineTransform());
    }
}
