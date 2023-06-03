/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;

/**
 *
 * @author jchen
 */
public class Game { //facilitates the flow of the game
    public static Board gameboard = new Board(); //board object
    public static Color currentTurn = Color.WHITE; //curent turn
    
    public static List<Move> moveLog = new ArrayList<>(); //records the whole game
    
    public static void makeMove(Square start, Square target) {
        Move m;
        //if move is castling
        if (Castling.isCastleMove(gameboard, currentTurn, start, target)) {
            m = Castling.createMove(currentTurn, target.col - start.col > 0); //create the move from the castling class
        } else {
            m = new Move(start, target, false, false); //otherwise create the move normally
        }
        
        Set<Move> legalMoves = gameboard.getLegalMoves(currentTurn); //get all legal moves
        
        if (legalMoves.contains(m)) { //if the move inputted by the user is in the legal move list
            gameboard.makeMove(m); //make teh move
            currentTurn = Color.otherColor(currentTurn); //switch the color
            
            moveLog.add(m); //add the move to the game log
            System.out.println(moveLog);
        }
        
        if (isGameEnd()) { //if teh game has ended close the window
            GUI.closeWindow();
        }
    }
    
    public static boolean isGameEnd() {
        Set<Move> legalMoves = gameboard.getLegalMoves(currentTurn); //get all legal moves
        
        if (legalMoves.isEmpty()) { //if there are no legal moves
            if (gameboard.isInCheck(currentTurn)) { //check if the user is also in check
                System.out.println("Checkmate!"); //if so, checkmate
            } else {
                System.out.println("Stalemate!"); //if not stalemate
            }
            
            return true;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> GUI.init()); //create GUI
    }
}
