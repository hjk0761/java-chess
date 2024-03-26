package chess.domain.strategy;

import chess.domain.Position;
import chess.domain.PositionDifference;

public class BlackPawnFirstMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position currentPosition, final Position newPosition) {
        PositionDifference positionDifference = currentPosition.calculateDifference(newPosition);

        boolean canMoveStraight = positionDifference.isWithinVerticalRange(1, 2);
        boolean canMoveDiagonal =
                positionDifference.isWithinVerticalRange(1, 1) && positionDifference.isMagnitudeEqual();

        return canMoveStraight || canMoveDiagonal;
    }
}
