package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class TicTacToe extends JPanel {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 640;
    private static final int[][] BOARD = new int[3][3]; // 0 = None, 1 = X, 2 = O
    private static int CURRENT_PLAYER = 1;
    private int winningLineType = 0; // 1 = horizontal, 2 = vertical, 3 = diagonal (top left to bottom right), 4 = diagonal (top right to bottom left)
    private int winningLineIndex = 0;


    public TicTacToe() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(new TicTacToeMouse());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        int strokeWidth = 5;
        g2d.setStroke(new BasicStroke(strokeWidth));

        g.drawLine(190, 250, 190, 550);
        g.drawLine(290, 250, 290, 550);
        g.drawLine(90, 350, 390, 350);
        g.drawLine(90, 450, 390, 450);

        System.out.println(Arrays.deepToString(BOARD));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BOARD[i][j] == 1) {
                    g2d.setColor(Color.RED);
                    g.drawLine(100 + (100 * j), 260 + (100 * i), 180 + (100 * j), 340 + (100 * i));
                    g.drawLine(100 + (100 * j), 340 + (100 * i), 180 + (100 * j), 260 + (100 * i));
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BOARD[i][j] == 2) {
                    g2d.setColor(Color.BLUE);
                    g.drawOval((100 + (100 * j)), (260 + (100 * i)), 80, 80);
                }
            }
        }

        // Check win condition and draw winning line
        if (checkWinCondition()) {
            g2d.setColor(Color.BLACK);
            if (winningLineType == 1) {
                // Draw horizontal line
                g.drawLine(90, 300 + (winningLineIndex * 100), 390, 300 + (winningLineIndex * 100));
            } else if (winningLineType == 2) {
                // Draw vertical line
                g.drawLine(140 + (winningLineIndex * 100), 250, 140 + (winningLineIndex * 100), 550);
            } else if (winningLineType == 3) {
                // Draw diagonal line from top left to bottom right
                g.drawLine(100, 260, 380, 540);
            } else if (winningLineType == 4) {
                // Draw diagonal line from top right to bottom left
                g.drawLine(380, 260, 100, 540);
            }
        }
    }


    class TicTacToeMouse implements MouseListener {
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (x >= 90 && y >= 250 && x <= 190 && y <= 350) {
                calculateMove(0, 0);
            }

            if (x >= 190 && y >= 250 && x <= 290 && y <= 350) {
                calculateMove(1, 0);
            }

            if (x >= 290 && y >= 250 && x <= 390 && y <= 350) {
                calculateMove(2, 0);
            }

            if (x >= 90 && y >= 350 && x <= 190 && y <= 450) {
                calculateMove(0, 1);
            }

            if (x >= 190 && y >= 350 && x <= 290 && y <= 450) {
                calculateMove(1, 1);
            }

            if (x >= 290 && y >= 350 && x <= 390 && y <= 450) {
                calculateMove(2, 1);
            }

            if (x >= 90 && y >= 450 && x <= 190 && y <= 550) {
                calculateMove(0, 2);
            }

            if (x >= 190 && y >= 450 && x <= 290 && y <= 550) {
                calculateMove(1, 2);
            }

            if (x >= 290 && y >= 450 && x <= 390 && y <= 550) {
                calculateMove(2, 2);
            }
        }

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }

    private void calculateMove(int xIndex, int yIndex) {
        if (CURRENT_PLAYER == 1) {
            BOARD[yIndex][xIndex] = 1;
            repaint();
            CURRENT_PLAYER = 2;
        } else {
            BOARD[yIndex][xIndex] = 2;
            repaint();
            CURRENT_PLAYER = 1;
        }
        if (checkWinCondition()) {
            System.out.println("Win");
        }
    }

    private boolean checkWinCondition() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (BOARD[i][0] == BOARD[i][1] && BOARD[i][1] == BOARD[i][2] && BOARD[i][0] != 0) {
                winningLineType = 1; // Horizontal line
                winningLineIndex = i;
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (BOARD[0][j] == BOARD[1][j] && BOARD[1][j] == BOARD[2][j] && BOARD[0][j] != 0) {
                winningLineType = 2; // Vertical line
                winningLineIndex = j;
                return true;
            }
        }

        // Check diagonals
        if ((BOARD[0][0] == BOARD[1][1] && BOARD[1][1] == BOARD[2][2] && BOARD[0][0] != 0)) {
            winningLineType = 3; // Diagonal (top left to bottom right)
            return true;
        }
        if (BOARD[0][2] == BOARD[1][1] && BOARD[1][1] == BOARD[2][0] && BOARD[0][2] != 0) {
            winningLineType = 4; // Diagonal (top right to bottom left)
            return true;
        }

        return false;
    }

}
