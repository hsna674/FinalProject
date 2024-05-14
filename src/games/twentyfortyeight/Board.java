package games.twentyfortyeight;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int size; // size of the grid
    private int score; // game score
    private int emptyTiles; // number of tiles with zero value
    private int initTiles = 2; // number of tiles board starts with (usually two tiles)
    private boolean gameover = false; // game is over when 2048 tile is found
    private String wonOrLost; // won or lost
    private boolean genNewTile = false; // generate new tile when any tile moved
    private List<List<Tile>> tiles; // board

    public Board(int size) {
        super();
        this.size = size;
        this.emptyTiles = this.size * this.size;
        this.tiles = new ArrayList<>();
        start();
    }

    private void initialize() {
        for (int row = 0; row < this.size; row++) {
            tiles.add(new ArrayList<Tile>());
            for (int col = 0; col < this.size; col++) {
                tiles.get(row).add(new Tile());
            }
        }
    }

    private void start() {
        Game.CONTROLS.bind();
        initialize();
        genInitTiles();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(List<List<Tile>> tiles) {
        this.tiles = tiles;
    }

    public Tile getTileAt(int row, int col) {
        return tiles.get(row).get(col);
    }

    public void setTileAt(int row, int col, Tile t) {
        tiles.get(row).set(col, t);
    }

    public void remTileAt(int row, int col) {
        tiles.get(row).remove(col);
    }

    public int getScore() {
        return score;
    }

    private List<Tile> mergeTiles(List<Tile> sequence) {
        for (int l = 0; l < sequence.size() - 1; l++) {
            if (sequence.get(l).getValue() == sequence.get(l + 1).getValue()) {
                int value;
                if ((value = sequence.get(l).merging()) == 2048) {
                    gameover = true;
                }
                score += value;
                sequence.remove(l + 1);
                genNewTile = true; // board has changed its state
                emptyTiles++;
            }
        }
        return sequence;
    }

    private List<Tile> addEmptyTilesFirst(List<Tile> merged) {
        for (int k = merged.size(); k < size; k++) {
            merged.add(0, new Tile());
        }
        return merged;
    }

    private List<Tile> addEmptyTilesLast(List<Tile> merged) { // boolean last/first
        for (int k = merged.size(); k < size; k++) {
            merged.add(k, new Tile());
        }
        return merged;
    }

    private List<Tile> removeEmptyTilesRows(int row) {

        List<Tile> moved = new ArrayList<>();

        for (int col = 0; col < size; col++) {
            if (!getTileAt(row, col).isEmpty()) { // NOT empty
                moved.add(getTileAt(row, col));
            }
        }

        return moved;
    }

    private List<Tile> removeEmptyTilesCols(int row) {

        List<Tile> moved = new ArrayList<>();

        for (int col = 0; col < size; col++) {
            if (!getTileAt(col, row).isEmpty()) { // NOT empty
                moved.add(getTileAt(col, row));
            }
        }

        return moved;
    }

    private List<Tile> setRowToBoard(List<Tile> moved, int row) {
        for (int col = 0; col < tiles.size(); col++) {
            if (moved.get(col).hasMoved(row, col)) {
                genNewTile = true;
            }
            setTileAt(row, col, moved.get(col));
        }

        return moved;
    }

    private List<Tile> setColToBoard(List<Tile> moved, int row) {
        for (int col = 0; col < tiles.size(); col++) {
            if (moved.get(col).hasMoved(col, row)) {
                genNewTile = true;
            }
            setTileAt(col, row, moved.get(col));
        }

        return moved;
    }

    public void moveUp() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesCols(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesLast(moved);
            moved = setColToBoard(moved, row);

        }

    }

    public void moveDown() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesCols(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesFirst(moved);
            moved = setColToBoard(moved, row);

        }

    }

    public void moveLeft() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesRows(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesLast(moved);
            moved = setRowToBoard(moved, row);

        }

    }

    public void moveRight() {

        List<Tile> moved;

        for (int row = 0; row < size; row++) {

            moved = removeEmptyTilesRows(row);
            moved = mergeTiles(moved);
            moved = addEmptyTilesFirst(moved);
            moved = setRowToBoard(moved, row);

        }

    }

    public void isGameOver() {

        if (gameover) {
            // vyhrál jsi (na desce je dláždice 2048)
            // end(true);
            setWonOrLost("WON");
        } else {
            if (isFull()) {
                if (!isMovePossible()) {
                    // you lost (board is full with no tiles to merge)
                    // end(false);
                    setWonOrLost("LOST");
                }

            } else {
                newRandomTile(); // game continues
            }
        }
    }

    private boolean isFull() {
        return emptyTiles == 0;
    }

    private boolean isMovePossible() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size - 1; col++) {
                if (getTileAt(row, col).getValue() == getTileAt(row, col + 1).getValue()) {
                    return true;
                }
            }
        }

        for (int row = 0; row < size - 1; row++) {
            for (int col = 0; col < size; col++) {
                if (getTileAt(col, row).getValue() == getTileAt(col, row + 1).getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void genInitTiles() {
        for (int i = 0; i < initTiles; i++) {
            genNewTile = true;
            newRandomTile();
        }
    }

    private void newRandomTile() {
        if (genNewTile) {
            int row;
            int col;
            int value = Math.random() < 0.9 ? 2 : 4;
            do {
                row = (int) (Math.random () * 4);
                col = (int) (Math.random () * 4);
            } while (getTileAt(row, col).getValue() != 0);
            setTileAt(row, col, new Tile(value, row, col));
            emptyTiles--;
            genNewTile = false;
        }
    }

    protected void show() {
        for (int i = 0; i < 2; ++i) System.out.println();
        System.out.println("SCORE: " + score);
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                System.out.format("%-5d", getTileAt(i, j).getValue());
            }
            System.out.println();
        }
    }

    public String getWonOrLost() {
        return wonOrLost;
    }

    public void setWonOrLost(String wonOrLost) {
        this.wonOrLost = wonOrLost;
    }

}