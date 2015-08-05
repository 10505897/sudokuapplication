
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Cell extends JLabel {
    private int row;
    private int column;

    public Cell(int x, int y) {
        super("", CENTER);
        this.row = x;
        this.column = y;
        
        setPreferredSize(new Dimension(40, 40));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        setOpaque(true);
    }

    public void setNumber(int number, boolean userInput) {
        setForeground(userInput ? Color.LIGHT_GRAY : Color.BLACK);
        setText(number > 0 ? number + "" : "");
    }

    public int getCellX() {
        return row;
    }

    public int getCellY() {
        return column;
    }
}