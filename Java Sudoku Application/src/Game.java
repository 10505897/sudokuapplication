
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {
    private int[][] gamesolution;
    private int[][] game;
    private boolean[][] checkgame;
    private int selNum;
    private boolean help;
    
    private int[][] genSolution(int[][] game, int index) {
    	
        if (index > 80)
            return game;

        int x = index % 9;
        int y = index / 9;

        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) numbers.add(i);
        Collections.shuffle(numbers);

        while (numbers.size() > 0) {
            int number = getNxtNum(game, x, y, numbers);
            if (number == -1)
                return null;

            game[y][x] = number;
            int[][] tmpGame = genSolution(game, index + 1);
            if (tmpGame != null)
                return tmpGame;
            game[y][x] = 0;
        }

        return null;
    }

    private int[][] genGame(int[][] game) {
    	
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++)
            positions.add(i);
        Collections.shuffle(positions);
        return genGame(game, positions);
    }

    private int[][] genGame(int[][] game, List<Integer> positions) {
    	
        while (positions.size() > 0) {
            int position = positions.remove(0);
            int x = position % 9;
            int y = position / 9;
            int temp = game[y][x];
            game[y][x] = 0;

            if (!isGameValid(game))
                game[y][x] = temp;
        }

        return game;
    }

    private boolean isGameValid(int[][] game) {
    	
        return isGameValid(game, 0, new int[] { 0 });
    }

    private boolean isGameValid(int[][] game, int index, int[] numberOfSolutions) {
    	
        if (index > 80)
            return ++numberOfSolutions[0] == 1;

        int x = index % 9;
        int y = index / 9;

        if (game[y][x] == 0) {
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++)
                numbers.add(i);

            while (numbers.size() > 0) {
                int number = getNxtNum(game, x, y, numbers);
                if (number == -1)
                    break;
                game[y][x] = number;

                if (!isGameValid(game, index + 1, numberOfSolutions)) {
                    game[y][x] = 0;
                    return false;
                }
                game[y][x] = 0;
            }
        } else if (!isGameValid(game, index + 1, numberOfSolutions))
            return false;

        return true;
    }

    private int[][] copy(int[][] game) {
    	
        int[][] copy = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                copy[y][x] = game[y][x];
        }
        return copy;
    }

    public Game() {
    	
        nGame();
        checkgame = new boolean[9][9];
        help = true;
    }

    public void nGame() {
    	
        gamesolution = genSolution(new int[9][9], 0);
        game = genGame(copy(gamesolution));
        setChanged();
        notifyObservers(Refresh.NEW_GAME);
    }
    
    public int getSelNum() {
    	
        return selNum;
    }
    
    public void setSelNum(int selNum) {
    	
        this.selNum = selNum;
        setChanged();
        notifyObservers(Refresh.SELECTED_NUMBER);
    }

    public boolean isSelNumPossible(int x, int y) {
    	
        return game[y][x] == 0 && possibleRow(game, y, selNum)
                && possibleColumn(game, x, selNum) && possibleGrid(game, x, y, selNum);
    }

    public int getNum(int x, int y) {
    	
        return game[y][x];
    }
    
    public void setNum(int x, int y, int number) {
    	
        game[y][x] = number;
    }

    public boolean isCheckValid(int x, int y) {
    	
        return checkgame[y][x];
    }

    private int getNxtNum(int[][] game, int x, int y, List<Integer> numbers) {
    	
        while (numbers.size() > 0) {
            int number = numbers.remove(0);
            if (possibleRow(game, y, number) && possibleColumn(game, x, number) && possibleGrid(game, x, y, number))
                return number;
        }
        return -1;
    }

    private boolean possibleRow(int[][] game, int y, int number) {
    	
        for (int x = 0; x < 9; x++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }

    private boolean possibleColumn(int[][] game, int x, int number) {
    	
        for (int y = 0; y < 9; y++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }

    private boolean possibleGrid(int[][] game, int x, int y, int number) {
    	
        int x1 = x < 3 ? 0 : x < 6 ? 3 : 6;
        int y1 = y < 3 ? 0 : y < 6 ? 3 : 6;
        for (int yy = y1; yy < y1 + 3; yy++) {
            for (int xx = x1; xx < x1 + 3; xx++) {
                if (game[yy][xx] == number)
                    return false;
            }
        }
        return true;
    }

    public void cGame() {
    	
        selNum = 0;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                checkgame[y][x] = game[y][x] == gamesolution[y][x];
        }
        setChanged();
        notifyObservers(Refresh.CHECK_GAME);
    }
    
    public void setHelp(boolean help) {
    	
        this.help = help;
        setChanged();
        notifyObservers(Refresh.HELP);
    }

    public boolean isHelp() {
    	
        return help;
    }
}