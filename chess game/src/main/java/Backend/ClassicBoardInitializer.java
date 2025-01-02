package Backend;

public final class ClassicBoardInitializer implements BoardInitializer {
    private static final BoardInitializer instance = new ClassicBoardInitializer();
    private final PieceFactory pawnFactory;
    private final PieceFactory rookFactory ;
    private final PieceFactory kingFactory ;
    private final PieceFactory knightFactory ;
    private final PieceFactory queenFactory ;
    private final PieceFactory bishopFactory ;

    private ClassicBoardInitializer() {
        this.pawnFactory = new PawnFactory();
        this.rookFactory = new RookFactory();
        this.bishopFactory = new BishopFactory();
        this.kingFactory = new KingFactory();
        this.queenFactory = new QueenFactory();
        this.knightFactory = new KnightFactory();
        
    }

    public static BoardInitializer getInstance() {
        return instance;
    }

    @Override
    public Piece[][] initialize() {
        Piece[][] initialState = {
                {rookFactory.createPiece(Player.BLACK), knightFactory.createPiece(Player.BLACK), bishopFactory.createPiece(Player.BLACK), queenFactory.createPiece(Player.BLACK), kingFactory.createPiece(Player.BLACK), bishopFactory.createPiece(Player.BLACK), knightFactory.createPiece(Player.BLACK), rookFactory.createPiece(Player.BLACK)},
                {pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE)},
                {rookFactory.createPiece(Player.WHITE), knightFactory.createPiece(Player.WHITE), bishopFactory.createPiece(Player.WHITE), queenFactory.createPiece(Player.WHITE), kingFactory.createPiece(Player.WHITE), bishopFactory.createPiece(Player.WHITE), knightFactory.createPiece(Player.WHITE), rookFactory.createPiece(Player.WHITE)}
        };
        return initialState;
    }
}
