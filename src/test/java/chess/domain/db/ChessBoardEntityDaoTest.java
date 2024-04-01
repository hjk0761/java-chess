package chess.domain.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChessBoardEntityDaoTest {

    private final FakeChessBoardDao chessBoardDao = new FakeChessBoardDao();

    @BeforeEach
    @Test
    public void addPiece() {
        final var piece = new ChessBoardEntity("a1", "WHITE", "KING");
        chessBoardDao.addPiece(piece);
    }

    @Test
    public void connection() {
        try (final var connection = chessBoardDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByPosition() {
        final var piece = chessBoardDao.findByPosition("a1");

        assertThat(piece).isEqualTo(new ChessBoardEntity("a1", "WHITE", "KING"));
    }

    @Test
    public void findAll() {
        final var piece = chessBoardDao.findAll();

        assertThat(piece).isEqualTo(List.of(new ChessBoardEntity("a1", "WHITE", "KING")));
    }

    @Test
    @AfterEach
    public void deleteAll() {
        chessBoardDao.deleteAll();

        assertThat(chessBoardDao.findByPosition("a1")).isNull();
    }
}
