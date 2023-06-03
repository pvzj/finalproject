package finalproject;

import java.util.*;
import javax.swing.Icon;

public class Board {
    public Piece[][] board;
    
    public Board() {
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }
    
    public Board(String FEN) {
        board = new Piece[8][8];
        FENToPosition(FEN);
    }
    
    public Piece[][] getBoard() {
        return this.board;
    }
    
    public void FENToPosition(String FEN) {
        resetBoard();
        
        int rowPointer = 0;
        int colPointer = 0;
        
        for (char c : FEN.toCharArray()) {
            if (c == '/') {
                rowPointer++;
                colPointer = 0;
            } else if (Character.isDigit(c)) {
                int numEmptySquares = Character.getNumericValue(c);
                for (int i = 0; i < numEmptySquares; i++) {
                    setPiece(rowPointer, colPointer, null);
                    colPointer++;
                }
            } else {
                setPiece(rowPointer, colPointer, charToPiece(c, rowPointer, colPointer));
                colPointer++;
            }
        }
    }
    
    private void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setPiece(i, j, null);
            }
        }
    }
    
    private static Piece charToPiece(char c, int row, int col) {
        switch (c) {
            case 'r':
                return new Rook(Color.BLACK, row, col);
            case 'R':
                return new Rook(Color.WHITE, row, col);
            case 'n':
                return new Knight(Color.BLACK, row, col);
            case 'N':
                return new Knight(Color.WHITE, row, col);
            case 'b':
                return new Bishop(Color.BLACK, row, col);
            case 'B':
                return new Bishop(Color.WHITE, row, col);
            case 'q':
                return new Queen(Color.BLACK, row, col);
            case 'Q':
                return new Queen(Color.WHITE, row, col);
            case 'k':
                return new King(Color.BLACK, row, col);
            case 'K':
                return new King(Color.WHITE, row, col);
            case 'p':
                return new Pawn(Color.BLACK, row, col);
            case 'P':
                return new Pawn(Color.WHITE, row, col);
            default:
                return null;
        }
    }
    
    public void displayBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board[row][col];
                
                if (p == null) {
                    System.out.print('-');
                } else {
                    System.out.print(p.getCharacter());
                }
            }
            System.out.println();
        }
    }
    
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }
    
    public Piece getPiece(Square s) {
        return board[s.row][s.col];
    }
    
    public void setPiece(int row, int col, Piece p) {
        board[row][col] = p;
    }
    
    public void setPiece(Square s, Piece p) {
        board[s.row][s.col] = p;
    }
    
    
    
    public Set<Move> getAllMoves(Color c) {
        Set<Move> moves = new HashSet<>();
        
        for (Piece[] r : board) {
             for (Piece p : r) {
                 if (p == null || p.color != c) {
                     continue;
                 }
                 moves.addAll(p.possibleMoves(this));
             }
        }
        
        return moves;
    }
    
    public Set<Move> getLegalMoves(Color c) {
        Set<Move> legalMoves = new HashSet<>();
        for (Move m : this.getAllMoves(c)) {
            Board temp = this.clone();
            
            temp.makeMove(m);
            if (!temp.isInCheck(c)) {
                legalMoves.add(m);
            } 
            
        }
        
        legalMoves.addAll(Castling.getCastleMoves(this, c));
        
        return legalMoves;  
    }
    
    public boolean isInCheck(Color color) {
        Square kingCoordinates = getKingCoordinates(color);
        for (Piece[] r : board) {
            for (Piece p : r) {
                if (p == null || p.color == color) {
                    continue;
                }
                
                List<Move> moves = p.possibleMoves(this);
                
                for (Move m : moves) {
                    if (m.getTarget().equals(kingCoordinates)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public Square getKingCoordinates(Color c) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Piece p = this.getPiece(i, j);
                
                if ((p instanceof King) && p.color == c) {
                    return new Square(i, j);
                }
            }
        }
        return null;
    }
    
    public void makeMove(Move m) {
        if (m.isCastleMove()) {
            Castling.performCastlingMove(this, Game.currentTurn,m.isCastleKingside());
            return;
        }
        
        Piece p = this.getPiece(m.getStart());
        this.setPiece(m.getStartRow(), m.getStartCol(), null);
        this.setPiece(m.getTargetRow(), m.getTargetCol(), p);

        Piece clonedPiece = p.clone();
        clonedPiece.position = new Square(m.getTargetRow(), m.getTargetCol());
        
        this.setPiece(m.getTargetRow(), m.getTargetCol(), clonedPiece);
        
        clonedPiece.hasMoved = true;
        
//        System.out.println(clonedPiece.getCharacter());
    }
    
    @Override
    public Board clone() {
        Board newBoard = new Board();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                Piece p = this.getPiece(i, j);

                if (p == null) {
                    newBoard.setPiece(i, j, null);
                } else {
                    Piece clonedPiece = p.clone();
                    newBoard.setPiece(i, j, clonedPiece);
                    clonedPiece.position.row = i;
                    clonedPiece.position.col = j;
                }
            }
        }
        return newBoard;
    }
}
