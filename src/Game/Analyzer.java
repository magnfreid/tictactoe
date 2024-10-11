package Game;


import java.util.ArrayList;

public class Analyzer {
    final private Board board;

    public Analyzer(Board board) {
        this.board = board;
    }

     public static class Line{
        ArrayList<Object> elements;
        String type;

        public Line(ArrayList<Object> elements, String type){
            this.elements = elements;
            this.type = type;
        }
    }

    public ArrayList<Line> getAllLines() {
       ArrayList<Line> lines = new ArrayList<>();
        Object[][] grid = board.getGrid();
        int boardSize = board.getBoardSize();
        ArrayList<Object> diagonal = new ArrayList<>();
        ArrayList<Object> antiDiagonal = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            ArrayList<Object> row = new ArrayList<>();
            ArrayList<Object> column = new ArrayList<>();
            for (int j = 0; j < board.getBoardSize(); j++) {
                row.add(grid[j][i]);
                column.add(grid[i][j]);
            }
            lines.add(new Line(row, "row"));
            lines.add(new Line(column, "column"));
        }
        for (int i = 0; i < boardSize; i++) {
            diagonal.add(grid[i][i]);
            antiDiagonal.add(grid[i][boardSize - 1 - i]);
        }
        lines.add(new Line(diagonal, "diagonal"));
        lines.add(new Line(antiDiagonal, "anti-diagonal"));
        return lines;
    }
}
