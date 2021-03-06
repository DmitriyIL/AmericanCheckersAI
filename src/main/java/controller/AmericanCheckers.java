/*
 * Description: This class contains the main method to create the GUI and
 * start the checkers game.
 * ============================================================================
 * JAVA CHECKERS
 * -------------
 * Description: This program is a simple implementation of the standard
 * checkers game, with standard rules, in Java.
 * 
 * RULES
 * >>>>>>>>>>>>>
 * 1.	Checkers can only move diagonally, on dark tiles.
 * 
 * 2.	Normal checkers can only move forward diagonally (for black checkers,
 * 		this is down and for white checkers, this is up).
 * 
 * 3.	A checker becomes a king when it reaches the opponents end and cannot
 * 		move forward anymore.
 * 
 * 4.	Once a checker becomes a king, the player's turn is over.
 * 
 * 5.	After a checker/king moves one space diagonally, the player's turn is
 * 		over.
 * 
 * 6.	If an opponent's checker/king can be skipped, it must be skipped.
 * 
 * 7.	If after a skip, the same checker can skip again, it must. Otherwise,
 * 		the turn is over.
 * 
 * 8.	The game is over if a player either has no more checkers or cannot make
 * 		a move on their turn.
 * 
 * 9.	The player with the black checkers moves first.
 */

package controller;

import model.Player;
import view.MainFrame;

import javax.swing.*;

public class AmericanCheckers {

	private Player blackPlayer;
	private Player whitePlayer;

	private MainFrame mainFrame;

	public static void main(String[] args) {
	    new AmericanCheckers();
	}

	private AmericanCheckers() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(() -> mainFrame = new MainFrame(AmericanCheckers.this));

		blackPlayer = PlayerType.HUMAN.getPlayer();
		whitePlayer = PlayerType.HUMAN.getPlayer();
	}

	public Player getBlackPlayer() {
		return blackPlayer;
	}

	public Player getWhitePlayer() {
		return whitePlayer;
	}

	public void setBlackPlayer(Player blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public void setWhitePlayer(Player wightPlayer) {
		this.whitePlayer = wightPlayer;
	}
}
