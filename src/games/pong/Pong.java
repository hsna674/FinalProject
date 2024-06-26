package games.pong;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pong extends JPanel implements KeyListener {

    private boolean player1Up, player1Down, player2Up, player2Down;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int PADDLE_WIDTH = 3;
    private static final int PADDLE_HEIGHT = 80;
    private static final int BALL_DIAMETER = 20;

    private int paddle1Y, paddle2Y;
    private int ballX, ballY;
    private int ballDX, ballDY;
    private int player1Score, player2Score;

    private boolean isPaused = false;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PausePanel pausePanel;

    public Pong(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(40, 92, 49));
        setFocusable(true);
        addKeyListener(this);

        paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        resetBall();

        new Timer(10, e -> {
            if (!isPaused) {
                updateGame();
                repaint();
            }
        }).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.black);
        g.fillRect(320, 0, 5, 480);
        g.fillOval(218, 140, 200, 200);
        g.setColor(Color.gray.brighter());
        g.fillRect(160, 0, 5, 480);
        g.fillRect(480, 0, 5, 480);
        g.fillRect(60, 0, 5, 480);
        g.fillRect(580, 0, 5, 480);
        g.setColor(new Color(40, 92, 49));
        g.fillOval(223, 289 / 2, 190, 190);
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.BOLD, 35));
        g.drawString("Pong!", 280, 248);
        g.setColor(Color.blue.brighter());
        g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.setColor(Color.white);
        g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        g.setColor(Color.white);


        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString(String.valueOf(player1Score), (WIDTH / 4) + 10, 50);
        g.drawString(String.valueOf(player2Score), (3 * WIDTH / 4) - 19, 50);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player1Up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player1Down = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2Up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2Down = true;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            togglePause();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player1Up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player1Down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            player2Up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player2Down = false;
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            cardLayout.show(mainPanel, "pause");
            mainPanel.getComponent(1).requestFocus(); // Ensure pause panel gets focus
        } else {
            cardLayout.show(mainPanel, "game");
            mainPanel.getComponent(0).requestFocus(); // Ensure game panel gets focus
        }
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_DIAMETER / 2;
        ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
        ballDX = Math.random() > 0.5 ? 5 : -5;  // Random direction
        ballDY = (int) (Math.random() * 3 - 1) * 2; // Random bounce angle (-2, 0, 2)
    }

    public void updateGame() {
        // Check for wall collisions
        if (ballY <= 0 || ballY + BALL_DIAMETER >= HEIGHT) {
            ballDY *= -1;
        }

        // Check for paddle collisions
        if (ballX <= PADDLE_WIDTH && ballY + BALL_DIAMETER >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            int paddleHitY = ballY + BALL_DIAMETER / 2 - paddle1Y;
            ballDX *= -1; // Invert ballDX for bounce
            ballDY = calculateBounceAngle(paddleHitY);
            // Update ball position based on new ballDX and ballDY (happens later)
        } else if (ballX + BALL_DIAMETER >= WIDTH - PADDLE_WIDTH && ballY + BALL_DIAMETER >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            int paddleHitY = ballY + BALL_DIAMETER / 2 - paddle2Y;
            ballDX *= -1; // Invert ballDX for bounce
            ballDY = calculateBounceAngle(paddleHitY);
            // Update ballX after calculating bounce (important)
            ballX += ballDX;
        }

        // Update ball position (after all checks)
        ballX += ballDX;
        ballY += ballDY;

        // Check for goal and reset
        if (ballX < 0) {
            player2Score++;
            resetBall();
        } else if (ballX + BALL_DIAMETER > WIDTH + 1) {
            player1Score++;
            resetBall();
        }

        if (player1Up) {
            paddle1Y = Math.max(0, paddle1Y - 5); // Move up by 5 pixels
        } else if (player1Down) {
            paddle1Y = Math.min(HEIGHT - PADDLE_HEIGHT, paddle1Y + 5); // Move down by 5 pixels
        }

        if (player2Up) {
            paddle2Y = Math.max(0, paddle2Y - 5); // Move up by 5 pixels
        } else if (player2Down) {
            paddle2Y = Math.min(HEIGHT - PADDLE_HEIGHT, paddle2Y + 5); // Move down by 5 pixels
        }
    }

    private int calculateBounceAngle(int paddleHitY) {
        int center = PADDLE_HEIGHT / 2;
        int offset = paddleHitY - center;
        int maxAngle = 4; // Adjust for desired bounce variation
        return (int) Math.round(offset * (double) maxAngle / center);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong");
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        Pong gamePanel = new Pong(mainPanel, cardLayout);
        PausePanel pausePanel = new PausePanel(mainPanel, cardLayout);

        mainPanel.add(gamePanel, "game");
        mainPanel.add(pausePanel, "pause");

        cardLayout.show(mainPanel, "game");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        gamePanel.requestFocusInWindow(); // Ensure game panel gets initial focus
    }
}

class PausePanel extends JPanel implements KeyListener {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public PausePanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString("PAUSED", 270, 240);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            cardLayout.show(mainPanel, "game");
            mainPanel.getComponent(0).requestFocus(); // Ensure game panel gets focus
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
