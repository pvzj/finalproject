/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Move {
    private Square start;
    private Square target;

    public Move(Square start, Square target) {
        this.start = start;
        this.target = target;
    }

    public Move(int startRow, int startCol, int targetRow, int targetCol) {
        this.start = new Square(startRow, startCol);
        this.target = new Square(targetRow, targetCol);
    }

    public int getStartRow() {
        return start.row;
    }

    public int getStartCol() {
        return start.col;
    }

    public int getTargetRow() {
        return target.row;
    }

    public int getTargetCol() {
        return target.col;
    }

    public Square getStart() {
        return start;
    }

    public Square getTarget() {
        return target;
    }
    
    
    public String toString() {
        return indexToSquare(start.row, start.col) + "->" + indexToSquare(target.row, target.col);
    }
    
    public static String indexToSquare(int row, int col) {
        return "" + ((char)(col + 'a')) + (8-row);
    }
}

