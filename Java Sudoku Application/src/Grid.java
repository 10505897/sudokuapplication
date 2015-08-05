
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Grid extends JPanel implements Observer {

    private Cell[][] cells;
    private JPanel[][] borders;


    public Grid() {
        super(new GridLayout(3, 3));

        borders = new JPanel[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
            	borders[y][x] = new JPanel(new GridLayout(3, 3));
            	borders[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                add(borders[y][x]);
            }
        }

        cells = new Cell[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                cells[y][x] = new Cell(x, y);
                borders[y / 3][x / 3].add(cells[y][x]);
            }
        }
    }

    public void update(Observable o, Object arg) {
        switch ((Refresh)arg) {
            case NEW_GAME:
                setGame((Game)o);
                break;
            case CHECK_GAME:
                setCheck((Game)o);
                break;
            case SELECTED_NUMBER:
            case POT_NUMBERS:
            case HELP:
                setPotNumbers((Game)o);
                break;
        }
    }

    public void setGame(Game game) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                cells[y][x].setBackground(Color.WHITE);
                cells[y][x].setNumber(game.getNum(x, y), false);
            }
        }
    }

    private void setCheck(Game game) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                cells[y][x].setBackground(Color.WHITE);
                if (cells[y][x].getForeground().equals(Color.LIGHT_GRAY))
                    cells[y][x].setBackground(game.isCheckValid(x, y) ? Color.GREEN : Color.RED);
            }
        }
    }

    private void setPotNumbers(Game game) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                cells[y][x].setBackground(Color.WHITE);
                if (game.isHelp() && game.isSelNumPossible(x, y))
                    cells[y][x].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public void setControls(Controls gridControl) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++)
            	borders[y][x].addMouseListener(gridControl);
        }
    }
}