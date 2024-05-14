package games.twentyfortyeight;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                Game.BOARD.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                Game.BOARD.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                Game.BOARD.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                Game.BOARD.moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                Game.WINDOW.dispose();
                break;
            default:
                break;
        }

        Game.BOARD.isGameOver();
        Game.WINDOW.repaint();

    }

    public void bind() {
        Game.WINDOW.addKeyListener(this);
    }

    public void unbind() {
        Game.WINDOW.removeKeyListener(this);
    }

}