/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Rook extends Piece {
    public Rook(Color color, Square position) {
        super(color, position);
        int[][][] rookOutcomes = {
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{ 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 }},
            {{ -1, 0 }, { -2, 0 }, { -3, 0 }, { -4, 0 }, { -5, 0 }, { -6, 0 }, { -7, 0 }},
            {{ 0, -1 }, { 0, -2 }, { 0, -3 }, { 0, -4 }, { 0, -5 }, { 0, -6 }, { 0, -7 }}
        };
        coordinateOutcomes = iterateThroughOutcomes(rookOutcomes);
    }
    
    public Rook(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() {
        if (this.color == Color.BLACK) {
            return 'r';
        } else {
            return 'R';
        }
    }
    @Override
    public PieceOutcome getCoordinateOutcomes() {
        return coordinateOutcomes;
    }
    
    @Override
    public Piece clone() {
        return new Rook(this.color, this.position);
    }
    
    @Override
    public String getPieceName() {
        return "rook";
    }
}

