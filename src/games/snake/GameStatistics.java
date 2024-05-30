package games.snake;

import java.io.*;

public class GameStatistics {
    private int score;
    private int highScore;
    private static final String HIGH_SCORE_FILE = "highscore.txt";

    public GameStatistics() {
        this.score = 0;
        this.highScore = readHighScore();
    }

    public void updateScore(Snake snake) {
        score = snake.getBody().size() - 1;
        if (score > highScore) {
            highScore = score;
            writeHighScore(highScore);
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    private int readHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line = br.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not read high score: " + e.getMessage());
        }
        return 0;
    }

    private void writeHighScore(int highScore) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            bw.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.err.println("Could not write high score: " + e.getMessage());
        }
    }
}
