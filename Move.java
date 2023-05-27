/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Move {
//    private Square start;
    private Piece piece;
    private Square target;
    private boolean castling;
    private boolean kingside;

    public Move(Piece piece, Square target, boolean castling, boolean kingside) {
        this.piece = piece;
        this.target = target;
        this.castling = castling;
        this.kingside = kingside;
    }
    
    public Move(Piece piece, int targetRow, int targetCol, boolean castling, boolean kingside) {
        this.piece = piece;
        this.target = new Square(targetRow, targetCol);
        this.castling = castling;
        this.kingside = kingside;
    }
    

//    public Move(Square start, Square target, boolean castling, boolean kingside) {
//        this.start = start;
//        this.target = target;
//        this.castling = castling;
//        this.kingside = kingside;
//    }
//
//    public Move(int startRow, int startCol, int targetRow, int targetCol, boolean castling, boolean kingside) {
//        this.start = new Square(startRow, startCol);
//        this.target = new Square(targetRow, targetCol);
//        this.castling = castling;
//        this.kingside = kingside;
//    }
//
//    public int getStartRow() {
//        return start.row;
//    }
//
//    public int getStartCol() {
//        return start.col;
//    }

    public Piece getPiece() {
        return piece;
    }
    
    public int getTargetRow() {
        return target.row;
    }

    public int getTargetCol() {
        return target.col;
    }

//    public Square getStart() {
//        return start;
//    }

    public Square getTarget() {
        return target;
    }

    public boolean isCastleMove() {
        return castling;
    }

    public boolean isCastleKingside() {
        return kingside;
    }
    
    public String toString() {
        if (castling) {
            return kingside ? "O-O" : "O-O-O";
        }
        String returnString = "";
        
        if (!(piece instanceof Pawn)) {
            returnString += Character.toUpperCase(piece.getCharacter());
        }
        return returnString + indexToSquare(target.row, target.col);
    }
    
    public static String indexToSquare(int row, int col) {
        return "" + ((char)(col + 'a')) + (8-row);
    }
}

