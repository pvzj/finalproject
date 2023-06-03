/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
public class Move { //represents a move (start and target squares)
    private Square start;
    private Square target;
    private boolean castling; //booleans for if the move is a castlign move
    private boolean kingside;

    public Move(Square start, Square target, boolean castling, boolean kingside) { //constructors
        this.start = start;
        this.target = target;
        this.castling = castling;
        this.kingside = kingside;
    }

    public Move(int startRow, int startCol, int targetRow, int targetCol, boolean castling, boolean kingside) {
        this(new Square(startRow, startCol), new Square(targetRow, targetCol), castling, kingside);
    }

    //getters
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
    
    //is move a promotion move
    public boolean isPromotion(Piece p) {
        int promotionRank = Game.currentTurn == Color.WHITE ? 0 : 7;
        
        return this.getTargetRow() == promotionRank && p instanceof Pawn;
    }
    
    //tostring methods
    //mainly for testing
    //converts moves to algebraic notation
    public String toString() {
        if (castling) {
            return kingside ? "O-O" : "O-O-O";
        }
        return indexToSquare(start.row, start.col) + "->" + indexToSquare(target.row, target.col);
    }
    
    public static String indexToSquare(int row, int col) {
        return "" + ((char)(col + 'a')) + (8-row);
    }
    
    //checks if the moves have equal start and target squares
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Move)) {
            return false;
        }
        
        Move m = (Move) o;
        
        return this.start.equals(m.start) && this.target.equals(m.target);
    }
    
    //hashcode method required so that the hashcode is created based on only start and target squares
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + start.hashCode();
        result = 31 * result + target.hashCode();
        return result;
    }
}

