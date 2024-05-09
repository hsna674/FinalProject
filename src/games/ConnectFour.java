package games;

import java.util.Scanner;

public class ConnectFour {
    // Define constants for empty, player 1, and player 2 pieces
    public static final int EMPTY = 0;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    private int[][] board; // 2D array representing the game board
    private int currentPlayer; // Current player (either PLAYER1 or PLAYER2)

    // Constructor to initialize the game board and set the current player
    public ConnectFour() {
        board = new int[6][7]; // Standard Connect 4 board size
        currentPlayer = PLAYER1; // Player 1 starts the game
        initializeBoard();
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
                if (currentPlayer == PLAYER1) {
                    currentPlayer = PLAYER2;
                } else if (currentPlayer == PLAYER2) {
                    currentPlayer = PLAYER1;
                }
                return true; // Move successful
            }
        }
        return false; // Column is full (should never reach this point in a valid game)
    }

    // Method to switch players after each move
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1;
    }

    // Method to get the current player
    public int getCurrentPlayer() {
        return currentPlayer;
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
        return isWin(PLAYER1) || isWin(PLAYER2) || isBoardFull();
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

    public int minimax(int depth, int alpha, int beta, int player) {
        if (depth == 0 || isGameOver()) {
            return evaluate(); // Base case: return evaluation score
        }

        if (player == currentPlayer) { // Maximizing player
            int maxEval = Integer.MIN_VALUE;
            for (int column = 0; column < 7; column++) {
                if (makeMove(column, 2)) {
                    int eval = minimax(depth - 1, alpha, beta, PLAYER2); // Recur for next level (opponent's turn)
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
                if (makeMove(column, 2)) {
                    int eval = minimax(depth - 1, alpha, beta, PLAYER1); // Recur for next level (AI's turn)
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

    public int evaluate() {
        int score = 0;

        // Evaluate horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                score += evaluateLine(i, j, 0, 1); // Evaluate right
            }
        }

        // Evaluate vertical
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                score += evaluateLine(i, j, 1, 0); // Evaluate down
            }
        }

        // Evaluate diagonal (top-left to bottom-right)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                score += evaluateLine(i, j, 1, 1); // Evaluate diagonal
            }
        }

        // Evaluate diagonal (bottom-left to top-right)
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                score += evaluateLine(i, j, -1, 1); // Evaluate diagonal
            }
        }

        return score;
    }

    // Helper method to evaluate a line of 4 cells
    private int evaluateLine(int row, int col, int rowDir, int colDir) {
        int score = 0;
        int aiCount = 0;
        int opponentCount = 0;

        for (int i = 0; i < 4; i++) {
            int cell = board[row + i * rowDir][col + i * colDir];
            if (cell == currentPlayer) {
                aiCount++;
            } else if (cell != EMPTY) {
                opponentCount++;
            }
        }

        if (aiCount == 4) {
            score += 1000; // AI wins
        } else if (aiCount == 3 && opponentCount == 0) {
            score += 100; // AI has 3 in a row
        } else if (aiCount == 2 && opponentCount == 0) {
            score += 10; // AI has 2 in a row
        } else if (aiCount == 1 && opponentCount == 0) {
            score += 1; // AI has 1 in a row
        } else if (opponentCount == 3 && aiCount == 0) {
            score -= 100; // Opponent has 3 in a row
        }

        return score;
    }

    // Method for the AI player to make a move
    public void makeAIMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int column = 0; column < 7; column++) {
            if (makeMove(column, 2)) {
                int score = minimax(1000, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
                undoMove(column); // Undo move

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = column;
                }
            }
        }

        makeMove(bestMove, 2); // Make the best move for the AI player
    }

    // Method to play the game against the AI
    public void playAgainstAI() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            printBoard();

            // Human player's turn
            System.out.println("Your turn. Enter column (0-6): ");
            int column = scanner.nextInt();
            if (makeMove(column, 1)) {
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

        if (isWin(PLAYER1)) {
            System.out.println("You win!");
        } else if (isWin(PLAYER2)) {
            System.out.println("AI wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }

}
