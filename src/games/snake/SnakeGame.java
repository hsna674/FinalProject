package games.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JPanel implements ActionListener {
    private Snake snake;
    private Apple apple;
    private Timer timer;
    private boolean gameOver;
    private int WIDTH = 300;
    private int HEIGHT = 300;
    GameStatistics statistics = new GameStatistics();
    private String highscoreText;

    public SnakeGame() {
        snake = new Snake();
        apple = new Apple();
        timer = new Timer(100, this);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        gameOver = false;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                Direction currentDirection = snake.getDirection();
                if (key == KeyEvent.VK_UP && currentDirection != Direction.DOWN) {
                    snake.setDirection(Direction.UP);
                } else if (key == KeyEvent.VK_DOWN && currentDirection != Direction.UP) {
                    snake.setDirection(Direction.DOWN);
                } else if (key == KeyEvent.VK_LEFT && currentDirection != Direction.RIGHT) {
                    snake.setDirection(Direction.LEFT);
                } else if (key == KeyEvent.VK_RIGHT && currentDirection != Direction.LEFT) {
                    snake.setDirection(Direction.RIGHT);
                }
            }
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            snake.move();
            if (snake.getBody().getFirst().equals(apple.getPosition())) {
                snake.grow();
                apple.relocate();
                statistics.updateScore(snake);
            } else if (snake.checkWallCollision(getWidth(), getHeight()) || snake.checkSelfCollision()) {
                gameOver = true;
                timer.stop();
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(String.valueOf(statistics.getScore()))) / 2;

        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString(String.valueOf(statistics.getScore()), x, 50);

        if (!gameOver) {
            snake.draw(g);
            apple.draw(g);
        } else {
            FontMetrics fm2 = g.getFontMetrics();
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", 50, 150);

            g.setFont(new Font("Arial", Font.BOLD, 20));

            highscoreText = "Highscore: " + statistics.getHighScore();
            int x2 = (getWidth() - fm2.stringWidth(highscoreText)) / 2;

            g.setColor(Color.WHITE);
            g.drawString(highscoreText, x2 + 20, 180);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
