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

    }
}
