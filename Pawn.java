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

public class Pawn extends Piece { //similar to other pieces, but slightly different due to pawn's abnormal moving and capturing patterns
    static final int[][][] whiteOutcomeArray = {{{-1, 0}, {-2, 0}}, {{-1, -1}},{{-1, 1}}}; //different outcomes based on color
    static final int[][][] blackOutcomeArray = {{{1, 0}, {2, 0}}, {{1, -1}},{{1, 1}}};
    
    public Pawn(Color color, Square position) {
        super(color, position);
        //generate coordinate outcomes array
        coordinateOutcomes = iterateThroughOutcomes(chooseOutcomeArray());
    }
    //constructor
    public Pawn(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() { //get character
        if (this.color == Color.BLACK) {
            return 'p';
        } else {
            return 'P';
        }
    }
    
    private int[][][] chooseOutcomeArray() { //choose the outcome array based on the color
        return (this.color == Color.WHITE ? whiteOutcomeArray : blackOutcomeArray);
    }
    
    @Override
    public boolean checkMove(Board b, List<Move> possibleMoves, int targetRow, int targetCol) { //overrides check move, due to special conditions for capturing
        int rowDiff = this.position.row-targetRow; //differences in row and col
        int colDiff = this.position.col-targetCol;
        
        if (isInsideBoard(targetRow, targetCol)) { //check if piece is inside board
            Piece target= b.getBoard()[targetRow][targetCol]; //target piece
            
            if (colDiff != 0) { //if coldiff is zero, that means the move is a normal move
                if (target!= null && target.color != this.color) { //check if the target both contains a piece and it is of the opposite color
                    captureMoves(possibleMoves, targetRow, targetCol, b); //generate the capture moves
                }
            } else { //if coldiff is one, that means the move is a capture
                normalMoves(possibleMoves, targetRow, targetCol, rowDiff, b); //generate the normal moves
            }
        }
        return true; //return true no matter what, because a pawn can't really be blocked
    }
    private void normalMoves(List<Move> possibleMoves, int targetRow, int targetCol, int rowDiff, Board b) { //normal move case
        int startingRank = (this.color == Color.WHITE ? 6 : 1); //starting rank of the pawn
        
        Piece target= b.getBoard()[targetRow][targetCol]; //target piece
        
        if (target== null) { //if target is empty
            if (Math.abs(rowDiff) == 2) { //if the move is a double move
                if (this.position.row != startingRank) { //if the piece is not on the starting rank, the move is not legal
                    return;
                }
                
                int infrontRowOffset = (this.color == Color.WHITE ? -1 : 1); //the square infront of the piece
                
                Piece infront = b.getBoard()[this.position.row+infrontRowOffset][this.position.col]; //check the piece infront
                
                if (infront != null) { //if that contains a piece, the double move is illegal
                    return;
                }
            }
            
            //add the move
            possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol, false, false)); 
        }
    }
    
    private void captureMoves(List<Move> possibleMoves, int targetRow, int targetCol, Board b) { //check capture moves
        Piece target= b.getBoard()[targetRow][targetCol]; //get target piece
        
        if (target!= null && target.color != this.color) { //if the target piece is not empty and the color is the opposite of this
            possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol, false, false)); //add the piece
        }
    }
    
    @Override
    public Piece clone() { //clone piece
        return new Pawn(this.color, position);
    }
    
    @Override
    public String getPieceName() { //return piece name
        return "pawn";
    }
 }
