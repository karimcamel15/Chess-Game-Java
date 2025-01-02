/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.ChessGame.ChessGameMemento;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author M
 */
public class ChessGameCaretaker {

    private final ChessGame chessGame;

    public ChessGameCaretaker(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public void saveState() {
        ChessGameMemento memento = new ChessGameMemento(chessGame);
        chessGame.undoStack.push(memento);
    }

    public boolean undoMove() {
        if (!chessGame.undoStack.isEmpty()) {
            ChessGameMemento memento = chessGame.undoStack.pop();
            chessGame.restoreBoardAfterUndo(memento);
        }
        else{
            return false;
        }
        return true;
    }
}
