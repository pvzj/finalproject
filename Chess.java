/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author jchen
 */
public class Chess {
    public static Board gameboard = new Board("1rq1k2r/p1p1n2p/p2p1pP1/2b1B3/2P1P3/2NQ1NPb/P1PP4/R3K2R");
    
    public static Color currentTurn = Color.BLACK;
    
    public static List<Move> gameLog = new ArrayList<>();
    
    public static void main(String args[]) {
        gameboard.displayBoard();
        
        System.out.println(gameboard.getLegalMoves(currentTurn));
    }
    
    public static void executeMove(Move m) {
        
    }
    
    public static Move promptUserForMove() {
        //TODO: use scanner to ask user and translate move notation to a Move object
        //if not legal, then prompt again
        Set<Move> legalMoves = gameboard.getLegalMoves(currentTurn);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your move: ");
            String move = sc.nextLine();
            for (Move m : legalMoves) {
                if (m.toString().equals(move)) {
                    return m;
                }
            }
            System.out.println("Illegal move, try again");
        }
    }
//
//    private static Move convertMoveNotationToMove(String move) {
//        if (move.equals("O-O")) {
//            return Castling.createMove(currentTurn, true);
//        } else if (move.equals("O-O-O")) {
//            return Castling.createMove(currentTurn, false);
//        }
//        
//        return null;
//    }
}
