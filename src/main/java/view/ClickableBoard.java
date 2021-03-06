/* Description: This class is the graphical user interface representation of
 * a checkers game. It is responsible for drawing the checker board and
 * allowing moves to be made. It does not provide a method to allow the user to
 * change settings of the game or restart it.
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.Timer;

import ai.MoveGenerator;
import model.Board;
import model.Game;
import model.HumanPlayer;
import model.Player;

/**
 * The {@code ClickableBoard} class is a graphical user interface component that
 * is capable of drawing any checkers game state. It also handles player turns.
 * For human players, this means interacting with and selecting tiles on the
 * checker board. For non-human players, this means using the ai implemented
 * by the specified player object itself is used.
 */
public class ClickableBoard extends JButton {
    /**
     * The amount of milliseconds before a computer player takes a move.
     */
    private static final int TIMER_DELAY = 1000;

    /**
     * The number of pixels of padding between this component's border and the
     * actual checker board that is drawn.
     */
    private static final int PADDING = 16;

    private Game game;

    private MainFrame mainFrame;

    private Player blackPlayer;
    private Player whitePlayer;

    /**
     * The last point that the current player have clicked on the checker board.
     */
    private Point clicked;

    /**
     * The flag to determine the color of the clicked tile. If the selection
     * is valid - a green color, otherwise - a red.
     */
    private boolean selectionValid;

    private boolean isGameOver;

    /**
     * The timer to control how fast a computer player makes a move.
     */
    private Timer timer;

    public ClickableBoard(MainFrame mainFrame) {
        this(mainFrame, new Game(), null, null);
    }

    public ClickableBoard(MainFrame mainFrame, Game game, Player blackPlayer, Player whitePlayer) {

        // Setup the component
        super.setFocusPainted(false);
        super.setContentAreaFilled(false);
        super.setBackground(Color.LIGHT_GRAY);
        this.addActionListener(new ClickListener());

        // Setup the game
        this.game = (game == null) ? new Game() : game;
        this.mainFrame = mainFrame;
        setBlackPlayer(blackPlayer);
        setWhitePlayer(whitePlayer);
    }

    /**
     * Checks if the game is over and redraws the component graphics.
     */
    public void update() {
        runPlayer();
        this.isGameOver = game.isGameOver();
        repaint();
    }

    private void runPlayer() {

        Player player = getCurrentPlayer();
        if (player == null || player.isHuman()) {
            return;
        }

        // Set a timer to run
        this.timer = new Timer(TIMER_DELAY, e -> {
            getCurrentPlayer().updateGame(game);
            timer.stop();
            update();
        });
        this.timer.start();
    }

    public boolean setGameState(boolean testValue, String newState, String expected) {

        // Test the value if requested
        if (testValue && !game.getGameState().equals(expected)) {
            return false;
        }

        // Update the game state
        game.setGameState(newState);
        repaint();

        return true;
    }

