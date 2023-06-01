/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jchen
 */
public class Bishop extends Piece {
    public Bishop(Color color, Square position) {
        super(color, position);
        
        int[][][] coordinateOutcomesArray = {
            {{ 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 }},
            {{ -1, -1 }, { -2, -2 }, { -3, -3 }, { -4, -4 }, { -5, -5 }, { -6, -6 }, { -7, -7 }},
            {{ 1, -1 }, { 2, -2 }, { 3, -3 }, { 4, -4 }, { 5, -5 }, { 6, -6 }, { 7, -7 }},
            {{ -1, 1 }, { -2, 2 }, { -3, 3 }, { -4, 4 }, { -5, 5 }, { -6, 6 }, { -7, 7 }}
        };
        
        coordinateOutcomes = iterateThroughOutcomes(coordinateOutcomesArray);
    }
    
    public Bishop(Color color, int row, int col) {
        this(color, new Square(row, col));
    }
    
    @Override
    public char getCharacter() {
        if (this.color == Color.BLACK) {
            return 'b';
        } else {
            return 'B';
        }
    }
    
    @Override
    public Piece clone() {
        return new Bishop(this.color, this.position);
    }
    
    @Override
    public String getPieceName() {
        return "bishop";
    }
}
