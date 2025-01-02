///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package Frontend;

import Backend.BoardFile;
import Backend.BoardRank;
import Backend.ChessBoard;
import Backend.Piece;
import Backend.Square;
import Backend.ChessGame;
import Backend.Move;
import Backend.GameStatus;
import Backend.Player;
import Backend.Utilities;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class ChessboardButtonActionPerformer implements ActionListener {

    private final ChessboardGUIScreen chessboardGUI;

    public ChessboardButtonActionPerformer(ChessboardGUIScreen chessboardGUI) {
        this.chessboardGUI = chessboardGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedChessBoardButton = (JButton) e.getSource();
        JButton[][] squareButtons = chessboardGUI.getSquareButtons();
        int clickedRANKX = -1, clickedFILEY = -1;
        for (int RankX = 0; RankX < squareButtons.length; RankX++) {
            for (int FileY = 0; FileY < squareButtons[RankX].length; FileY++) {
                if (squareButtons[RankX][FileY] == clickedChessBoardButton) {
                    clickedRANKX = RankX;
                    clickedFILEY = FileY;
                    break;
                }
            }
        }
            Square clickedChessBoardSquare = new Square(BoardFile.values()[clickedFILEY], BoardRank.values()[clickedRANKX]);
            if (chessboardGUI.getSelectedSquare() == null) {
                selectedChessBoardSquare(clickedChessBoardSquare);
            } else {
                movePiece(chessboardGUI.getSelectedSquare(), clickedChessBoardSquare);
                checkGameStatus();
            }
    }

    private void selectedChessBoardSquare(Square selectedSquare) {
        ChessGame chessGame = chessboardGUI.getChessGame();
        Piece selectedPiece = chessGame.getPieceAtSquare(selectedSquare);
        if (selectedPiece != null && selectedPiece.getOwner() == chessGame.getWhoseTurn()) {
            List<Square> validMoves = chessGame.getAllValidMovesFromSquare(selectedSquare);

            if (!validMoves.isEmpty()) {
                chessboardGUI.setSelectedSquare(selectedSquare);
                HighlightedSquares(validMoves);
            } else {
                chessboardGUI.setSelectedSquare(null);
            }
        }
    }

    private void movePiece(Square OriginalChessBoardSquare, Square NewChessBoardSquare) {
        ChessGame chessGame = chessboardGUI.getChessGame();
        Move move = new Move(OriginalChessBoardSquare, NewChessBoardSquare);
        if (chessGame.isValidMove(move)) {
            chessGame.makeMove(move);
            updateChessBoardGUIScreen(OriginalChessBoardSquare, NewChessBoardSquare);
            chessboardGUI.setSelectedSquare(null);
            checkGameStatus();
            chessboardGUI.flipBoard();
        } else {
            chessboardGUI.setSelectedSquare(null);
        }
    }

    private JButton getButtonForSquare(Square square) {
        JButton[][] squareButtons = chessboardGUI.getSquareButtons();
        int RankX = square.getRank().getValue();
        int FileY = square.getFile().getValue();
        return squareButtons[RankX][FileY];
    }

    private void HighlightedSquares(List<Square> validMoves) {
        for (Square validMove : validMoves) {
            JButton button = getButtonForSquare(validMove);
            button.setBackground(Color.GREEN);
        }
    }

    private void updateChessBoardGUIScreen(Square OriginalChessBoardSquare, Square NewChessBoardSquare) {
        JButton OldButton = getButtonForSquare(OriginalChessBoardSquare);
        JButton NewButton = getButtonForSquare(NewChessBoardSquare);
        NewButton.removeAll();
        NewButton.add(OldButton.getComponent(0));
    }

    private void checkGameStatus() {
        ChessGame chessGame = chessboardGUI.getChessGame();
        GameStatus gameStatus = chessGame.getGameStatus();
        switch (gameStatus) {
            case WHITE_WON:
                JOptionPane.showMessageDialog(chessboardGUI, "White won by checkmate");
                break;
            case BLACK_WON:
                JOptionPane.showMessageDialog(chessboardGUI, "Black won by checkmate");
                break;
            case STALEMATE:
                JOptionPane.showMessageDialog(chessboardGUI, "Game ended in stalemate");
                break;
            case INSUFFICIENT_MATERIAL:
                JOptionPane.showMessageDialog(chessboardGUI, "Game ended in insufficient materials");
                break;
            case WHITE_UNDER_CHECK:
                ChessBoard board = chessGame.getBoard();
                Player turn = chessGame.getWhoseTurn();
                Square whiteking = Utilities.getKingSquare(turn, board);
                highlightKingSquare(whiteking);
                break;
            case BLACK_UNDER_CHECK:
                ChessBoard board1 = chessGame.getBoard();
                Player turn1 = chessGame.getWhoseTurn();
                Square Blackking = Utilities.getKingSquare(turn1, board1);
                highlightKingSquare(Blackking);
                break;
            case IN_PROGRESS:
                break;
        }
    }

    private void highlightKingSquare(Square kingSquare) {
        JButton button = getButtonForSquare(kingSquare);
        button.setBackground(Color.RED);
    }

}
