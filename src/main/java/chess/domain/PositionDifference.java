package chess.domain;

public record PositionDifference(int xDifference, int yDifference) {
    public boolean isMagnitudeEqual() {
        return Math.abs(xDifference) == Math.abs(yDifference);
    }

    public boolean isOnAxis() {
        return xDifference == 0 || yDifference == 0;
    }

    public boolean isWithinUnitRange() {
        if (!(isMagnitudeEqual() || isOnAxis())) {
            return false;
        }
        return xDifference <= 1 && yDifference <= 1;
    }
}
