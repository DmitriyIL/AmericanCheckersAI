/*
 * Description: This class is a user interface to interact with a checkers
 * game window.
 */

package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.PlayerType;
import model.Player;

/**
 * The {@code OptionPanel} class provides a user interface component to control
 * options for the game of checkers being played in the window.
 */
public class OptionPanel extends JPanel {


    private static final String[] playerTypes = PlayerType.getStrValues();

	private MainFrame mainFrame;

	private JButton restartBtn;
	private JComboBox<String> bPlayerOpts;
	private JComboBox<String> wPlayerOpts;

	private ActionListener bPlayerOptsListener, wPlayerOptsListener, restartListener;

	/**
	 * Creates a new option panel for the specified checkers window.
	 * 
	 * @param mainFrame	the window with the game of checkers to update.
	 */
	public OptionPanel(MainFrame mainFrame) {
		super(new GridLayout(0, 1));

		this.mainFrame = mainFrame;

		initListeners();

		// Initialize the components
		this.restartBtn = new JButton("Restart");
		this.bPlayerOpts = new JComboBox<>(playerTypes);
		this.wPlayerOpts = new JComboBox<>(playerTypes);
		this.restartBtn.addActionListener(restartListener);
		this.bPlayerOpts.addActionListener(bPlayerOptsListener);
		this.wPlayerOpts.addActionListener(wPlayerOptsListener);
		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		// Add components to the layout
		top.add(restartBtn);
		middle.add(new JLabel("Black player: "));
		middle.add(bPlayerOpts);
		bottom.add(new JLabel("White Player: "));
		bottom.add(wPlayerOpts);
		this.add(top);
		this.add(middle);
		this.add(bottom);
	}

	private void initListeners() {
        restartListener = e -> mainFrame.restart();

        bPlayerOptsListener = e -> {
            String playerTypeName = (String) bPlayerOpts.getSelectedItem();
            Player player = PlayerType.getPlayerFromTypeName(playerTypeName);
            mainFrame.setBlackPlayer(player);
        };

        wPlayerOptsListener = e -> {
            String playerTypeName = (String) wPlayerOpts.getSelectedItem();
            Player player = PlayerType.getPlayerFromTypeName(playerTypeName);
            mainFrame.setWhitePlayer(player);
        };
	}
}
