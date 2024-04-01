USE chess;

CREATE TABLE chessBoard
(
    position    VARCHAR(5) NOT NULL,
    team        VARCHAR(10) NOT NULL,
    type        VARCHAR(10) NOT NULL
);

CREATE TABLE gameInfo
(
    turn    VARCHAR(5) NOT NULL
);