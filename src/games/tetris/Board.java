package games.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class Board extends JPanel {

    private static final long serialVersionUID = 1L;
    private final int ROWS = 20;
    private final int COLS = 10;

    private final boolean[][] cells = new boolean[ROWS][COLS];

    private int firstRowIndex;
    private int rowsToDelete = 0;

    private int score = 0;

    private Shapes shapes;
    private final SidePanel sidePanel;

    private final int m_interval = 300;
    private final Timer m_timer;

    private KeyListener listener;

    private boolean gameOver = false;
    private final Label labelGameOver;

    public Board(SidePanel sideP) {

        this.setBackground(new Color(0, 0, 0));
        this.setPreferredSize(new Dimension(202, 402));

        labelGameOver = new Label();

        m_timer = new Timer(m_interval, new TimerListener());

        listener = new CustomKeyListener();
        addKeyListener(listener);

        sidePanel = sideP;
        sidePanel.setBoard(Board.this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            if (shapes.hasReached()) {
                int[] temp = shapes.getPosition();
                if (temp[1] <= 0) {
                    gameOverDisplay();
                }
            }

            if (!gameOver) {
                remove(labelGameOver);

                g2.setPaint(Color.blue);
                for (int i = 0; i <= cells[0].length; i++) {
                    double x = i * 20;
                    g2.draw(new Line2D.Double(x, 0, x, 400));
                }
                for (int i = 0; i <= cells.length; i++) {
                    double y = i * 20;
                    g2.draw(new Line2D.Double(0, y, 200, y));
                }

                fillShapes(g2);

                fillBoard(g2);

                if (shapes.hasReached()) {
                    shapeGenerator();
                }
            }
        } catch (NullPointerException e) {
            remove(labelGameOver);

            for (int i = 0; i <= cells[0].length; i++) {
                double x = i * 20;
                g2.draw(new Line2D.Double(x, 0, x, 400));
            }
            for (int i = 0; i <= cells.length; i++) {
                double y = i * 20;
                g2.draw(new Line2D.Double(0, y, 200, y));
            }
        }
    }

    public void shapeGenerator() {
        int idx;
        Random rand = new Random();
        String[] shape = {"L", "RL", "S", "Z", "T", "SQ", "I"};
        Integer[] angles = {0, 1, 2, 3};
        idx = rand.nextInt(shape.length);
        String randomShape = shape[idx];
        idx = rand.nextInt(angles.length);
        int randomAngle = angles[idx];

        this.shapes = new Shapes(randomShape, randomAngle);
    }

    public void boardInitializer() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j] = false;
            }
        }
    }

    public void drawCell(Graphics gr, int x, int y) {
        gr.setColor(Color.RED);
        gr.fill3DRect(x * 20, y * 20, 19, 19, true);
        if (shapes.hasReached()) {
            cells[y][x] = true;
        }
    }

    public void fillShapes(Graphics gr) {
        int[] a = shapes.getPosition();
        int[] x = shapes.getX();
        int[] y = shapes.getY();
        for (int i = 0; i < 4; i++) {
            drawCell(gr, a[0] + x[i], a[1] + y[i]);
        }
    }

    public void fillBoard(Graphics gr) {
        checkLine();

        if (rowsToDelete > 0) {
            updateScore();
            deleteLine();
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j]) {
                    gr.setColor(Color.BLUE);
                    gr.fill3DRect(j * 20, i * 20, 19, 19, true);
                }
            }
        }
    }

    public void checkLine() {
        int occupiedCells;
        int occupiedRows = 0;
        for (int i = (ROWS - 1); i > -1; i--) {
            occupiedCells = 0;
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j]) {
                    occupiedCells++;
                }
            }
            if (occupiedCells == COLS) {
                if (occupiedRows == 0) {
                    firstRowIndex = i;
                }
                occupiedRows++;
            }
        }
        rowsToDelete = occupiedRows;
    }

    public void deleteLine() {
        int temp;
        int shiftTimes = 0;
        while (shiftTimes < rowsToDelete) {
            for (int i = firstRowIndex; i > 0; i--) {
                temp = i - 1;
                System.arraycopy(cells[temp], 0, cells[i], 0, COLS);
            }
            shiftTimes++;
        }
        rowsToDelete = 0;
        repaint();
    }

    public void updateScore() {
        if (rowsToDelete == 1) {
            score++;
        } else if (rowsToDelete == 2) {
            score = score + 3;
        } else if (rowsToDelete == 3) {
            score = score + 5;
        } else if (rowsToDelete == 4) {
            score = score + 10;
        } else {
            score = 0;
        }

        sidePanel.setScore(score);
    }

    public void setGameOver(boolean state) {
        gameOver = state;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void gameOverDisplay() {
        setGameOver(true);

        labelGameOver.setAlignment(Label.CENTER);
        labelGameOver.setBackground(new Color(150, 150, 255));
        labelGameOver.setFont(new Font("Berlin Sans FB Demi", 1, 15));
        labelGameOver.setText("GAME OVER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(labelGameOver, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(labelGameOver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(293, Short.MAX_VALUE))
        );

        boardInitializer();
        setAnimation(false);
        repaint();
    }

    public boolean[][] returnGrid() {
        return cells;
    }

    public void setAnimation(boolean turnOnOff) {
        if (turnOnOff) {
            m_timer.start();
            this.grabFocus();
        } else {
            m_timer.stop();
        }
    }

    public void startGame() {
        boardInitializer();
        shapeGenerator();
        repaint();
        setGameOver(false);
        setAnimation(true);
    }

    public void resetGame() {
        boardInitializer();
        updateScore();
        repaint();
        setGameOver(false);
        setAnimation(false);
        shapes = null;
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shapes.dropElement(Board.this);
            repaint();
        }
    }

    private class CustomKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                shapes.rotate(Board.this);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                shapes.dropElement(Board.this);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                shapes.moveRight(Board.this);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                shapes.moveLeft(Board.this);
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}