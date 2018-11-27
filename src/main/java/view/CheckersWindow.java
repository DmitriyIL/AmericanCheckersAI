package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Player;

/**
 * The class is responsible for managing a window. This window contains
 * a game of checkers and also options to change the settings
 * of the game with an {@link OptionPanel}.
 */
public class CheckersWindow extends JFrame {

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String DEFAULT_TITLE = "Java Checkers";

    /**
     * The checker board component playing the updatable game.
     */
    private CheckerBoard board;

    private OptionPanel opts;

    public CheckersWindow() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
    }

    public CheckersWindow(int width, int height, String title) {

        // Setup the window
        super(title);
        super.setSize(width, height);
        super.setLocationByPlatform(true);

        // Setup the components
        JPanel layout = new JPanel(new BorderLayout());
        this.board = new CheckerBoard(this);
        this.opts = new OptionPanel(this);
        layout.add(board, BorderLayout.CENTER);
        layout.add(opts, BorderLayout.SOUTH);
        this.add(layout);
    }

    /**
     * Updates the type of player that is being used for player 1.
     *
     * @param player1 the new player instance to control player 1.
     */
    public void setPlayer1(Player player1) {
        this.board.setPlayer1(player1);
        this.board.update();
    }

    /**
     * Updates the type of player that is being used for player 2.
     *
     * @param player2 the new player instance to control player 2.
     */
    public void setPlayer2(Player player2) {
        this.board.setPlayer2(player2);
        this.board.update();
    }

    /**
     * Resets the game of checkers in the window.
     */
    public void restart() {
        this.board.getGame().restart();
        this.board.update();
    }

    public void setGameState(String state) {
        this.board.getGame().setGameState(state);
    }
}
