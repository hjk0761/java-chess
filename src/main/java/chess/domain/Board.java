package chess.domain;

import chess.domain.piece.EmptyPiece;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;
    private final RemovedPiece removedPiece;

    public Board() {
        Map<Position, Piece> board = new HashMap<>();
        initialize(board);

        this.board = board;
        this.removedPiece = new RemovedPiece();
    }

    private void initialize(Map<Position, Piece> board) {
        List<Piece> pieces = BoardInitializer.initialize();
        pieces.forEach(piece -> board.put(piece.getPieceInfo().getPosition(), piece));
    }

    public boolean movePieceAndRenewBoard(Position source, Position target) {
        Piece piece = board.get(source);
        Piece movedPiece = movePiece(source, target, piece);
        PieceInfo pieceInfo = movedPiece.getPieceInfo();
        Position newPosition = pieceInfo.getPosition();

        if (source != newPosition) {
            removedPiece.addPiece(board.get(newPosition));
        }
        board.put(source, new EmptyPiece(new PieceInfo(source, Team.NONE)));
        board.put(newPosition, movedPiece);

        return target == newPosition;
    }

    public boolean isSameTeamFromPosition(Position position, Team team) {
        Piece piece = board.get(position);

        return piece.isSameTeam(team);
    }

    private Piece movePiece(Position source, Position target, Piece piece) {
        return piece.move(target,
                checkObstacleInRange(source, target),
                checkPieceExist(target),
                checkSameTeamPieceExist(piece.getTeam(), target));
    }

    public boolean isKingRemoved() {
        return removedPiece.isRecentlyRemovedPieceType(PieceType.KING);
    }

    boolean checkObstacleInRange(Position currentPosition, Position newPosition) {
        List<Position> internalPositions = currentPosition.getInternalPositions(newPosition);

        return internalPositions.stream()
                .map(board::get)
                .anyMatch(piece -> piece.getType() != PieceType.EMPTY);
    }

    boolean checkPieceExist(Position position) {
        Piece piece = board.get(position);

        return piece.getType() != PieceType.EMPTY;
    }

    boolean checkSameTeamPieceExist(Team currentTeam, Position otherPosition) {
        Piece otherPiece = board.get(otherPosition);

        return otherPiece.isSameTeam(currentTeam);
    }

    void placePiece(Position currentPosition, Piece piece) {
        board.put(currentPosition, piece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
