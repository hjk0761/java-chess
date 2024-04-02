package chess.domain.db;

import chess.domain.piece.*;
import chess.domain.pieceInfo.PieceInfo;
import chess.domain.pieceInfo.Position;
import chess.domain.pieceInfo.Team;

import java.util.ArrayList;
import java.util.List;

public class PieceMapper {

    public static List<Piece> toPieces(final List<PieceEntity> pieceEntities) {
        if (pieceEntities.isEmpty()) {
            throw new IllegalArgumentException("이전에 save 한 정보가 없습니다.");
        }
        return makePieces(pieceEntities);
    }

    private static List<Piece> makePieces(final List<PieceEntity> boards) {
        List<Piece> newBoard = new ArrayList<>();
        boards.forEach(board -> newBoard.add(piece(board)));
        return newBoard;
    }

    private static Position position(final PieceEntity board) {
        return Position.of(board.position());
    }

    private static Piece piece(final PieceEntity board) {
        List<Piece> allPieces = pieces(position(board), Team.valueOf(board.team()));
        return allPieces.stream()
                .map(Piece::rearrangeStrategyByPosition)
                .filter(piece -> piece.getType().name().equals(board.type()))
                .findFirst()
                .orElse(new EmptyPiece(new PieceInfo(position(board), Team.valueOf(board.team()))));
    }

    private static List<Piece> pieces(final Position position, final Team team) {
        PieceInfo pieceInfo = new PieceInfo(position, team);
        return List.of(
                new King(pieceInfo),
                new Queen(pieceInfo),
                new Bishop(pieceInfo),
                new Knight(pieceInfo),
                new Rook(pieceInfo),
                new Pawn(pieceInfo)
        );
    }

    public static List<PieceEntity> toPieceEntities(final List<Piece> pieces) {
        return makePieceEntities(pieces);
    }

    private static List<PieceEntity> makePieceEntities(final List<Piece> pieces) {
        List<PieceEntity> pieceSet = new ArrayList<>();
        pieces.forEach(piece -> pieceSet.add(
                new PieceEntity(piece.getPosition().getPosition(), piece.getTeam().name(), piece.getType().name())));
        return pieceSet;
    }
}
