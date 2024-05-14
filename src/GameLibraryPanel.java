import games.Battleship;
import games.ConnectFour;
import games.Pong;
import games.TicTacToe;
import games.twentyfortyeight.Game;

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

        createGameButton("icons/tic_tac_toe_button.png", "Tic Tac Toe", new TicTacToeListener(), games, new Insets(0, 12, 0, 0));
        createGameButton("icons/pong_button.png", "Pong", new PongListener(), games, new Insets(0, 12, 0, 0));
        createGameButton("icons/pong_button.png", "Connect 4", new ConnectFourListener(), games, new Insets(0, 12, 0, 0));
        createGameButton("icons/battleship_button.png", "Battleship", new BattleshipListener(), games, new Insets(0, 12, 0, 0));

        JButton twentyFortyEight = new JButton("2048");
        twentyFortyEight.setIcon(new ImageIcon(getClass().getResource("icons/pong_button.png")));
        twentyFortyEight.setPreferredSize(new Dimension(50, 50));
        twentyFortyEight.setMargin(new Insets(0, 12, 0, 0));
        twentyFortyEight.addActionListener(new twentyFortyEightListener());
        games.add(twentyFortyEight);

        add(games);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.decode("#9debe9"));
        g.fillRect(0, 0, 800, 50);
    }

    protected void createGameButton(String iconPath, String buttonText, ActionListener listener, JPanel panel, Insets margin) {
        ImageIcon icon = createImageIcon(iconPath);
        if (icon != null) {
            JButton button = new JButton(buttonText);
            button.setIcon(icon);
            button.setPreferredSize(new Dimension(50, 50));
            button.setMargin(margin);
            button.addActionListener(listener);
            panel.add(button);
        } else {
            System.err.println("Couldn't find image file: " + iconPath);
        }
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

    public abstract class GameListener implements ActionListener {
        protected String gameName;

        public GameListener(String gameName) {
            this.gameName = gameName;
        }

        protected void createGameFrame(JComponent gameComponent) {
            JFrame frame = new JFrame(gameName);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.add(gameComponent);
            frame.pack();
            frame.setVisible(true);
        }
    }

    public class TicTacToeListener extends GameListener {
        public TicTacToeListener() {
            super("Tic Tac Toe");
        }

        public void actionPerformed(ActionEvent e) {
            createGameFrame(new TicTacToe());
        }
    }

    public class PongListener extends GameListener {
        public PongListener() {
            super("Pong");
        }

        public void actionPerformed(ActionEvent e) {
            createGameFrame(new Pong());
        }
    }

    public class ConnectFourListener extends GameListener {
        public ConnectFourListener() {
            super("Connect Four");
        }

        public void actionPerformed(ActionEvent e) {
            createGameFrame(new ConnectFour());
        }
    }

    public class BattleshipListener extends GameListener {
        public BattleshipListener() {
            super("Battleship");
        }

        public void actionPerformed(ActionEvent e) {
            createGameFrame(new Battleship());
        }
    }

    public class twentyFortyEightListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new Game();
        }
    }
}
