package games.twentyfortyeight;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Grid extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int TILE_RADIUS = 15;
    private static final int WIN_MARGIN = 20;
    private static final int TILE_SIZE = 65;
    private static final int TILE_MARGIN = 15;
    private static final String FONT = "Tahoma";

    public Grid() {
        super(true); // turn on doublebuffering
    }

    public void paintComponent(Graphics g2) {
        super.paintComponent(g2);

        Graphics2D g = ((Graphics2D) g2); // cast to get context for drawing

        /* turn on antialiasing for smooth and non-pixelated edges */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawBackground(g);
        drawTitle(g);
        drawScoreBoard(g);
        drawBoard(g);

        g.dispose(); // release memory
    }

    private static void drawTitle(Graphics g) {
        g.setFont(new Font(FONT, Font.BOLD, 38));
        g.setColor(ColorScheme.BRIGHT);
        g.drawString("2048", WIN_MARGIN, 50);
    }

    private void drawScoreBoard(Graphics2D g) {
        int width = 80;
        int height = 40;
        int xOffset = Game.WINDOW.getWidth() - WIN_MARGIN - width;
        int yOffset = 20;
        g.fillRoundRect(xOffset, yOffset, width, height, TILE_RADIUS, TILE_RADIUS);
        g.setFont(new Font(FONT, Font.BOLD, 10));
        g.setColor(new Color(0XFFFFFF));
        g.drawString("SCORE", xOffset + 22, yOffset + 15);
        g.setFont(new Font(FONT, Font.BOLD, 12));
        g.drawString(String.valueOf(Game.BOARD.getScore()), xOffset + 35, yOffset + 30);
    }

    private static void drawBackground(Graphics g) {
        g.setColor(ColorScheme.WINBG);
        g.fillRect(0, 0, Game.WINDOW.getWidth(), Game.WINDOW.getHeight());
    }

    private static void drawBoard(Graphics g) {
        g.translate(WIN_MARGIN, 80);
        g.setColor(ColorScheme.GRIDBG);
        g.fillRoundRect(0, 0, Game.WINDOW.getWidth() - (WIN_MARGIN * 2), 320 + TILE_MARGIN, TILE_RADIUS, TILE_RADIUS);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                drawTile(g, Game.BOARD.getTileAt(row, col), col, row);
            }
        }
    }

    private static void drawTile(Graphics g, Tile tile, int x, int y) {
        int value = tile.getValue();
        int xOffset = x * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
        int yOffset = y * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
        g.setColor(Game.COLORS.getTileBackground(value));
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, TILE_RADIUS, TILE_RADIUS);

        g.setColor(Game.COLORS.getTileColor(value));

        final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
        final Font font = new Font(FONT, Font.BOLD, size);
        g.setFont(font);

        String s = String.valueOf(value);
        final FontMetrics fm = g.getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (value != 0) {
            Game.BOARD.getTileAt(y, x).setPosition(y, x); // tile gets its new position
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
        }


        if (Game.BOARD.getWonOrLost() != null && !Game.BOARD.getWonOrLost().isEmpty()) {
            g.setColor(new Color(255, 255, 255, 40));
            g.fillRect(0, 0, Game.WINDOW.getWidth(), Game.WINDOW.getHeight());
            g.setColor(ColorScheme.BRIGHT);
            g.setFont(new Font(FONT, Font.BOLD, 30));
            g.drawString("You " + Game.BOARD.getWonOrLost() + "!", 68, 150);
            Game.CONTROLS.unbind();
        }


    }

}