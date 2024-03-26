package chess.domain.strategy;

import chess.domain.Position;

public class EmptyMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position currentPosition, final Position newPosition) {
        return false;
    }
}
