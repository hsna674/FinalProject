package games.snake;

import java.awt.*;
import java.util.Random;

public class Apple {
    private Point position;
    private Random random;

    public Apple() {
        random = new Random();
        position = new Point(random.nextInt(30), random.nextInt(30));
    }

    public Point getPosition() {
        return position;
    }

    public void relocate() {
        position.setLocation(random.nextInt(30), random.nextInt(30));
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(position.x * 10, position.y * 10, 10, 10);
    }
}
