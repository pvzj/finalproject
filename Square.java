/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Square {
    public int row;
    public int col;
    
    Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Square)) {
            return false;
        }
        
        Square s = (Square) o;
        
        return this.row == s.row && this.col == s.col;
    }
    
    public String toString() {
        return "(" + row + " " + col+")";
    }
}