    /**
     * Draws the current checkers game state.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Game game = this.game.copy();

        // Perform calculations
        final int BOX_PADDING = 4;
        final int W = getWidth(), H = getHeight();
        final int DIM = (W < H) ? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
        final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
        final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
        final int CHECKER_SIZE = Math.max(0, BOX_SIZE - 2 * BOX_PADDING);

        // Draw checker board
        g.setColor(Color.BLACK);
        g.drawRect(OFFSET_X - 1, OFFSET_Y - 1, BOX_SIZE * 8 + 1, BOX_SIZE * 8 + 1);
        g.setColor(Color.WHITE);
        //g.fillRect(OFFSET_X, OFFSET_Y, BOX_SIZE * 8, BOX_SIZE * 8);
        g.setColor(Color.BLACK);
        for (int y = 0; y < 8; y++) {
            for (int x = (y + 1) % 2; x < 8; x += 2) {
                g.fillRect(OFFSET_X + x * BOX_SIZE, OFFSET_Y + y * BOX_SIZE,
                        BOX_SIZE, BOX_SIZE);
            }
        }

        // Highlight the clicked tile if valid
        if (Board.isValidPoint(clicked)) {
            g.setColor(selectionValid ? Color.GREEN : Color.RED);
            g.fillRect(OFFSET_X + clicked.x * BOX_SIZE,
                    OFFSET_Y + clicked.y * BOX_SIZE,
                    BOX_SIZE, BOX_SIZE);
        }

        // Draw the checkers
        Board board = game.getBoard();
        for (int y = 0; y < 8; y++) {
            int cy = OFFSET_Y + y * BOX_SIZE + BOX_PADDING;
            for (int x = (y + 1) % 2; x < 8; x += 2) {
                int id = board.get(x, y);

                // Empty, just skip
                if (id == Board.EMPTY) {
                    continue;
                }

                int cx = OFFSET_X + x * BOX_SIZE + BOX_PADDING;

                // Black checker
                if (id == Board.BLACK_CHECKER) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.BLACK);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                }

                // Black king
                else if (id == Board.BLACK_KING) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.BLACK);
                    g.fillOval(cx - 1, cy - 2, CHECKER_SIZE, CHECKER_SIZE);
                }

                // White checker
                else if (id == Board.WHITE_CHECKER) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.WHITE);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                }

                // White king
                else if (id == Board.WHITE_KING) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawOval(cx + 1, cy + 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.WHITE);
                    g.fillOval(cx - 1, cy - 2, CHECKER_SIZE, CHECKER_SIZE);
                }

                // Any king (add some extra highlights)
                if (id == Board.BLACK_KING || id == Board.WHITE_KING) {
                    g.setColor(new Color(255, 240, 0));
                    g.drawOval(cx - 1, cy - 2, CHECKER_SIZE, CHECKER_SIZE);
                    g.drawOval(cx + 1, cy, CHECKER_SIZE - 4, CHECKER_SIZE - 4);
                }
            }
        }

        // Draw the player turn sign
        String msg = game.isP1Turn() ? "Player 1's turn" : "Player 2's turn";
        int width = g.getFontMetrics().stringWidth(msg);
        Color back = game.isP1Turn() ? Color.BLACK : Color.WHITE;
        Color front = game.isP1Turn() ? Color.WHITE : Color.BLACK;
        g.setColor(back);
        g.fillRect(W / 2 - width / 2 - 5, OFFSET_Y + 8 * BOX_SIZE + 2,
                width + 10, 15);
        g.setColor(front);
        g.drawString(msg, W / 2 - width / 2, OFFSET_Y + 8 * BOX_SIZE + 2 + 11);

        // Draw a game over sign
        if (isGameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            msg = "Game Over!";
            width = g.getFontMetrics().stringWidth(msg);
            g.setColor(new Color(240, 240, 255));
            g.fillRoundRect(W / 2 - width / 2 - 5,
                    OFFSET_Y + BOX_SIZE * 4 - 16,
                    width + 10, 30, 10, 10);
            g.setColor(Color.RED);
            g.drawString(msg, W / 2 - width / 2, OFFSET_Y + BOX_SIZE * 4 + 7);
        }
    }

    public Game getGame() {
        return game;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = (blackPlayer == null) ? new HumanPlayer() : blackPlayer;
        if (game.isP1Turn() && !this.blackPlayer.isHuman()) {
            this.clicked = null;
        }
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = (whitePlayer == null) ? new HumanPlayer() : whitePlayer;
        if (!game.isP1Turn() && !this.whitePlayer.isHuman()) {
            this.clicked = null;
        }
    }

    public Player getCurrentPlayer() {
        return game.isP1Turn() ? blackPlayer : whitePlayer;
    }

    /**
     * Handles a click on this component at the specified point. If the current
     * player is not human, this method does nothing. Otherwise, the clicked
     * point is updated and a move is attempted if the last click and this one
     * both are on black tiles.
     *
     * @param x the x-coordinate of the click on this component.
     * @param y the y-coordinate of the click on this component.
     */
    private void handleClick(int x, int y) {
        Game copy = game.copy();

        // Determine what square (if any) was clicked
        final int W = getWidth(), H = getHeight();
        final int DIM = W < H ? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
        final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
        final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
        x = (x - OFFSET_X) / BOX_SIZE;
        y = (y - OFFSET_Y) / BOX_SIZE;
        Point sel = new Point(x, y);

        // Determine if a move should be attempted
        if (Board.isValidPoint(sel) && Board.isValidPoint(clicked)) {
            boolean change = copy.isP1Turn();
            String expected = copy.getGameState();
            boolean move = copy.makeMove(clicked, sel);
            boolean updated = (move && setGameState(true, copy.getGameState(), expected));
            change = (copy.isP1Turn() != change);
            this.clicked = change ? null : sel;
        } else {
            this.clicked = sel;
        }

        // Check if the selection is valid
        this.selectionValid = isValidSelection(
                copy.getBoard(), copy.isP1Turn(), clicked);

        update();
    }

    /**
     * Checks if a clicked point is valid in the context of the current
     * player's turn.
     *
     * @param board    the current board.
     * @param isP1Turn the flag indicating if it is player 1's turn.
     * @param selected the point to test.
     * @return true if and only if the clicked point is a checker that would
     * be allowed to make a move in the current turn.
     */
    private boolean isValidSelection(Board board, boolean isP1Turn, Point selected) {

        // Trivial cases
        int i = Board.toTileIndex(selected), id = board.get(i);
        if (id == Board.EMPTY || id == Board.INVALID) { // no checker here
            return false;
        } else if (isP1Turn ^ (id == Board.BLACK_CHECKER || id == Board.BLACK_KING)) { // wrong checker
            return false;
        } else if (!MoveGenerator.getSkips(board, i).isEmpty()) { // skip available
            return true;
        } else if (MoveGenerator.getMoves(board, i).isEmpty()) { // no moves
            return false;
        }

        // Determine if there is a skip available for another checker
        List<Point> points = board.find(isP1Turn ? Board.BLACK_CHECKER : Board.WHITE_CHECKER);
        points.addAll(board.find(isP1Turn ? Board.BLACK_KING : Board.WHITE_KING));
        for (Point p : points) {
            int checker = Board.toTileIndex(p);
            if (checker == i) {
                continue;
            }
            if (!MoveGenerator.getSkips(board, checker).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * The {@code ClickListener} class is responsible for responding to click
     * events on the checker board component. It uses the coordinates of the
     * mouse relative to the location of the checker board component.
     */
    private class ClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Get the new mouse coordinates and handle the click
            Point m = ClickableBoard.this.getMousePosition();
            if (m != null) {
                handleClick(m.x, m.y);
            }
        }
    }
}
