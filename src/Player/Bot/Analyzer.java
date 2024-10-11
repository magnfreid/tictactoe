package Player.Bot;


import Game.Board;
import Game.BoardNavigation.Coordinate;
import Game.BoardNavigation.Line;

import java.util.ArrayList;

public class Analyzer {
    final private Board board;

    public Analyzer(Board board) {
        this.board = board;
    }


    public ArrayList<Line> getAllLines() {
        ArrayList<Line> lines = new ArrayList<>();
        Object[][] grid = board.getGrid();
        int boardSize = board.getBoardSize();
        ArrayList<Coordinate> diagonal = new ArrayList<>();
        ArrayList<Coordinate> antiDiagonal = new ArrayList<>();
        //Rows and columns
        for (int i = 0; i < boardSize; i++) {
            ArrayList<Coordinate> row = new ArrayList<>();
            ArrayList<Coordinate> column = new ArrayList<>();
            for (int j = 0; j < board.getBoardSize(); j++) {
                row.add(new Coordinate(j, i, grid[j][i]));
                column.add(new Coordinate(i, j, grid[i][j]));
            }
            lines.add(new Line(row, "row"));
            lines.add(new Line(column, "column"));
        }
        //Diagonal and anti-diagonal
        for (int i = 0; i < boardSize; i++) {
            diagonal.add(new Coordinate(i, i, grid[i][i]));
            antiDiagonal.add(new Coordinate(i, boardSize - 1 - i, grid[i][boardSize - 1 - i]));
        }
        lines.add(new Line(diagonal, "diagonal"));
        lines.add(new Line(antiDiagonal, "anti-diagonal"));
        return lines;
    }
}
