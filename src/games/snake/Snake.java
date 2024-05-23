package games.snake;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(5, 5));
        direction = Direction.RIGHT;
    }

    public void move() {
        Point head = body.getFirst();
        Point newPoint = null;
        switch (direction) {
            case UP: newPoint = new Point(head.x, head.y - 1); break;
            case DOWN: newPoint = new Point(head.x, head.y + 1); break;
            case LEFT: newPoint = new Point(head.x - 1, head.y); break;
            case RIGHT: newPoint = new Point(head.x + 1, head.y); break;
        }
        body.addFirst(newPoint);
        body.removeLast();
    }

    public void grow() {
        body.addLast(body.getLast());
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public void draw(Graphics g) {
        for (Point p : body) {
            if (p == body.getFirst()) {
                g.setColor(new Color(0x1E7D10));
                g.fillRect(p.x * 10, p.y * 10, 10, 10);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(p.x * 10, p.y * 10, 10, 10);
            }
        }
    }

    public boolean checkCollision(Point point) {
        return body.contains(point);
    }

    public boolean checkWallCollision(int width, int height) {
        Point head = body.getFirst();
        return head.x < 0 || head.x >= width / 10 || head.y < 0 || head.y >= height / 10;
    }

    public boolean checkSelfCollision() {
        Point head = body.getFirst();
        return body.stream().skip(1).anyMatch(p -> p.equals(head));
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT;
}
