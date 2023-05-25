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

public class Pawn extends Piece {
    static final int[][][] whiteOutcomeArray = {{{-1, 0}, {-2, 0}}, {{-1, -1}},{{-1, 1}}};
    static final int[][][] blackOutcomeArray = {{{1, 0}, {2, 0}}, {{1, -1}},{{1, 1}}};
    
    public Pawn(Color color, Square position) {
        super(color, position);
        
        coordinateOutcomes = iterateThroughOutcomes(chooseOutcomeArray());
    }
    
    public Pawn(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() {
        if (this.color == Color.BLACK) {
            return 'p';
        } else {
            return 'P';
        }
    }
    
    private int[][][] chooseOutcomeArray() {
        return (this.color == Color.WHITE ? whiteOutcomeArray : blackOutcomeArray);
    }
    
    @Override
    public List<Move> possibleMoves(Board b) {
        List<Move> possibleMoves = new ArrayList<>();
        
        for (List<Square> direction : coordinateOutcomes.outcomes) {
            for (Square s : direction) {
                int targetRow = this.position.row + s.row;
                int targetCol = this.position.col + s.col;
                
//                System.out.println(targetRow + " " + targetCol);
                if (isInsideBoard(targetRow, targetCol)) {
                    Piece p = b.getBoard()[targetRow][targetCol];
                    if (s.col != 0) {
                        if (p != null && p.color != this.color) {
                            captureMoves(possibleMoves, targetRow, targetCol, b);
                        }
                    } else {
                        normalMoves(possibleMoves, targetRow, targetCol, s.row, b);
                    }
                }
            }
        }
        
        return possibleMoves;
    }
    
    private void normalMoves(List<Move> possibleMoves, int targetRow, int targetCol, int rowChange, Board b) {
        int startingRank = (this.color == Color.WHITE ? 6 : 1);
        
        Piece p = b.getBoard()[targetRow][targetCol];
        
        if (p == null) {  
            if (Math.abs(rowChange) == 2 && this.position.row != startingRank) {
                return;
            }
            possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol));
        }
    }
    
    private void captureMoves(List<Move> possibleMoves, int targetRow, int targetCol, Board b) {
        Piece p = b.getBoard()[targetRow][targetCol];
        
        if (p != null && p.color != this.color) {
                possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol));
        }
    }
    
    @Override
    public Piece clone() {
        return new Pawn(this.color, position);
    }
 }
