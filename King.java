/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class King extends Piece { //king class, extends piece: same thing as other piece classes (comments in bishop class)
    //each 2d array is only 1 element long, because the king can move one square in any direction 
    public static final int[][][] coordinateOutcomesArray = {
            {{-1, -1}},
            {{-1, 1}},
            {{-1, 0}},
            {{0, -1}},
            {{0, 1}},
            {{1, -1}},
            {{1, 0}},
            {{1, 1}}
    };
    
    public King(Color color, Square position) {
        super(color, position);
        
        coordinateOutcomes = iterateThroughOutcomes(coordinateOutcomesArray);
    }
    
    public King(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() {
        if (this.color == Color.BLACK) {
            return 'k';
        } else {
            return 'K';
        }
    }
    
    @Override
    public Piece clone() {
        return new King(this.color, this.position);
    }    
    
    @Override
    public String getPieceName() {
        return "king";
    }
}
