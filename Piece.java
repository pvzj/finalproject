/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
import java.util.*;

public abstract class Piece {
    public Color color;
    public Square position;
    
    public PieceOutcome coordinateOutcomes;
    
    public boolean hasMoved;
    
    public Piece(Color color, Square position) {
        this.color = color;
        this.position = position;
        hasMoved = false;
    }

    public Color getColor() {
        return color;
    }
    
    public static boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    public List<Move> possibleMoves(Board b) {
        List<Move> possibleMoves = new ArrayList<>();
        
        PieceOutcome coordinateOutcomes = getCoordinateOutcomes();
        
        for (List<Square> direction : coordinateOutcomes.outcomes) {
            for (Square s : direction) {
                int targetRow = this.position.row + s.row;
                int targetCol = this.position.col + s.col;

                if (isInsideBoard(targetRow, targetCol)) {
                    Piece p = b.getBoard()[targetRow][targetCol];
                    if (p == null) {
                        possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol));
                    } else {
                        if (p.color != this.color) {
                            possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol));
                        }
                        break;
                    }    
                }
            }
        }
        
        return possibleMoves;
    }
    
    public PieceOutcome iterateThroughOutcomes(int[][][] outcome) {
        PieceOutcome p = new PieceOutcome();
        for (int[][] direction : outcome) {
            List<Square> l = new ArrayList<>();
            for (int[] coordinate : direction) {
                l.add(new Square(coordinate[0], coordinate[1]));
            }
            p.outcomes.add(l);
        }
        return p;
    }
    
    public abstract char getCharacter();
    
    public PieceOutcome getCoordinateOutcomes() {
        return coordinateOutcomes;
    }
    
    
    @Override
    public abstract Piece clone();
}