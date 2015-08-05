
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Buttons extends JPanel implements Observer {
    JButton btnNewGame, btnCheckGame, btnExitGame;
    JCheckBox cbHelp;
    ButtonGroup bgNumbers;
    JToggleButton[] btnNumbers;

    public Buttons() {
        super(new BorderLayout());

        JPanel pAlign = new JPanel();
        pAlign.setLayout(new BoxLayout(pAlign, BoxLayout.PAGE_AXIS));
        add(pAlign, BorderLayout.NORTH);

        JPanel pGameManagement = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pGameManagement.setBorder(BorderFactory.createTitledBorder(" Game Management "));
        pAlign.add(pGameManagement);

        btnNewGame = new JButton("New Game");
        btnNewGame.setFocusable(false);
        pGameManagement.add(btnNewGame);

        btnCheckGame = new JButton("Check Game");
        btnCheckGame.setFocusable(false);
        pGameManagement.add(btnCheckGame);

        btnExitGame = new JButton("Exit Game");
        btnExitGame.setFocusable(false);
        pGameManagement.add(btnExitGame);

        JPanel pKeypad = new JPanel();
        pKeypad.setLayout(new BoxLayout(pKeypad, BoxLayout.PAGE_AXIS));
        pKeypad.setBorder(BorderFactory.createTitledBorder(" Keypad "));
        pAlign.add(pKeypad);

        JPanel pHelp = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pKeypad.add(pHelp);

        cbHelp = new JCheckBox("Help", true);
        cbHelp.setFocusable(false);
        pHelp.add(cbHelp);

        JPanel pNumbers = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pKeypad.add(pNumbers);

        bgNumbers = new ButtonGroup();
        btnNumbers = new JToggleButton[9];
        for (int i = 0; i < 9; i++) {
            btnNumbers[i] = new JToggleButton("" + (i + 1));
            btnNumbers[i].setPreferredSize(new Dimension(40, 40));
            btnNumbers[i].setFocusable(false);
            bgNumbers.add(btnNumbers[i]);
            pNumbers.add(btnNumbers[i]);
        }
    }

    public void update(Observable o, Object arg) {
        switch ((Refresh)arg) {
            case NEW_GAME:
            case CHECK_GAME:
                bgNumbers.clearSelection();
                break;
            case SELECTED_NUMBER:
    			break;
    		case POT_NUMBERS:
    			break;
    		case HELP:
    			break;
        }
    }

    public void setController(Controls buttonControl) {
        btnNewGame.addActionListener(buttonControl);
        btnCheckGame.addActionListener(buttonControl);
        btnExitGame.addActionListener(buttonControl);
        cbHelp.addActionListener(buttonControl);
        for (int i = 0; i < 9; i++)
            btnNumbers[i].addActionListener(buttonControl);
    }
}