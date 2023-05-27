/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change board license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit board template
 */
package finalproject;

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
        
//        Piece king = board.getPiece(row, kingStartCol);

        return new int[]{ row, kingStartCol, rookStartCol, kingTargetCol, rookTargetCol };
    }

    public static void performCastlingMove(Board board, Color color, boolean kingside) {
        int[] castlingData = getCastlingData(color, kingside);
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int rookStartCol = castlingData[2];
        int kingTargetCol = castlingData[3];
        int rookTargetCol = castlingData[4];
        
        Piece king = board.getPiece(row, kingStartCol);
        Piece rook = board.getPiece(row, rookStartCol);
        
        board.makeMove(new Move(king, row, kingTargetCol, false, false));
        board.makeMove(new Move(rook, row, rookTargetCol, false, false));
    }
    
    public static void checkCastlingLegality(Board board, Color color, Set<Move> moves) {
        if (board.isInCheck(color)) {
            return;
        }
        
        boolean kingside = isLegal(board, color, true);
        boolean queenside = isLegal(board, color, false);
        
        
        if (kingside) {
            moves.add(createMove(board, color, kingside));
        }
        if (queenside) {
            moves.add(createMove(board, color, kingside));
        }
    }
    
    public static Move createMove(Board board, Color color, boolean kingside) {
        int[] castlingData = getCastlingData(color, true);
        
        int row = castlingData[0];
        int kingStartCol = castlingData[1];
        int kingTargetCol = castlingData[3];
        
        Piece king = board.getPiece(row, kingStartCol);
        
        return new Move(king, row, kingTargetCol, true, kingside);
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
            Piece targetPiece = board.getPiece(row, col);
            if (targetPiece != null) {
                return false;
            }
            
            Board copy = board.clone();
            
            Piece king = board.getPiece(row, kingStartCol);
            
            copy.makeMove(new Move(king, row, col, false, false));
            if (copy.isInCheck(color)) {
                return false;
            }
        }

        return true;
    }
}
