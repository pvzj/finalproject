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
public class Queen extends Piece {
    public Queen(Color color, Square position) {
        super(color, position);
        coordinateOutcomes = createQueenOutcomes();
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
        PieceOutcome bishopCoordinateOutcomes = new Bishop(null, null).coordinateOutcomes;
        PieceOutcome rookCoordinateOutcomes = new Rook(null, null).coordinateOutcomes;
        PieceOutcome queenCoordinateOutcomes = new PieceOutcome();
        
        queenCoordinateOutcomes.outcomes = new HashSet<>(bishopCoordinateOutcomes.outcomes);
        queenCoordinateOutcomes.outcomes.addAll(rookCoordinateOutcomes.outcomes);
        
        return queenCoordinateOutcomes;
    }
    
    @Override
    public Piece clone() {
        return new Queen(this.color, position);
    }
    
}

