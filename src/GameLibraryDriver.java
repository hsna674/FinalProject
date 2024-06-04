import javax.swing.*;
import java.util.Objects;

public class GameLibraryDriver {
    public static void main(String[] args){
        JFrame frame = new JFrame("Game Library");
        frame.setSize(800, 450);
        frame.setLocation(20, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(new GameLibraryPanel());
        frame.setVisible(true);
    }
}
