package games.twentyfortyeight;

public class Tile {

    private int value;
    private int row;
    private int col;

    public Tile(int value) {
        super();
        this.value = value;
    }

    public Tile(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public Tile() {
        new Tile(0);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int merging() {
        return (this.value += this.value);
    }

    public boolean hasMoved(int row, int col) {
        return (!isEmpty() && ((this.row != row) || (this.col != col)));
    }

    public boolean isEmpty() {
        return (value == 0);
    }

    @Override
    public String toString() {
        return "Tile [value=" + value + "]";
    }

}