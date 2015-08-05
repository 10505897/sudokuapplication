
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Sudoku extends JFrame {
    public Sudoku() {
        super("Java Sudoku Application by Joshua Gardiner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        Game game = new Game();

        Controls buttonControl = new Controls(game);
        Buttons button = new Buttons();
        button.setController(buttonControl);
        add(button, BorderLayout.EAST);

        Grid grid = new Grid();
        Controls gridControl = new Controls(grid, game);
        grid.setGame(game);
        grid.setControls(gridControl);
        add(grid, BorderLayout.CENTER);

        game.addObserver(button);
        game.addObserver(grid);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Sudoku();
    }
}