package finalproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class Board { //represents the chessboard
    public Piece[][] board; //2d array of pieces 
    
    public static final int BOARD_DIMENSION = 8;
    
    public List<Move> moveLog = new ArrayList<>(); //records the whole game
    
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
    
    public Set<Move> getAllMoves(Color color) { //get all the possible moves on the board, disregarding check
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
    
    public Set<Move> getLegalMoves(Color c) { //get all the legal moves on the board, regarding check
        Set<Move> legalMoves = new HashSet<>();
        
        for (Move m : this.getAllMoves(c)) { //get all moves
            Board temp = this.clone(); //clone the board
            
            temp.makeMove(m); //make the move
            
            if (!temp.isInCheck(c)) { //if the player is not in check, after the move has been played
                legalMoves.add(m); //add it to the legal moves
            } 
            
        }
        
        //add the legal castling moves, since castling is handled separately
        legalMoves.addAll(Castling.getCastleMoves(this, c)); 
        
        return legalMoves;  
    }
    
    public boolean isInCheck(Color color) { //checks if the given color is in check
        Square kingCoordinates = getKingCoordinates(color); //get the king's position
        
        for (Piece[] row : board) {
            for (Piece piece : row) { //iterate through the board
                if (piece == null || piece.color == color) { //only get pieces of the opposite color
                    continue;
                }
                
                List<Move> moves = piece.possibleMoves(this); //get the piece's possible moves
                
                //if any of the piece's moves target the king, return true, since the king is in check
                for (Move m : moves) {
                    if (m.getTarget().equals(kingCoordinates)) {
                        return true;
                    }
                }
            }
        }
        //otherwise return false
        return false;
    }
    
    public Square getKingCoordinates(Color c) {
        for (int row = 0; row < board.length; row++) { //iterate through the board
            for (int col = 0; col < board[0].length; col++) {
                Piece piece = this.getPiece(row, col); //get piece on the square
                
                if (piece == null) {
                    continue;
                }
                
                //check if the piece is a king and its color matches
                if ((piece instanceof King) && piece.color == c) {
                    return new Square(row, col); //return this square
                }
            }
        }
        return null;
    }
    
    public void makeMove(Move m) { //make a move on the board
        if (m.isCastleMove()) { //castling is handled separately
            Castling.performCastlingMove(this, Game.currentTurn,m.isCastleKingside());
            return;
        } else if (m.isEnPassant()) {
            this.setPiece(m.getStartRow(), m.getTargetCol(), null);
        }
        
        Piece p = this.getPiece(m.getStart()); //get the piece at the start
        
        this.setPiece(m.getStart(), null); //set the starting position to null
        
        //create clone of the piece, as to not modify the original position
        //this is because the code calls this method for every possible move to see if the move is legal
        //therefore, the original piece should not be modified and a copy should be used
        Piece clonedPiece = p.clone(); 
        clonedPiece.position = new Square(m.getTarget()); //set the position of the piece
        
        this.setPiece(m.getTarget(), clonedPiece); //set the target
        
        //update the moved status
        clonedPiece.hasMoved = true;
        moveLog.add(m);
    }
    
    //clone the board
    //this is used to test the legality of a move
    @Override
    public Board clone() {
        Board newBoard = new Board();
        for (int row = 0; row < this.board.length; row++) { //iterate through the board
            for (int col = 0; col < this.board[0].length; col++) {
                Piece p = this.getPiece(row, col); //get the piece on the old board

                if (p == null) {
                    newBoard.setPiece(row, col, null); //set the piece to null
                } else {
                    Piece clonedPiece = p.clone(); //clone the piece
                    newBoard.setPiece(row, col, clonedPiece); //put the piece on the board
                    
                    clonedPiece.position.row = row; //update the piece's position
                    clonedPiece.position.col = col;
                }
            }
        }
        return newBoard;
    }
    
    public Move getPreviousMove() { //returns the previous move played in the log
        if (moveLog.isEmpty()) {
            return null;
        }
        
        return moveLog.get(moveLog.size() - 1);
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
