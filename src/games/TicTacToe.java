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
    }

    class TicTacToeMouse implements MouseListener {
        public void mouseClicked(MouseEvent e) {
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

        public void mousePressed(MouseEvent e) {}

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
    }
}
