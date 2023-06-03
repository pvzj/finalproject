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
public class Castling {
    
    private static int[] getCastlingData(Color color, boolean kingside) {
        int row = (color == Color.WHITE) ? 7 : 0;
        int kingStartCol = 4;
        int rookStartCol = kingside ? 7 : 0;
        int kingTargetCol = kingside ? 6 : 2;
        int rookTargetCol = kingside ? 5 : 3;

        return new int[]{ row, kingStartCol, rookStartCol, kingTargetCol, rookTargetCol };
    }

    public static void performCastlingMove(Board board, Color color, boolean kingside) {
        int[] castlingData = getCastlingData(color, kingside);
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int rookStartCol = castlingData[2];
        int kingTargetCol = castlingData[3];
        int rookTargetCol = castlingData[4];
        
        board.makeMove(new Move(row, kingStartCol, row, kingTargetCol, false, false));
        board.makeMove(new Move(row, rookStartCol, row, rookTargetCol, false, false));
    }
    
    public static Set<Move> getCastleMoves(Board board, Color color) {
        Set<Move> moves = new HashSet<>();
        
        if (board.isInCheck(color)) {
            return moves;
        }
        
        boolean kingside = isLegal(board, color, true);
        boolean queenside = isLegal(board, color, false);
        
        
        if (kingside) {
            moves.add(createMove(color, kingside));
        }
        if (queenside) {
            moves.add(createMove(color, kingside));
        }
        
        return moves;
    }
    
    public static boolean isCastleMove(Board board, Color color, Square start, Square target) {
        if (start.row != target.row) {
            return false;
        }
        
        int direction = target.col - start.col;
        
        if (Math.abs(direction) != 2) {
            return false;
        }
        return isLegal(board, color, direction > 0);
    }
    
    public static Move createMove(Color color, boolean kingside) {
        int[] castlingData = getCastlingData(color, true);
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int kingTargetCol = castlingData[3];
        
        return new Move(row, kingStartCol, row, kingTargetCol, true, kingside);
    }
    
    public static boolean isLegal(Board board, Color color, boolean kingside) {
        int[] castlingData = getCastlingData(color, kingside);
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int rookStartCol = castlingData[2];
        int kingTargetCol = castlingData[3];
        int rookTargetCol = castlingData[4];
        
        Piece king = board.getPiece(row, kingStartCol);
        Piece rook = board.getPiece(row, rookStartCol);
        
        if (!arePiecesValid(king, rook)) {
            return false;
        }

        if (!isPathClear(board, color, row, kingStartCol, rookStartCol, kingTargetCol, rookTargetCol, kingside)) {
            return false;
        }

        return true;
    }

    private static boolean arePiecesValid(Piece king, Piece rook) {
        return king != null && rook != null && (king instanceof King) && (rook instanceof Rook) && !king.hasMoved && !rook.hasMoved;
    }

    private static boolean isPathClear(Board board, Color color, int row, int kingStartCol, int rookStartCol, int kingTargetCol, int rookTargetCol, boolean kingside) {
        int start = kingside ? kingStartCol + 1 : 2;
        int end = kingside ? 6 : kingStartCol - 1;
        
        for (int col = start; col <= end; col++) {
            if (board.getPiece(row, col) != null) {
                return false;
            }
            
            Board copy = board.clone();
            
            copy.makeMove(new Move(row, kingStartCol, row, col, false, false));
            if (copy.isInCheck(color)) {
                return false;
            }
        }

        return true;
    }
}
