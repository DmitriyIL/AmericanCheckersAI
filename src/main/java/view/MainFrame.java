package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AmericanCheckers;
import model.Player;

/**
 * The class is responsible for managing a window. This window contains
 * a game of checkers and also options to change the settings
 * of the game with an {@link OptionPanel}.
 */
public class MainFrame extends JFrame {

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String DEFAULT_TITLE = "Java Checkers";

    private ClickableBoard clickableBoard;
    private OptionPanel opts;

    private AmericanCheckers controller;

    public MainFrame(AmericanCheckers controller) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
        this.controller = controller;
    }

    public MainFrame(int width, int height, String title) {

        // Setup the window
        super(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);

        // Setup the components
        JPanel layout = new JPanel(new BorderLayout());
        this.clickableBoard = new ClickableBoard(this);
        this.opts = new OptionPanel(this);
        layout.add(clickableBoard, BorderLayout.CENTER);
        layout.add(opts, BorderLayout.SOUTH);
        this.add(layout);

        setVisible(true);
    }

    /**
     * Updates the type of player that is being used for player 1.
     *
     * @param player the new player instance to control player 1.
     */
    public void setBlackPlayer(Player player) {
        controller.setBlackPlayer(player);
        clickableBoard.setBlackPlayer(player);
        clickableBoard.update();
    }

    /**
     * Updates the type of player that is being used for player 2.
     *
     * @param player the new player instance to control player 2.
     */
    public void setWhitePlayer(Player player) {
        controller.setWhitePlayer(player);
        clickableBoard.setWhitePlayer(player);
        clickableBoard.update();
    }

    /**
     * Resets the game of checkers in the window.
     */
    public void restart() {
        this.clickableBoard.getGame().restart();
        this.clickableBoard.update();
    }

    public void setGameState(String state) {
        this.clickableBoard.getGame().setGameState(state);
    }
}
