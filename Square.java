/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Square { //represents a square on the board  
    public int row; //attributes
    public int col;
    
    //constructors
    Square(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    Square(Square s) {
        this(s.row, s.col);
    }
    
    //getters/setters
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
    
    //equals method, compares the rows and columns of the square
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof Square)) {
            return false;
        }
        
        Square s = (Square) o;
        
        return this.row == s.row && this.col == s.col;
    }
    
    @Override
    public int hashCode() { //ensures .contains works
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + col;
        return result;
    }
    
    //tostring for testing
    public String toString() {
        return "(" + row + " " + col+")";
    }
}
