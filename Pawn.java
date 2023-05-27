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
                    Piece target= b.getBoard()[targetRow][targetCol];
                    if (s.col != 0) {
                        if (target!= null && target.color != this.color) {
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
        
        Piece target= b.getBoard()[targetRow][targetCol];
        
        if (target== null) {
            if (Math.abs(rowChange) == 2) {
                if (this.position.row != startingRank) {
                    return;
                }
                
                
                int infrontRowOffset = (this.color == Color.WHITE ? -1 : 1);
                
                Piece infront = b.getBoard()[this.position.row+infrontRowOffset][this.position.col];
                if (infront != null) {
                    return;
                }
            }
            possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol, false, false));
        }
    }
    
    private void captureMoves(List<Move> possibleMoves, int targetRow, int targetCol, Board b) {
        Piece target= b.getBoard()[targetRow][targetCol];
        
        if (target!= null && target.color != this.color) {
                possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol, false, false));
        }
    }
    
    @Override
    public Piece clone() {
        return new Pawn(this.color, position);
    }
 }