package games;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ConnectFour extends JPanel {
    // Define constants for empty, AI player, and human player pieces
    public static final int EMPTY = 0;
    public static final int HUMAN_PLAYER = 1;
    public static final int AI_PLAYER = 2;

    // Screen Dimensions
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private int[][] board; // 2D array representing the game board
    private int currentPlayer; // Current player (either AI_PLAYER or HUMAN_PLAYER)

    // Constructor to initialize the game board and set the current player
    public ConnectFour() {
        board = new int[6][7]; // Standard Connect 4 board size
        currentPlayer = HUMAN_PLAYER; // Human player starts the game
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        initializeBoard();
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x1c3c94));
        g.fillOval(25, 50, 75, 75);
        g.fillOval(540, 50, 75, 75);
        g.fillOval(25, 380, 75, 75);
        g.fillOval(540, 380, 75, 75);
        g.fillRect(63, 50, 515, 405);
        g.fillRect(25, 88, 590, 330);
    }

    // Method to initialize the game board with empty cells
    private void initializeBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Method to print the current state of the game board
    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------");
    }

    // Method to make a move for the current player
    public boolean makeMove(int column, int player) {
        // Check if the column is within bounds and not full
        if (column < 0 || column >= 7 || board[0][column] != EMPTY) {
            return false; // Invalid move
        }

        // Find the lowest empty row in the selected column and place the player's piece there
        for (int i = 5; i >= 0; i--) {
            if (board[i][column] == EMPTY) {
                board[i][column] = player;
                if (currentPlayer == HUMAN_PLAYER) {
                    currentPlayer = AI_PLAYER;
                } else if (currentPlayer == AI_PLAYER) {
                    currentPlayer = HUMAN_PLAYER;
                }
                return true; // Move successful
            }
        }
        return false; // Column is full (should never reach this point in a valid game)
    }

    // Method to check if the game board is full
    public boolean isBoardFull() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == EMPTY) {
                    return false; // Board is not full
                }
            }
        }
        return true; // Board is full
    }

    // Method to check if the game is over (win, loss, or draw)
    public boolean isGameOver() {
        return isWin(HUMAN_PLAYER) || isWin(AI_PLAYER) || isBoardFull();
    }

    // Method to check if a player has won
    private boolean isWin(int player) {
        // Check horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check vertical
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // Check diagonal (top-left to bottom-right)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player && board[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check diagonal (bottom-left to top-right)
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == player && board[i - 1][j + 1] == player && board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
                    return true;
                }
            }
        }
        return false; // No win
    }

    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        game.playAgainstAI();
    }

    // Implement alpha-beta pruning to optimize the minimax algorithm
    public int minimax(int depth, int alpha, int beta, int player) {
        if (depth == 0 || isGameOver()) {
            return evaluate(); // Base case: return evaluation score
        }

        if (player == AI_PLAYER) { // Maximizing player
            int maxEval = Integer.MIN_VALUE;
            for (int column = 0; column < 7; column++) {
                if (makeMove(column, AI_PLAYER)) {
                    int eval = minimax(depth - 1, alpha, beta, HUMAN_PLAYER); // Recur for next level (human player's turn)
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    undoMove(column); // Undo move

                    if (beta <= alpha) {
                        break; // Beta cutoff
                    }
                }
            }
            return maxEval;
        } else { // Minimizing player
            int minEval = Integer.MAX_VALUE;
            for (int column = 0; column < 7; column++) {
                if (makeMove(column, HUMAN_PLAYER)) {
                    int eval = minimax(depth - 1, alpha, beta, AI_PLAYER); // Recur for next level (AI's turn)
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    undoMove(column); // Undo move

                    if (beta <= alpha) {
                        break; // Alpha cutoff
                    }
                }
            }
            return minEval;
        }
    }


    public void undoMove(int column) {
        // Find the lowest occupied row in the selected column and remove the player's piece from there
        for (int i = 0; i < 6; i++) {
            if (board[i][column] != EMPTY) {
                board[i][column] = EMPTY;
                return;
            }
        }
    }

    // Refine the evaluation function to prioritize winning moves and block opponent's winning moves
    public int evaluate() {
        int score = 0;

        // Evaluate horizontal, vertical, and diagonal lines for potential wins or blocking moves
        score += evaluateLinesForPlayer(AI_PLAYER); // Evaluate for AI player
        score -= evaluateLinesForPlayer(HUMAN_PLAYER); // Evaluate for human player

        // Prioritize central columns
        score += prioritizeCentralColumns();

        // Consider multiple potential winning lines
        score += considerMultiplePotentialWins();

        // Penalize vulnerable positions for the opponent
        score -= penalizeVulnerablePositions();

        return score;
    }

    // Prioritize central columns (columns 3 and 4)
    private int prioritizeCentralColumns() {
        int centralColumnsScore = 0;
        for (int i = 0; i < 6; i++) {
            if (board[i][3] == AI_PLAYER) {
                centralColumnsScore += 1;
            }
            if (board[i][4] == AI_PLAYER) {
                centralColumnsScore += 1;
            }
        }
        return centralColumnsScore * 10; // Adjust weight as needed
    }

    // Consider multiple potential winning lines
    private int considerMultiplePotentialWins() {
        int multipleWinsScore = 0;
        // Check for potential winning lines for AI_PLAYER
        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < 6; row++) {
                if (board[row][column] == EMPTY) {
                    board[row][column] = AI_PLAYER;
                    if (isWin(AI_PLAYER)) {
                        multipleWinsScore += 10; // Adjust weight as needed
                    }
                    board[row][column] = EMPTY; // Undo move
                    break;
                }
            }
        }
        return multipleWinsScore;
    }

    // Penalize vulnerable positions for the opponent
    private int penalizeVulnerablePositions() {
        int vulnerablePositionsScore = 0;
        // Check for potential winning lines for HUMAN_PLAYER
        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < 6; row++) {
                if (board[row][column] == EMPTY) {
                    board[row][column] = HUMAN_PLAYER;
                    if (isWin(HUMAN_PLAYER)) {
                        vulnerablePositionsScore -= 10; // Adjust weight as needed
                    }
                    board[row][column] = EMPTY; // Undo move
                    break;
                }
            }
        }
        return vulnerablePositionsScore;
    }

    // Helper method to evaluate lines for potential wins or blocking moves
    private int evaluateLinesForPlayer(int player) {
        int totalScore = 0;

        // Evaluate horizontal, vertical, and diagonal lines for the given player
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                // Evaluate horizontal lines
                if (j <= 3) {
                    totalScore += evaluateLine(i, j, 0, 1, player); // Evaluate right
                }
                // Evaluate vertical lines
                if (i <= 2) {
                    totalScore += evaluateLine(i, j, 1, 0, player); // Evaluate down
                }
                // Evaluate diagonal lines (top-left to bottom-right)
                if (i <= 2 && j <= 3) {
                    totalScore += evaluateLine(i, j, 1, 1, player); // Evaluate diagonal
                }
                // Evaluate diagonal lines (bottom-left to top-right)
                if (i >= 3 && j <= 3) {
                    totalScore += evaluateLine(i, j, -1, 1, player); // Evaluate diagonal
                }
            }
        }

        return totalScore;
    }

    // Helper method to evaluate a line of 4 cells for potential wins or blocking moves
    private int evaluateLine(int row, int col, int rowDir, int colDir, int player) {
        int score = 0;
        int playerCount = 0;
        int opponentCount = 0;

        for (int i = 0; i < 4; i++) {
            int cell = board[row + i * rowDir][col + i * colDir];
            if (cell == player) {
                playerCount++;
            } else if (cell != EMPTY) {
                opponentCount++;
            }
        }

        if (playerCount == 4) {
            score += 1000; // Player wins
        } else if (playerCount == 3 && opponentCount == 0) {
            score += 100; // Player has 3 in a row
        } else if (playerCount == 2 && opponentCount == 0) {
            score += 10; // Player has 2 in a row
        } else if (playerCount == 1 && opponentCount == 0) {
            score += 1; // Player has 1 in a row
        }

        return score;
    }

    // Method for the AI player to make a move
    public void makeAIMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int column = 0; column < 7; column++) {
            if (makeMove(column, AI_PLAYER)) {
                int score = minimax(10, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
                undoMove(column); // Undo move

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = column;
                }
            }
        }

        makeMove(bestMove, AI_PLAYER); // Make the best move for the AI player
    }

    // Method to play the game against the AI
    public void playAgainstAI() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            printBoard();

            // Human player's turn
            System.out.println("Your turn. Enter column (1-7): ");
            int column = scanner.nextInt() - 1;
            if (makeMove(column, HUMAN_PLAYER)) {
                if (isGameOver()) {
                    break;
                }
                printBoard();
            } else {
                System.out.println("Invalid move! Please try again.");
                continue;
            }

            // AI player's turn
            makeAIMove();
        }

        printBoard();

        if (isWin(HUMAN_PLAYER)) {
            System.out.println("You win!");
        } else if (isWin(AI_PLAYER)) {
            System.out.println("AI wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }

}
