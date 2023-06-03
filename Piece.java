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
    
    public abstract String getPieceName();
    
    public static boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    public List<Move> possibleMoves(Board b) {
        List<Move> possibleMoves = new ArrayList<>();
        
        PieceOutcome coordinateOutcomes = getCoordinateOutcomes();
        
        for (List<Outcome> direction : coordinateOutcomes.outcomes) {
            for (Outcome o : direction) {
                int targetRow = this.position.row + o.rowDiff;
                int targetCol = this.position.col + o.colDiff;

                if (!checkMove(b, possibleMoves, targetRow, targetCol)) {
                    break;
                }
            }
        }
        
        return possibleMoves;
    }
    
    public boolean checkMove(Board b, List<Move> possibleMoves, int targetRow, int targetCol) {
         if (isInsideBoard(targetRow, targetCol)) {
            Piece target = b.getBoard()[targetRow][targetCol];
            
            boolean isTargetEmpty = target == null;
            if (isTargetEmpty || target.color != this.color) {
                possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol, false, false));
                return isTargetEmpty;
            }
         }
         
         return false;
    }
    
    public PieceOutcome iterateThroughOutcomes(int[][][] outcome) {
        PieceOutcome res = new PieceOutcome();
        
        for (int[][] direction : outcome) {
            List<Outcome> outcomes = new ArrayList<>();
            for (int[] coordinate : direction) {
                outcomes.add(new Outcome(coordinate[0], coordinate[1]));
            }
            res.outcomes.add(outcomes);
        }
        return res;
    }
    
    public abstract char getCharacter();
    
    public PieceOutcome getCoordinateOutcomes() {
        return coordinateOutcomes;
    }
    
    
    @Override
    public abstract Piece clone();
}