import games.Pong;
import games.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameLibraryPanel extends JPanel {
    public GameLibraryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Game Library");
        title.setFont(new Font("Varela Round", Font.BOLD, 35));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        JPanel games = new JPanel();
        games.setLayout(new FlowLayout(FlowLayout.CENTER));
        games.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon icon = createImageIcon("icons/tic_tac_toe_button.png");
        ImageIcon icon1 = createImageIcon("icons/pong_button.png");

        if (icon != null) {
            JButton tic_tac_toe_button = new JButton();
            JButton pong_button = new JButton("Test Button");
            JButton test2 = new JButton("Test Button");

            tic_tac_toe_button.setIcon(icon);
            tic_tac_toe_button.setPreferredSize(new Dimension(50, 50));
            tic_tac_toe_button.setMargin(new Insets(0, 0, 0, 0));
            tic_tac_toe_button.addActionListener(new tic_tac_toe_listener());

            pong_button.setIcon(icon1);
            pong_button.setPreferredSize(new Dimension(50, 50));
            pong_button.setMargin(new Insets(0, 12, 0, 0));
            pong_button.addActionListener(new pong_listener());

            games.add(tic_tac_toe_button);
            games.add(pong_button);
            games.add(test2);
        } else {
            System.err.println("Couldn't find image file");
        }
        add(games);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.decode("#9debe9"));
        g.fillRect(0, 0, 800, 50);
    }

    protected ImageIcon createImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public class pong_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Pong");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new Pong());
            frame.pack();
            frame.setVisible(true);
        }
    }

    public class tic_tac_toe_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new TicTacToe());
            frame.pack();
            frame.setVisible(true);
        }
    }
}
