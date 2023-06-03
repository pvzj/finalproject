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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUI {
    public static Board gameboard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    public static Color currentTurn = Color.WHITE;
    
    public static Square moveProcessFirstSquare;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
        gameboard.displayBoard();
        System.out.println(gameboard.getLegalMoves(Color.WHITE));
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Chess");
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

        public ChessboardPanel() {
            setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
            setPreferredSize(new Dimension(SQUARE_SIZE * BOARD_SIZE, SQUARE_SIZE * BOARD_SIZE));
            drawPieces();
        }

        private void drawPieces() {
            removeAll();
            
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    Piece p = gameboard.getPiece(row, col);
                    
                    JButton squareButton;
                    
                    if (p != null) {
                        Image pieceImage = getIcon(p);
                        squareButton = new JButton(new ImageIcon(pieceImage));
                    } else {
                        squareButton = new JButton();
                    }
                    
                    squareButton.setBackground((row + col) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR);
                    squareButton.setOpaque(true);
                    squareButton.setBorderPainted(false);
                    squareButton.setFocusPainted(false);
                    squareButton.addActionListener(new SquareButtonActionListener(row, col));
                    
                    add(squareButton);
                }
            }
            
            revalidate();
            repaint();
        }

        private class SquareButtonActionListener implements ActionListener {
            private final int row;
            private final int col;

            public SquareButtonActionListener(int row, int col) {
                this.row = row;
                this.col = col;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("Clicked Square: " + row + ", " + col);
                
                Square s = new Square(row, col);
                
                if (moveProcessFirstSquare == null) {
                    moveProcessFirstSquare = s;
                } else {
                    Move m = new Move(moveProcessFirstSquare, s, false, false);
//                    System.out.println(m);
                    
                    moveProcessFirstSquare = null;
                    
                    
                    System.out.println(gameboard.getLegalMoves(currentTurn));
                    if (gameboard.getLegalMoves(currentTurn).contains(m)) {
                        gameboard.makeMove(m);
                        
                        drawPieces();
                        
                        currentTurn = Color.otherColor(currentTurn);
                    }
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