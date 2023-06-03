/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public enum Color { //enum with black and white
    WHITE, BLACK;
    
    public static Color otherColor(Color c) { //get the other color
        return c == WHITE ? Color.BLACK : Color.WHITE;
    }
}