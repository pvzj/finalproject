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

public abstract class Piece { //abstract piece method, superclass of all the different pieces
    public Color color; //fields
    public Square position;
    
    public PieceOutcome coordinateOutcomes; //coordinate outcomes
    
    public boolean hasMoved;
    
    public Piece(Color color, Square position) { //constructor
        this.color = color;
        this.position = position;
        hasMoved = false;
    }
    
    public static boolean isInsideBoard(int row, int col) { //check if location is within the board
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    public List<Move> possibleMoves(Board b) { //get all possible moves of the piece
        List<Move> possibleMoves = new ArrayList<>();
        
        PieceOutcome coordinateOutcomes = getCoordinateOutcomes(); //the outcomes of the piece
        
        for (List<Outcome> direction : coordinateOutcomes.outcomes) {
            for (Outcome o : direction) { //iterate through all the directions
                int targetRow = this.position.row + o.rowDiff; //add the outcome to the piece's position to get its new position
                int targetCol = this.position.col + o.colDiff;

                if (!checkMove(b, possibleMoves, targetRow, targetCol)) { //check if the move is legal
                    break; //stop iterating through direction if the direction is blocked
                }
            }
        }
        
        return possibleMoves;
    }
    
    //checks the obstructions of the piece's movement. 
    //returns if the piece is able continue moving in the same direction
    public boolean checkMove(Board b, List<Move> possibleMoves, int targetRow, int targetCol) {
        if (!isInsideBoard(targetRow, targetCol)) { //check if the move is inside the board
            return false;
        }
        
        Piece target = b.getBoard()[targetRow][targetCol]; //target square on the board

        boolean isTargetEmpty = target == null; //if the square is empty
        
        if (isTargetEmpty || target.color != this.color) { //if the square is empty or if the target piece is of the opposite color
            possibleMoves.add(new Move(this.position.row, this.position.col, targetRow, targetCol, false, false, false)); //legal move
            
            //if the target was empty, the piece can continue
            //if the target was a piece of the opposite color, it can't continue;
            return isTargetEmpty;
        }
        
        return false; //the piece can't continue
    }
    
    public PieceOutcome iterateThroughOutcomes(int[][][] outcome) { //given input 3d array, create the object
        PieceOutcome res = new PieceOutcome();
        
        for (int[][] direction : outcome) { //iterate through outcomes
            List<Outcome> currentDirection = new ArrayList<>();
            
            for (int[] coordinate : direction) { //for each coordinate in the 2d array of directions
                currentDirection.add(new Outcome(coordinate[0], coordinate[1])); //create outcome
            }
            res.outcomes.add(currentDirection); //add the direction
        }
        return res; //return the piece outcome
    }
    
    //clone method
    @Override
    public abstract Piece clone();
    
    //getters
    public abstract char getCharacter();
    
    public PieceOutcome getCoordinateOutcomes() {
        return coordinateOutcomes;
    }
    
    public Color getColor() {
        return color;
    }
    
    public abstract String getPieceName();
}