/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Outcome { //represents difference in index for a piece move
    public int rowDiff; //difference in row and col values
    public int colDiff;
    
    //constructor
    Outcome(int rowDiff, int colDiff) {
        this.rowDiff = rowDiff;
        this.colDiff = colDiff;
    }
    //getters/setters
    public int getRowDiff() {
        return rowDiff;
    }

    public void setRowDiff(int rowDiff) {
        this.rowDiff = rowDiff;
    }

    public int getColDiff() {
        return colDiff;
    }

    public void setColDiff(int colDiff) {
        this.colDiff = colDiff;
    }
}
