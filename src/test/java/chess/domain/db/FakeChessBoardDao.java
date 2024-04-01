package chess.domain.db;

import chess.domain.pieceInfo.Team;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeChessBoardDao extends ChessBoardDao{
    private final Map<String, List<String>> board = new HashMap<>();
    private Team turn;

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }

    @Override
    public void addPiece(PieceEntity pieceEntity) {
        board.put(pieceEntity.position(), List.of(pieceEntity.team(), pieceEntity.type()));
    }

    @Override
    public PieceEntity findByPosition(String position) {
        if (board.containsKey(position)) {
            return new PieceEntity(position, board.get(position).get(0), board.get(position).get(1));
        }
        return null;
    }

    @Override
    public List<PieceEntity> findAll() {
        List<PieceEntity> all = new ArrayList<>();
        board.forEach((key, value) -> all.add(
                new PieceEntity(key, value.get(0), value.get(1))));
        return all;
    }

    @Override
    public void deleteAll() {
        board.clear();
    }

    @Override
    public void addTurn(Team turn) {
        this.turn = turn;
    }

    @Override
    public Team findTurn() {
        return turn;
    }
}
