/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jchen
 */
public class King extends Piece {
    public King(Color color, Square position) {
        super(color, position);
        int[][][] coordinateOutcomesArray = {
            {{-1, -1}},
            {{-1, 1}},
            {{-1, 0}},
            {{0, -1}},
            {{0, 1}},
            {{1, -1}},
            {{1, 0}},
            {{1, 1}}
        };
        
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
