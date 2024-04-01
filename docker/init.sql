USE chess;

CREATE TABLE chessBoard
(
    position    VARCHAR(5) NOT NULL,
    team        VARCHAR(10) NOT NULL,
    type        VARCHAR(10) NOT NULL,
    PRIMARY KEY (position)
);

CREATE TABLE gameInfo
(
    game_id INT NOT NULL,
    turn    VARCHAR(5) NOT NULL,
    PRIMARY KEY (game_id)
);