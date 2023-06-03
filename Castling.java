/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change board license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit board template
 */
package finalproject;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jchen
 */
public class Castling { //important methods for castling
    
    private static int[] getCastlingData(Color color, boolean kingside) { //returns the squares that are involved during castling
        int row = (color == Color.WHITE) ? 7 : 0;
        int kingStartCol = 4;
        int rookStartCol = kingside ? 7 : 0;
        int kingTargetCol = kingside ? 6 : 2;
        int rookTargetCol = kingside ? 5 : 3;

        return new int[]{ row, kingStartCol, rookStartCol, kingTargetCol, rookTargetCol };
    }

    public static void performCastlingMove(Board board, Color color, boolean kingside) { 
        int[] castlingData = getCastlingData(color, kingside); //get data
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int rookStartCol = castlingData[2];
        int kingTargetCol = castlingData[3];
        int rookTargetCol = castlingData[4];
        
        board.makeMove(new Move(row, kingStartCol, row, kingTargetCol, false, false, false)); //move king
        board.makeMove(new Move(row, rookStartCol, row, rookTargetCol, false, false, false)); //move rook
    }
    
    public static Set<Move> getCastleMoves(Board board, Color color) { //return a set of all the legal castling moves in the position
        Set<Move> moves = new HashSet<>();
        
        if (board.isInCheck(color)) { //if the king is in check, return nothing
            return moves;
        }
        
        boolean kingside = isLegal(board, color, true); //check if kingside castling is legal
        boolean queenside = isLegal(board, color, false); //check if queenside castling is legal
        
        
        if (kingside) {
            moves.add(createMove(color, kingside)); //if legal, add kingside castling
        }
        if (queenside) {
            moves.add(createMove(color, kingside)); //if legal, add queenside castling
        }
        
        return moves;
    }
    
    public static boolean isCastleMove(Board board, Color color, Square start, Square target) { //given a user's input of two squares, see if the moves is castling
        if (start.row != target.row) { //if the rows don't match, it can't be castling
            return false;
        }
        
        int direction = target.col - start.col; //get the difference of the columns of the squares
        
        if (Math.abs(direction) != 2) { //if the difference is not two, it is not castling
            return false;
        }
        
        //direction > 0 is kingside
        //direction < 0 is queenside
        return isLegal(board, color, direction > 0); //check if the pieces are correct, etc.
    }
    
    public static Move createMove(Color color, boolean kingside) { //create the castling move
        int[] castlingData = getCastlingData(color, true); //get data
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int kingTargetCol = castlingData[3];
        
        return new Move(row, kingStartCol, row, kingTargetCol, true, kingside, false);
    }
    
    public static boolean isLegal(Board board, Color color, boolean kingside) { //checks legality of castling in a given position
        int[] castlingData = getCastlingData(color, kingside); //get data
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int rookStartCol = castlingData[2];
        
        Piece king = board.getPiece(row, kingStartCol); //get the pieces
        Piece rook = board.getPiece(row, rookStartCol);

        return arePiecesValid(king, rook) && isPathClear(board, color, row, kingStartCol, kingside); //return if the pieces are valid and the path is clear
    }

    private static boolean arePiecesValid(Piece king, Piece rook) { //checks if the pieces are correct and if they have moved or not
        return king != null && rook != null && (king instanceof King) && (rook instanceof Rook) && !king.hasMoved && !rook.hasMoved;
    }

    private static boolean isPathClear(Board board, Color color, int row, int kingStartCol, boolean kingside) { //check if the path for castling is clear
        int start = kingside ? kingStartCol + 1 : 2; //get the starting and ending columns based on what side you are castling
        int end = kingside ? 6 : kingStartCol - 1;
        
        for (int col = start; col <= end; col++) { //iterate through the start and end
            if (board.getPiece(row, col) != null) { //if there is a piece blocking the path, return false
                return false;
            }
            
            //make sure the squares in the middle aren't being attacked by an opposing piece
            Board copy = board.clone();
            copy.makeMove(new Move(row, kingStartCol, row, col, false, false, false));
            if (copy.isInCheck(color)) {
                return false;
            }
        }

        return true;
    }
}
