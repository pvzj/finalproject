/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.HashSet;

/**
 *
 * @author jchen
 */
public class Queen extends Piece { //similar to other pieces, but slightly different
    //queen movement is the same as the rook and bishop movements put together
    public Queen(Color color, Square position) {
        super(color, position);
        coordinateOutcomes = createQueenOutcomes(); //use createQueenOutcomes method instead of its own array
    }
    
    public Queen(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() {
        if (this.color == Color.BLACK) {
            return 'q';
        } else {
            return 'Q';
        }
    }
    
    public PieceOutcome createQueenOutcomes() {
        //get pieceoutcomes of the bishop and rooks
        PieceOutcome bishopCoordinateOutcomes = new Bishop(null, null).coordinateOutcomes; 
        PieceOutcome rookCoordinateOutcomes = new Rook(null, null).coordinateOutcomes;
        PieceOutcome queenCoordinateOutcomes = new PieceOutcome();
        
        //combine the two sets
        queenCoordinateOutcomes.outcomes = new HashSet<>(bishopCoordinateOutcomes.outcomes);
        queenCoordinateOutcomes.outcomes.addAll(rookCoordinateOutcomes.outcomes);
        
        return queenCoordinateOutcomes;
    }
    
    @Override
    public Piece clone() {
        return new Queen(this.color, position);
    }
    
    @Override
    public String getPieceName() {
        return "queen";
    }
    
}

