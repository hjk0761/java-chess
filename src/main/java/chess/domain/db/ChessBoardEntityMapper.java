package chess.domain.db;

import chess.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardEntityMapper {
    private final List<ChessBoardEntity> pieces;

    private ChessBoardEntityMapper(List<ChessBoardEntity> pieces) {
        this.pieces = pieces;
    }

    public static ChessBoardEntityMapper of(List<Piece> pieces) {
        List<ChessBoardEntity> piece = makeChessBoard(pieces);
        return new ChessBoardEntityMapper(piece);
    }

    private static List<ChessBoardEntity> makeChessBoard(List<Piece> pieces) {
        List<ChessBoardEntity> pieceSet = new ArrayList<>();
        pieces.forEach(piece -> pieceSet.add(
                new ChessBoardEntity(piece.getPosition().getPosition(), piece.getTeam().name(), piece.getType().name())));
        return pieceSet;
    }

    public List<ChessBoardEntity> getPieces() {
        return pieces;
    }
}
