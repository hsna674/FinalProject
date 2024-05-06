package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class TicTacToe extends JPanel {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 640;
    private static final int CELL_SIZE = 100;
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

        // Draw grid lines
        for (int i = 1; i < 3; i++) {
            g.drawLine(90, 250 + i * CELL_SIZE, 390, 250 + i * CELL_SIZE); // Horizontal lines
            g.drawLine(90 + i * CELL_SIZE, 250, 90 + i * CELL_SIZE, 550); // Vertical lines
        }

        // Draw X and O
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BOARD[i][j] == 1) {
                    g2d.setColor(Color.RED);
                    g.drawLine(100 + j * CELL_SIZE, 260 + i * CELL_SIZE, 180 + j * CELL_SIZE, 340 + i * CELL_SIZE);
                    g.drawLine(100 + j * CELL_SIZE, 340 + i * CELL_SIZE, 180 + j * CELL_SIZE, 260 + i * CELL_SIZE);
                } else if (BOARD[i][j] == 2) {
                    g2d.setColor(Color.BLUE);
                    g.drawOval(100 + j * CELL_SIZE, 260 + i * CELL_SIZE, 80, 80);
                }
            }
        }

        // Draw current player text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String currentPlayerText = "Current Player: " + (CURRENT_PLAYER == 1 ? "X" : "O");
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(currentPlayerText);
        g.drawString(currentPlayerText, (WIDTH - textWidth) / 2, 50);

        // Check win condition and draw winning line
        if (checkWinCondition()) {
            g2d.setColor(Color.GREEN);
            if (winningLineType == 1) {
                // Draw horizontal line
                g.drawLine(90, 300 + winningLineIndex * CELL_SIZE, 390, 300 + winningLineIndex * CELL_SIZE);
            } else if (winningLineType == 2) {
                // Draw vertical line
                g.drawLine(140 + winningLineIndex * CELL_SIZE, 250, 140 + winningLineIndex * CELL_SIZE, 550);
            } else if (winningLineType == 3) {
                // Draw diagonal line from top left to bottom right
                g.drawLine(90, 250, 390, 550);
            } else if (winningLineType == 4) {
                // Draw diagonal line from top right to bottom left
                g.drawLine(390, 250, 90, 550);
            }
        }
    }

    class TicTacToeMouse implements MouseListener {
        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int col = (x - 90) / CELL_SIZE;
            int row = (y - 250) / CELL_SIZE;

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && BOARD[row][col] == 0) {
                calculateMove(row, col);
            }
        }

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }

    private void calculateMove(int row, int col) {
        BOARD[row][col] = CURRENT_PLAYER;
        repaint();
        CURRENT_PLAYER = CURRENT_PLAYER == 1 ? 2 : 1;
        if (checkWinCondition()) {
            System.out.println("Win");
        }
    }

    private boolean checkWinCondition() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (BOARD[i][0] == BOARD[i][1] && BOARD[i][1] == BOARD[i][2] && BOARD[i][0] != 0) {
                winningLineType = 1; // Horizontal line
                winningLineIndex = i;
                return true;
            }
            if (BOARD[0][i] == BOARD[1][i] && BOARD[1][i] == BOARD[2][i] && BOARD[0][i] != 0) {
                winningLineType = 2; // Vertical line
                winningLineIndex = i;
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
