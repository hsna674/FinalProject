package games.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class SidePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private boolean paused = false;
    private boolean started = false;

    private JLabel labelScore;
    private Label labelScoreUpdate;
    private JButton startButton;
    private JButton pauseButton;

    private Board board;

    private int score = 0;

    public SidePanel() {
        this.setBackground(new Color(0, 0, 50));
        this.setPreferredSize(new Dimension(210, 400));
        this.setBorder(BorderFactory.createTitledBorder(
                null,
                "Tetris",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Berlin Sans FB Demi", Font.PLAIN, 12),
                Color.white)
        );

        initComponents();
    }

    private void initComponents() {
        labelScore = new JLabel();
        labelScoreUpdate = new Label();
        startButton = new JButton();
        pauseButton = new JButton();

        labelScore.setHorizontalAlignment(SwingConstants.CENTER);
        labelScore.setForeground(new Color(255, 255, 255));
        labelScore.setFont(new Font("Berlin Sans FB Demi", 1, 15));
        labelScore.setText("Score");

        labelScoreUpdate.setAlignment(Label.CENTER);
        labelScoreUpdate.setBackground(new Color(150, 150, 255));
        labelScoreUpdate.setFont(new Font("Berlin Sans FB Demi", 1, 15));
        labelScoreUpdate.setText(score + "");

        startButton.setText("Start");
        startButton.addActionListener(evt -> startAction());

        pauseButton.setText("Pause");
        pauseButton.addActionListener(evt -> pauseAction());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelScore, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(labelScoreUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(labelScore, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(labelScoreUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(pauseButton)
                                        .addComponent(startButton))
                                .addContainerGap(220, Short.MAX_VALUE))
        );
    }

    public void setScore(int s) {
        score = s;
        labelScoreUpdate.setText(s + "");
    }

    public void setBoard(Board b) {
        board = b;
    }

    private void setStart(boolean state) {
        started = state;
    }

    private boolean isStarted() {
        return started;
    }

    private void startAction() {
        if (!isStarted()) {
            board.startGame();
            startButton.setText("Reset");
            setStart(true);
        }
        else if (isStarted()) {
            board.resetGame();
            startButton.setText("Start");
            setStart(false);
        }
    }

    private void setPaused(boolean state) {
        paused = state;
    }

    private boolean isPaused() {
        return paused;
    }

    private void pauseAction() {
        if (started && !board.isGameOver()) {
            if (!isPaused()) {
                board.setAnimation(false);
                pauseButton.setText("Resume");
                setPaused(true);
            }
            else if (isPaused()) {
                board.setAnimation(true);
                pauseButton.setText("Pause");
                setPaused(false);
            }
        }
    }
}