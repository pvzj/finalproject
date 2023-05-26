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
    public static Board gameboard = new Board("1n1qkbnr/pppppppp/br6/3N4/3P1P2/2BQB1N1/P1P2PPP/R3K2R");
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
//        System.out.println(gameboard.getLegalMoves(Color.WHITE));
//        System.out.println(gameboard.getLegalMoves(Color.WHITE).size());
//        System.out.println(gameboard.board[7][1].possibleMoves(gameboard));

        System.out.println(gameboard.isCastlingLegal(Color.WHITE, true));
        System.out.println(gameboard.isCastlingLegal(Color.WHITE, false));
    }
    
    
}
