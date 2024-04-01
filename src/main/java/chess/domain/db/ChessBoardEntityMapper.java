package chess.domain.db;

import chess.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardEntityMapper {
    private final List<PieceEntity> pieces;

    private ChessBoardEntityMapper(List<PieceEntity> pieces) {
        this.pieces = pieces;
    }

    public static ChessBoardEntityMapper of(List<Piece> pieces) {
        List<PieceEntity> piece = makeChessBoard(pieces);
        return new ChessBoardEntityMapper(piece);
    }

    private static List<PieceEntity> makeChessBoard(List<Piece> pieces) {
        List<PieceEntity> pieceSet = new ArrayList<>();
        pieces.forEach(piece -> pieceSet.add(
                new PieceEntity(piece.getPosition().getPosition(), piece.getTeam().name(), piece.getType().name())));
        return pieceSet;
    }

    public List<PieceEntity> getPieces() {
        return pieces;
    }
}
