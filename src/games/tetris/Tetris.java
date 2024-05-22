package games.tetris;

import java.awt.FlowLayout;
import javax.swing.*;

public class Tetris {

    public static void main(String[] args) {
        SidePanel sidePanel = new SidePanel();
        Board board = new Board(sidePanel);
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(board);
        frame.getContentPane().add(sidePanel);
        frame.setLocation(700,50);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
    }
}