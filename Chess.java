/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.List;
import java.util.Set;

/**
 *
 * @author jchen
 */
public class Chess {
    public static Board gameboard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    
    public static Color currentTurn = Color.BLACK;
    
    public static void main(String args[]) {
        
        
        gameboard.displayBoard();
        
//        for (Piece[] line: Board.board) {
//            for (Piece p : line) {
//                if (p != null) {
//                    System.out.println(Move.indexToSquare(p.row, p.col));
//                    System.out.println(p.possibleMoves());
//                }
//            }
//        }
//        System.out.println(gameboard.isInCheck(gameboard, Color.WHITE));
//        System.out.println(gameboard.getLegalMoves(currentTurn));
        System.out.println(gameboard.getLegalMoves(currentTurn).size());
//        System.out.println(gameboard.board[7][1].possibleMoves(gameboard));
//        Castling.performCastlingMove(gameboard, currentTurn, false);
//        System.out.println("dasfkl;sdjfkls");
//        gameboard.displayBoard();
    }
    
    
}
