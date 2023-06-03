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
    private Square start;
    private Square target;
    private boolean castling;
    private boolean kingside;

    public Move(Square start, Square target, boolean castling, boolean kingside) {
        this.start = start;
        this.target = target;
        this.castling = castling;
        this.kingside = kingside;
    }

    public Move(int startRow, int startCol, int targetRow, int targetCol, boolean castling, boolean kingside) {
        this.start = new Square(startRow, startCol);
        this.target = new Square(targetRow, targetCol);
        this.castling = castling;
        this.kingside = kingside;
    }

    public int getStartRow() {
        return start.row;
    }

    public int getStartCol() {
        return start.col;
    }

    public int getTargetRow() {
        return target.row;
    }

    public int getTargetCol() {
        return target.col;
    }

    public Square getStart() {
        return start;
    }

    public Square getTarget() {
        return target;
    }

    public boolean isCastleMove() {
        return castling;
    }

    public boolean isCastleKingside() {
        return kingside;
    }
    
    public boolean isPromotion(Piece p) {
        int promotionRank = Game.currentTurn == Color.WHITE ? 0 : 7;
        
        return this.getTargetRow() == promotionRank && p instanceof Pawn;
    }
    
    public String toString() {
        if (castling) {
            return kingside ? "O-O" : "O-O-O";
        }
        return indexToSquare(start.row, start.col) + "->" + indexToSquare(target.row, target.col);
    }
    
    public static String indexToSquare(int row, int col) {
        return "" + ((char)(col + 'a')) + (8-row);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Move)) {
            return false;
        }
        
        Move m = (Move) o;
        
        return this.start.equals(m.start) && this.target.equals(m.target);
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + start.hashCode();
        result = 31 * result + target.hashCode();
        return result;
    }
}

