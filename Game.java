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
public class Game {
    public static Board gameboard = new Board("rnbqkbnr/pppppppp/8/8/8/5BN1/PPPPPPPP/RNBQK2R");
    public static Color currentTurn = Color.WHITE;
    
    public static List<Move> moveLog = new ArrayList<>();
    
    public static void makeMove(Square start, Square target) {
        Move m;
        
        boolean isMoveCastling = Castling.isCastleMove(gameboard, currentTurn, start, target);
        if (isMoveCastling) {
            m = Castling.createMove(currentTurn, target.col - start.col > 0);
        } else {
            m = new Move(start, target, false, false);
        }
        
        Set<Move> legalMoves = gameboard.getLegalMoves(currentTurn);
        
        if (legalMoves.contains(m)) {
            gameboard.makeMove(m);
            currentTurn = Color.otherColor(currentTurn);
            
            moveLog.add(m);
        }
        
        if (isGameEnd()) {
            GUI.closeWindow();
        }
    }
    
    public static boolean isGameEnd() {
        Set<Move> legalMoves = gameboard.getLegalMoves(currentTurn);
        
        if (legalMoves.isEmpty()) {
            if (gameboard.isInCheck(currentTurn)) {
                System.out.println("Checkmate!");
            } else {
                System.out.println("Stalemate!");
            }
            
            return true;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> GUI.createAndShowGUI());
    }
}
