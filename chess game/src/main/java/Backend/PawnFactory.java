/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author M
 */
public class PawnFactory implements PieceFactory {
    @Override
    public Piece createPiece(Player owner) {
        return new Pawn(owner);
    }
}