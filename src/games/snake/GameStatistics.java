package games.snake;

public class GameStatistics {
    private int score;

    public GameStatistics() {
        this.score = 0;
    }

    public void updateScore(Snake snake) {
        score = snake.getBody().size() - 1;
    }

    public int getScore() {
        return score;
    }
}
