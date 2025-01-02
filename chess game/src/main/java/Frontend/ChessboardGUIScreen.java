package Frontend;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Backend.BoardFile;
import Backend.BoardRank;
import Backend.ChessGame;
import Backend.ChessGameCaretaker;
import Backend.Square;
import Backend.Piece;
import Backend.ClassicChessGame;
import Backend.Player;
import java.awt.event.ActionEvent;

public class ChessboardGUIScreen extends JFrame {

    private JButton[][] ChessBoardButtons;
    private ChessGame chessGame;
    private Square selectedSquare;
    private ChessboardButtonActionPerformer chessboardButtonPerfomer;
    private JPanel chessboardPanel;

    public ChessboardGUIScreen(ChessGame chessGame) {
        this.chessGame = chessGame;
        initializeChessBoardScreenGrid();
        initializeChessboardButtonListener();
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    public JButton[][] getSquareButtons() {
        return ChessBoardButtons;
    }

    private void initializeChessBoardScreenGrid() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessboardPanel = new JPanel(new GridLayout(8, 8));
        ChessBoardButtons = new JButton[8][8];
        for (int RankX = 0; RankX < 8; RankX++) {
            for (int FileY = 0; FileY < 8; FileY++) {
                JPanel square = new JPanel();
                square.setPreferredSize(new Dimension(60, 60));
                JButton squareButton = new JButton();
                squareButton.setPreferredSize(new Dimension(60, 60));
                if ((RankX + FileY) % 2 == 0) {
                    squareButton.setBackground(Color.WHITE);
                } else {
                    squareButton.setBackground(Color.gray);
                }
                Piece piece = chessGame.getPieceAtSquare(new Square(BoardFile.values()[FileY], BoardRank.values()[RankX]));
                if (piece != null) {
                    try {
                        String imagePath = getImagePath(piece);
                        ImageIcon pieceIcon = new ImageIcon(ImageIO.read(new File(imagePath)));
                        pieceIcon.setImage(pieceIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                        JLabel pieceLabel = new JLabel(pieceIcon);
                        pieceLabel.putClientProperty("piece", piece);
                        squareButton.add(pieceLabel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ChessBoardButtons[RankX][FileY] = squareButton;
                squareButton.addActionListener(chessboardButtonPerfomer);
                chessboardPanel.add(squareButton);
            }
        }
        add(chessboardPanel);
        pack();
        setLocationRelativeTo(null);
    }

    protected void flipBoard() {
        chessboardPanel.removeAll();
        for (int RankX = 0; RankX < 8; RankX++) {
            for (int FileY = 0; FileY < 8; FileY++) {
                JButton FlippedButton;
                if (chessGame.getWhoseTurn()==Player.BLACK) {
                    FlippedButton = ChessBoardButtons[7 - RankX][7 - FileY];
                } else {
                    FlippedButton = ChessBoardButtons[RankX][FileY];
                }
                FlippedButton.removeAll();
                if ((RankX + FileY) % 2 == 0) {
                    FlippedButton.setBackground(Color.WHITE);
                } else {
                    FlippedButton.setBackground(Color.gray);
                }
                Piece FlippedPiece;
                if (chessGame.getWhoseTurn()==Player.BLACK) {
                    FlippedPiece = chessGame.getPieceAtSquare(new Square(BoardFile.values()[7 - FileY], BoardRank.values()[7 - RankX]));
                } else {
                    FlippedPiece = chessGame.getPieceAtSquare(new Square(BoardFile.values()[FileY], BoardRank.values()[RankX]));
                }
                if (FlippedPiece != null) {
                    try {
                        String imagePath = getImagePath(FlippedPiece);
                        ImageIcon pieceIcon = new ImageIcon(ImageIO.read(new File(imagePath)));
                        pieceIcon.setImage(pieceIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                        JLabel pieceLabel = new JLabel(pieceIcon);
                        pieceLabel.putClientProperty("piece", FlippedPiece);
                        FlippedButton.add(pieceLabel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                chessboardPanel.add(FlippedButton);
            }
        }
        chessboardPanel.revalidate();
        chessboardPanel.repaint();
    }

    private String getImagePath(Piece piece) {
        String PieceColor;
        if (piece.getOwner() == Player.WHITE) {
            PieceColor = "White";
        } else {
            PieceColor = "Black";
        }
        String pieceType = piece.getClass().getSimpleName();
        return PieceColor + pieceType + ".png";
    }

    private void initializeChessboardButtonListener() {
        chessboardButtonPerfomer = new ChessboardButtonActionPerformer(this);
        for (JButton[] row : ChessBoardButtons) {
            for (JButton button : row) {
                button.addActionListener(chessboardButtonPerfomer);
            }
        }
    }

    public void setSelectedSquare(Square selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    public Square getSelectedSquare() {
        return selectedSquare;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGame chessGame = new ClassicChessGame();
            ChessGameCaretaker careTaker = new ChessGameCaretaker(chessGame);
            ChessboardGUIScreen chessboardGUI = new ChessboardGUIScreen(chessGame);
            JFrame chessboardFrame = new JFrame("Chess");
            chessboardFrame.setSize(800, 600);
            JButton undoButton = new JButton("Undo");
            undoButton.addActionListener((ActionEvent e) -> {
                if (careTaker.undoMove()) {
                    chessboardGUI.flipBoard();
                } else {
                    JOptionPane.showMessageDialog(null, "no more undo avaliable");
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(undoButton);
            chessboardFrame.setLayout(new BorderLayout());
            chessboardFrame.add(chessboardGUI.chessboardPanel, BorderLayout.CENTER);
            chessboardFrame.add(buttonPanel, BorderLayout.EAST);
            chessboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chessboardFrame.setLocationRelativeTo(null);
            chessboardFrame.setVisible(true);
        });
    }
}
