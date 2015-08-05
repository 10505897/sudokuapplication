import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class Controls implements ActionListener, MouseListener {
    private Game one;

    public Controls(Game game) {
        this.one = game;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game"))
            one.nGame();
        else if (e.getActionCommand().equals("Check Game"))
            one.cGame();
        else if (e.getActionCommand().equals("Exit Game"))
            System.exit(0);
        else if (e.getActionCommand().equals("Help"))
            one.setHelp(((JCheckBox)e.getSource()).isSelected());
        else
            one.setSelNum(Integer.parseInt(e.getActionCommand()));
    }
    
    private Grid grid;
    private Game two;

    public Controls(Grid grid, Game game) {
        this.grid = grid;
        this.two = game;
    }

    public void mousePressed(MouseEvent e) {
        JPanel gpanel = (JPanel)e.getSource();
        Component component = gpanel.getComponentAt(e.getPoint());
        if (component instanceof Cell) {
            Cell cell = (Cell)component;
            int x = cell.getCellX();
            int y = cell.getCellY();

            if (e.getButton() == MouseEvent.BUTTON1 && (two.getNum(x, y) == 0 || cell.getForeground().equals(Color.BLUE))) {
                int number = two.getSelNum();
                if (number == -1)
                    return;
                two.setNum(x, y, number);
                cell.setNumber(number, true);
            } else if (e.getButton() == MouseEvent.BUTTON3 && !cell.getForeground().equals(Color.BLACK)) {
                two.setNum(x, y, 0);
                cell.setNumber(0, false);
            }
            grid.update(two, Refresh.POT_NUMBERS);
        }
    }

    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}