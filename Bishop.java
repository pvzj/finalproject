/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;
/**
 *
 * @author jchen
 */
public class Bishop extends Piece { //bishop class, extends piece
    //create all possible outcmoes of the bishop
    //each 2d array in the 3d array represents a direction, so that when one direction is blocked, the piece cannot go further
    //each array inside the directional arrays have 2 numbers, representing the change in its row and column values for each move

    //only represented as a 2d array because it is easy to enter the data. 
    //the numbers will be converted into a PieceOutcome object  
    public static final int[][][] coordinateOutcomesArray = { 
            {{ 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 }},
            {{ -1, -1 }, { -2, -2 }, { -3, -3 }, { -4, -4 }, { -5, -5 }, { -6, -6 }, { -7, -7 }},
            {{ 1, -1 }, { 2, -2 }, { 3, -3 }, { 4, -4 }, { 5, -5 }, { 6, -6 }, { 7, -7 }},
            {{ -1, 1 }, { -2, 2 }, { -3, 3 }, { -4, 4 }, { -5, 5 }, { -6, 6 }, { -7, 7 }}
    };
    
    
    public Bishop(Color color, Square position) { //constructor that initializes the coordinate outcome
        super(color, position); //calls piece constructor

        coordinateOutcomes = iterateThroughOutcomes(coordinateOutcomesArray); //converts array to PieceOutcome
    }
    
    public Bishop(Color color, int row, int col) { //alternate constructor
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() { //get character to be represented as in testing board
        return this.color == Color.BLACK ? 'b' : 'B';
    }
    
    @Override
    public Piece clone() { //get clone of piece
        return new Bishop(this.color, this.position);
    }
    
    @Override
    public String getPieceName() { //get name of piece
        return "bishop";
    }
}
