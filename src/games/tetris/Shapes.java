package games.tetris;

public class Shapes {

    private int[][] LShapeX = {{1, 0, 0, 0}, {0, 1, 2, 2}, {1, 1, 1, 0}, {0, 0, 1, 2}};
    private int[][] LShapeY = {{2, 2, 1, 0}, {1, 1, 1, 0}, {2, 1, 0, 0}, {1, 0, 0, 0}};

    private int[][] SShapeX = {{0, 1, 1, 2}, {1, 1, 0, 0}, {0, 1, 1, 2}, {1, 1, 0, 0}};
    private int[][] SShapeY = {{1, 1, 0, 0}, {2, 1, 1, 0}, {1, 1, 0, 0}, {2, 1, 1, 0}};

    private int[][] ZShapeX = {{2, 1, 1, 0}, {1, 1, 0, 0}, {2, 1, 1, 0}, {1, 1, 0, 0}};
    private int[][] ZShapeY = {{1, 1, 0, 0}, {0, 1, 1, 2}, {1, 1, 0, 0}, {0, 1, 1, 2}};

    private int[][] RevLShapeX = {{0, 1, 1, 1}, {2, 2, 1, 0}, {0, 0, 0, 1}, {2, 1, 0, 0}};
    private int[][] RevLShapeY = {{2, 2, 1, 0}, {1, 0, 0, 0}, {2, 1, 0, 0}, {1, 1, 1, 0}};

    private int[][] TShapeX = {{1, 2, 1, 0}, {0, 0, 0, 1}, {2, 1, 0, 1}, {1, 1, 1, 0}};
    private int[][] TShapeY = {{1, 0, 0, 0}, {2, 1, 0, 1}, {1, 1, 1, 0}, {2, 1, 0, 1}};

    private int[][] SquareShapeX = {{1, 0, 1, 0}, {1, 0, 1, 0}, {1, 0, 1, 0}, {1, 0, 1, 0}};
    private int[][] SquareShapeY = {{1, 1, 0, 0}, {1, 1, 0, 0}, {1, 1, 0, 0}, {1, 1, 0, 0}};

    private int[][] IShapeX = {{0, 0, 0, 0}, {0, 1, 2, 3}, {0, 0, 0, 0}, {0, 1, 2, 3}};
    private int[][] IShapeY = {{3, 2, 1, 0}, {0, 0, 0, 0}, {3, 2, 1, 0}, {0, 0, 0, 0}};

    private int[] shapePosition = {4, 0};

    private final String shape;
    private int angle;

    private boolean status;

    public Shapes(String shape, int angle) {
        this.shape = shape;
        this.angle = angle;
        this.status = false;
    }

    public int[] getPosition() {
        return shapePosition;
    }

    public void setStatus(boolean st) {
        this.status = st;
    }

    public boolean hasReached() {
        return this.status;
    }

    private int getMinX() {
        int[] tempX = getX();
        int minValue = tempX[0];
        for (int i = 1; i < tempX.length; i++) {
            if (tempX[i] < minValue) {
                minValue = tempX[i];
            }
        }
        return minValue;
    }

    private int getMaxX() {
        int[] tempX = getX();
        int maxValue = tempX[0];
        for (int i = 1; i < tempX.length; i++) {
            if (tempX[i] > maxValue) {
                maxValue = tempX[i];
            }
        }
        return maxValue;
    }

    private int getMaxY() {
        int[] tempY = getY();
        int maxValue = tempY[0];
        for (int i = 1; i < tempY.length; i++) {
            if (tempY[i] > maxValue) {
                maxValue = tempY[i];
            }
        }
        return maxValue;
    }

    public void dropElement(Board b) {
        int[] tempPosition = getPosition();

        int tempY = getMaxY();
        int bottom = tempY + tempPosition[1];

        boolean obs = false;
        boolean[][] squares = b.returnGrid();
        int[] X = getX();
        int[] Y = getY();
        for (int i = 0; i < X.length; i++) {
            int x = tempPosition[0] + X[i];
            int y = tempPosition[1] + Y[i];
            int temp = y + 1;
            if (temp < 20) {
                if (squares[temp][x]) {
                    obs = true;
                    break;
                }
            }
        }

        if (bottom < 19 && (!obs)) {
            shapePosition[1]++;
        } else {
            setStatus(true);
        }
    }

    public void moveRight(Board b) {
        int[] tempPosition = getPosition();

        int tempX = getMaxX();
        int right = tempX + tempPosition[0];

        boolean obs = false;
        boolean[][] squares = b.returnGrid();
        int[] X = getX();
        int[] Y = getY();
        for (int i = 0; i < X.length; i++) {
            int x = tempPosition[0] + X[i];
            int y = tempPosition[1] + Y[i];
            int temp = x + 1;
            if (temp < 10) {
                if (squares[y][temp]) {
                    obs = true;
                    break;
                }
            }
        }

        if ((!obs) && right < 9) {
            shapePosition[0]++;
        }
    }

    public void moveLeft(Board b) {
        int[] tempPosition = getPosition();

        int tempX = getMinX();
        int left = tempX + tempPosition[0];

        boolean obs = false;
        boolean[][] squares = b.returnGrid();
        int[] X = getX();
        int[] Y = getY();
        for (int i = 0; i < X.length; i++) {
            int x = tempPosition[0] + X[i];
            int y = tempPosition[1] + Y[i];
            int temp = x - 1;
            if (temp >= 0) {
                if (squares[y][temp]) {
                    obs = true;
                    break;
                }
            }
        }

        if ((!obs) && left > 0) {
            shapePosition[0]--;
        }
    }

    public int getAngle() {
        return this.angle;
    }

    private void rotationValidator(int a, Board b) {
        int[] p = getPosition();
        int[] x = getX();
        int[] y = getY();
        boolean[][] squares = b.returnGrid();
        boolean obs = false;
        for (int i = 0; i < 4; i++) {
            int tempX = p[0] + x[i];
            int tempY = p[1] + y[i];
            if (tempX > 9) {
                obs = true;
                break;
            }
            if (squares[tempY][tempX] || tempX > 9) {
                obs = true;
                break;
            }
        }
        if (obs) {
            this.angle = a;
        }
    }

    public void rotate(Board b) {
        int tempY = getMaxY();
        int[] tempPosition = getPosition();
        int bottom = tempY + tempPosition[1];
        int a = getAngle();
        if (bottom < 19) {
            if (a == 0) {
                this.angle = 3;
                rotationValidator(a, b);
            } else {
                this.angle--;
                rotationValidator(a, b);
            }
        }
    }

    public int[] getX() {
        switch (shape) {
            case "L":
                return this.LShapeX[angle];
            case "RL":
                return this.RevLShapeX[angle];
            case "S":
                return this.SShapeX[angle];
            case "Z":
                return this.ZShapeX[angle];
            case "T":
                return this.TShapeX[angle];
            case "SQ":
                return this.SquareShapeX[angle];
            case "I":
                return this.IShapeX[angle];
            default:
                return null;
        }
    }

    public int[] getY() {
        switch (shape) {
            case "L":
                return this.LShapeY[angle];
            case "RL":
                return this.RevLShapeY[angle];
            case "S":
                return this.SShapeY[angle];
            case "Z":
                return this.ZShapeY[angle];
            case "T":
                return this.TShapeY[angle];
            case "SQ":
                return this.SquareShapeY[angle];
            case "I":
                return this.IShapeY[angle];
            default:
                return null;
        }
    }
}