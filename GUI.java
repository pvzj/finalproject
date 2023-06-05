/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package finalproject;

/**
 *
 * @author jchen
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUI { //GUI for chessboard
    public static final String filepath = "src/main/java/finalproject/icons/";
    public static Square moveProcessFirstSquare; //first input square from user

    public static void init() { //create GUI
        JFrame frame = new JFrame("Chess"); //title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessboardPanel chessboardPanel = new ChessboardPanel(); //create chessboard panel
        frame.add(chessboardPanel);

        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(560, 585)); //set dimensions
        frame.pack();
        
        frame.setVisible(true); //set visible
    }
    
    public static void closeWindow() { //close window
        System.exit(0);
    }

    private static class ChessboardPanel extends JPanel { //chessboard panel
        private static final int SQUARE_SIZE = 68; //size of each panel
        private static final java.awt.Color LIGHT_SQUARE_COLOR = new java.awt.Color(238, 238, 210); //color of squares
        private static final java.awt.Color DARK_SQUARE_COLOR = new java.awt.Color(118, 150, 86);

        public ChessboardPanel() { //constructor
            setLayout(new GridLayout(Board.BOARD_DIMENSION, Board.BOARD_DIMENSION)); //set grid layout
            setPreferredSize(new Dimension(SQUARE_SIZE * Board.BOARD_DIMENSION, SQUARE_SIZE * Board.BOARD_DIMENSION)); //set size
            drawPieces(); //draw pieces
        }

        private void drawPieces() {
            removeAll(); //clear the board
            
            for (int row = 0; row < Board.BOARD_DIMENSION; row++) {
                for (int col = 0; col < Board.BOARD_DIMENSION; col++) {
                    Piece p = Game.gameboard.getPiece(row, col); //get the piece in the square
                    
                    JButton squareButton; //button represents the squares
                    
                    if (p != null) { //if the square on the board has a piece on it
                        Image pieceImage = getIcon(p); //get icon of the piece
                        squareButton = new JButton(new ImageIcon(pieceImage)); //add a button with the image
                    } else {
                        squareButton = new JButton(); //otherwise just add the button
                    }
                    
                    squareButton.setBackground((row + col) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR); //set the blackground color of the button
                    squareButton.setOpaque(true);
                    squareButton.setBorderPainted(false);
                    squareButton.setFocusPainted(false);
                    
                    squareButton.addActionListener(new SquareButtonActionListener(row, col)); //add the action listener to the button
                    
                    add(squareButton); //add button to the panel
                }
            }
            
            revalidate();
            repaint();
        }

        private class SquareButtonActionListener implements ActionListener {
            private final int row; 
            private final int col;

            public SquareButtonActionListener(int row, int col) { //listen based on button's row and column
                this.row = row;
                this.col = col;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked Square: " + row + ", " + col); //print out clicked square for debugging
                
                Square s = new Square(row, col); //make square object
                
                if (moveProcessFirstSquare == null) { //if a square was not previously clicked
                    moveProcessFirstSquare = s; //set the first square of the move to this square
                } else {
                    Game.makeMove(moveProcessFirstSquare, s); //otherwise make the move
                    moveProcessFirstSquare = null; //clear the first square of the move process
                    drawPieces(); //redraw the pieces
                    
                }
            }
        }
        
        public BufferedImage getIcon(Piece p) { //get icon of the piece
            String color = p.getColor() == Color.WHITE ? "white" : "black"; //get whether the piece is white or black
            String pieceName = p.getPieceName(); //get piece name
            
            try { //find the image
                return ImageIO.read(new File((filepath + color + pieceName + ".png"))); 
            } catch (IOException e) { //if images are not found, close window
                System.out.println("Images Not Found");
                closeWindow();
                return null; 
            }
        }
    }
}