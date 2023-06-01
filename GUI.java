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
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUI {
    public static Board gameboard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    public static Color currentTurn = Color.WHITE;
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
        gameboard.displayBoard();
        System.out.println(gameboard.getLegalMoves(Color.BLACK));
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Chessboard Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessboardPanel chessboardPanel = new ChessboardPanel();
        frame.add(chessboardPanel);
        
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(560, 585));
        
        frame.pack();
        frame.setVisible(true);
    }

    private static class ChessboardPanel extends JPanel {
        private static final int BOARD_SIZE = 8;
        private static final int SQUARE_SIZE = 68;
        private static final java.awt.Color LIGHT_SQUARE_COLOR = new java.awt.Color(238, 238, 210);
        private static final java.awt.Color DARK_SQUARE_COLOR = new java.awt.Color(118, 150, 86);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the chessboard squares
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    int x = col * SQUARE_SIZE;
                    int y = row * SQUARE_SIZE;

                    java.awt.Color squareColor = ((row + col) % 2 == 0) ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
                    g.setColor(squareColor);
                    g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
            
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    Piece p = gameboard.getPiece(row, col);
                    
                    if (p == null) {
                        continue;
                    }
                    Image pieceImage = getIcon(p);

                    int x = col * SQUARE_SIZE;
                    int y = row * SQUARE_SIZE;

                    g.drawImage(pieceImage, x, y, null);
                }
            }
        }
        
        public BufferedImage getIcon(Piece p) {
            String color = p.getColor() == Color.WHITE ? "white" : "black";
            String pieceName = p.getPieceName();
            
            try {
                return ImageIO.read(new File(("src/main/java/finalproject/icons/" + color + pieceName + ".png")));
            } catch (IOException e) {
                return null;
            }
        }
    }
}
