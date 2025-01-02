package Backend;

public final class Square {
    private final BoardFile file;
    private final BoardRank rank;

    public Square(BoardFile file, BoardRank rank) {
        this.file = file;
        this.rank = rank;
    }

    public BoardFile getFile() {
        return file;
    }

    public BoardRank getRank() {
        return rank;
    }
    @Override
    public String toString() {
        return "Square{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}
