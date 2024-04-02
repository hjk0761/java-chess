package chess.domain.db;

import chess.domain.pieceInfo.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("DB 연결 오류:" + e.getMessage());
        }
    }

    public void addPiece(final PieceEntity pieceEntity) {
        final String query = "INSERT INTO chess_board VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE team = ?, type = ?";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceEntity.position());
            preparedStatement.setString(2, pieceEntity.team());
            preparedStatement.setString(3, pieceEntity.type());
            preparedStatement.setString(4, pieceEntity.team());
            preparedStatement.setString(5, pieceEntity.type());
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceEntity findByPosition(final String position) {
        final String query = "SELECT * FROM chess_board WHERE position = ?";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PieceEntity(
                        resultSet.getString("position"),
                        resultSet.getString("team"),
                        resultSet.getString("type")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<PieceEntity> findAll() {
        final String query = "SELECT * FROM chess_board";
        final List<PieceEntity> result = new ArrayList<>();
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new PieceEntity(
                        resultSet.getString("position"),
                        resultSet.getString("team"),
                        resultSet.getString("type")
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void deleteAll() {
        final String query = "DELETE FROM chess_board";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTurn(final Team turn) {
        final String query = "INSERT INTO game_infos VALUES(?, ?) ON DUPLICATE KEY UPDATE turn = ?";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, turn.name());
            preparedStatement.setString(3, turn.name());
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team findTurn() {
        final String query = "SELECT turn FROM game_infos WHERE game_id = 1";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("turn"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
