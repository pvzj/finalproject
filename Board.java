package finalproject;

import java.util.*;
import javax.swing.Icon;

public class Board { //represents the chessboard
    public Piece[][] board; //2d array of pieces 
    
    public static final int BOARD_DIMENSION = 8;
    
    public Board() { //default constructor, no parameters
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"); //intializes board from starting position
    }
    
    public Board(String FEN) { //constructor with FEN
        board = new Piece[BOARD_DIMENSION][BOARD_DIMENSION]; //initialize array
        FENToPosition(FEN); //convert the FEN into the chessboard position
    }
    
    public void FENToPosition(String FEN) { //see: https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
        resetBoard(); //clear the board first
        
        int rowPointer = 0; //pointers to show which row and column is being filled
        int colPointer = 0;
        
        for (char c : FEN.toCharArray()) { //iterate through string
            if (c == '/') { //means new line
                rowPointer++;
                colPointer = 0;
            } else if (Character.isDigit(c)) { //if a digit n occurs, it means n empty spaces
                int numEmptySquares = Character.getNumericValue(c);
                for (int i = 0; i < numEmptySquares; i++) {
                    setPiece(rowPointer, colPointer, null);
                    colPointer++;
                }
            } else { //otherwise it means there is a piece, and put the piece on the board
                setPiece(rowPointer, colPointer, charToPiece(c, rowPointer, colPointer));
                colPointer++;
            }
        }
    }
    
    private void resetBoard() { //fill the board with null
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                setPiece(i, j, null);
            }
        }
    }
    
    private static Piece charToPiece(char c, int row, int col) { //given a character, return the piece associated with it
        switch (c) { //switch statement
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
    
    public void displayBoard() { //display teh board in the terminal
        for (int row = 0; row < BOARD_DIMENSION; row++) {
            for (int col = 0; col < BOARD_DIMENSION; col++) {
                Piece p = board[row][col];
                
                if (p == null) { //if the piece is null (the square is empty), print a -
                    System.out.print('-');
                } else { //otherwise print the piece's character
                    System.out.print(p.getCharacter());
                }
            }
            System.out.println(); //new line
        }
    }
    
    public Set<Move> getAllMoves(Color color) { //for every piece on the board, 
        Set<Move> moves = new HashSet<>();
        
        for (Piece[] row : board) { //iterate through every square on the board
             for (Piece piece : row) {
                 if (piece == null || piece.getColor() != color) { //if the piece is empty or not of the desired color, do nothing
                     continue;
                 }
                 moves.addAll(piece.possibleMoves(this)); //add the piece's moves to the set with all the possible moves
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
    
    
    
    //clone the board
    //this is used to test the legality of a move
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
    
    //getter/setters
    public Piece[][] getBoard() {
        return this.board;
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
}
