/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Knight extends Piece {
    public Knight(Color color, Square position) {
        super(color, position);
        
        
        
        int[][][] coordinateOutcomesArray = {
            {{-2, -1}}, 
            {{-2, 1}},
            {{-1, -2}}, 
            {{-1, 2}},
            {{1, -2}}, 
            {{1, 2}},
            {{2, -1}}, 
            {{2, 1}}
        };
        
        coordinateOutcomes = iterateThroughOutcomes(coordinateOutcomesArray);
    }
    
    public Knight(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() {
        if (this.color == Color.BLACK) {
            return 'n';
        } else {
            return 'N';
        }
    }
    
    
    @Override
    public Piece clone() {
        return new Knight(this.color, this.position);
    }
}
