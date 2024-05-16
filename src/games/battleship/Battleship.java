package games.battleship;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class Battleship extends JPanel {
    private JButton[][] board;
    private int[][] matrix;
    private int hits, torpedoes;
    private JLabel label;
    private JButton reset;
    private final int N = 10;

    public Battleship() {
        setLayout(new BorderLayout());
        hits = 0;
        torpedoes = 20;

        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);

        JPanel north = new JPanel();
        north.setLayout(new FlowLayout());
        add(north, BorderLayout.NORTH);
        label = new JLabel("Welcome to Battleship - You have 20 torpedoes.");
        north.add(label);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(N, N));
        add(center, BorderLayout.CENTER);

        board = new JButton[N][N];
        matrix = new int[N][N];
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++) {
                board[r][c] = new JButton();
                board[r][c].setOpaque(true);
                board[r][c].setBorderPainted(true);
                board[r][c].setBorder(BorderFactory.createLineBorder(Color.BLUE.darker()));
                board[r][c].setBackground(Color.blue.darker());
                board[r][c].addActionListener(new Handler1(r, c));
                center.add(board[r][c]);
            }

        reset = new JButton("Reset");
        reset.addActionListener(new Handler2());
        reset.setEnabled(false);
        add(reset, BorderLayout.SOUTH);

        placeShip();
    }

    private void placeShip() {
        {
            int coin = (int) (Math.random() * 2);
            if (coin == 0) {
                int row = (int) (Math.random() * 7);
                int col = (int) (Math.random() * 7);
                matrix[row][col] = 1;
                matrix[row + 1][col] = 1;
                matrix[row + 2][col] = 1;
                matrix[row + 3][col] = 1;
            } else {
                int row = (int) (Math.random() * 7);
                int col = (int) (Math.random() * 7);
                matrix[row][col] = 1;
                matrix[row][col + 1] = 1;
                matrix[row][col + 2] = 1;
                matrix[row][col + 3] = 1;
            }
            int coins = (int) (Math.random() * 2);
            if (coins == 0) {
                int rows = (int) (Math.random() * 7);
                int cols = (int) (Math.random() * 7);
                matrix[rows][cols] = 1;
                matrix[rows + 1][cols] = 1;
                matrix[rows + 2][cols] = 1;
                matrix[rows + 3][cols] = 1;
                matrix[rows + 4][cols] = 1;
            } else {
                int rows = (int) (Math.random() * 7);
                int cols = (int) (Math.random() * 7);
                matrix[rows][cols] = 1;
                matrix[rows][cols + 1] = 1;
                matrix[rows][cols + 2] = 1;
                matrix[rows][cols + 3] = 1;
                matrix[rows][cols + 4] = 1;
            }
        }
    }


    private class Handler1 implements ActionListener {
        private int myRow, myCol;

        public Handler1(int r, int c) {
            myRow = r;
            myCol = c;
        }

        int count = 0;

        public void actionPerformed(ActionEvent e) {
            if (matrix[myRow][myCol] == 1) {
                hits++;
                count++;
                board[myRow][myCol].setBackground(Color.red);
                board[myRow][myCol].setEnabled(false);
            } else {
                board[myRow][myCol].setBackground(Color.white);
                board[myRow][myCol].setEnabled(false);
            }

            if (torpedoes > 0) {
                torpedoes--;
                label.setText("You have " + torpedoes + " torpedoes left");

            } else if (torpedoes == 0 && hits < 10) {
                label.setText("You suck.");
                //set buttons with matrix of 1 to black and set all buttons to unenabled
                //i'm not doing this bc its a for loop that takes a long time to write but you can copy paste from the beginning of this file
                for (int i = 0; i < 10; i++) {
                    for (int p = 0; p < 10; p++) {
                        if (matrix[i][p] == 1) {
                            board[i][p].setBackground(Color.black);
                            board[i][p].setEnabled(false);
                        } else {
                            board[i][p].setEnabled(false);
                            reset.setEnabled(true);
                            board[i][p].setEnabled(false);
                        }
                    }
                }
            }

            if (hits == 10) {
                label.setText("You Won :)");
                reset.setEnabled(true);
                for (int f = 0; f < 10; f++) {
                    for (int c = 0; c < 10; c++) {
                        board[f][c].setEnabled(false);
                    }
                }

            }
        }
    }

    private class Handler2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int y = 0; y < 10; y++) {
                for (int z = 0; z < 10; z++) {
                    board[z][y].setBackground(Color.blue.darker());
                    board[z][y].setEnabled(true);


                    if (matrix[y][z] == 1) {
                        matrix[y][z] = 0;
                    }
                }
            }


            label.setText("Welcome to Battleship - You have 20 torpedoes.");
            for (int g = 0; g < 10; g++) {
                for (int p = 0; p < 10; p++) {
                    board[g][p].setEnabled(true);
                }
            }
            reset.setEnabled(false);
            torpedoes = 20;
            hits = 0;
            placeShip();

        }

    }
}