package chess.controller;

import chess.domain.ChessGame;
import chess.domain.Command;
import chess.domain.Position;
import chess.domain.Team;
import chess.domain.dto.BoardDto;
import chess.view.InputView;
import chess.view.OutputView;

import static chess.domain.CommandType.*;

public class ChessGameController {

    public void run() {
        ChessGame chessGame = new ChessGame();

        OutputView.printChessGameStartMessage();
        OutputView.printCommandGuideMessage();

        play(chessGame);
    }

    private void play(ChessGame chessGame) {
        try {
            playChess(chessGame);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            play(chessGame);
        }
    }

    private void playChess(ChessGame chessGame) {
        Command command = Command.of(InputView.inputCommand());
        while(!command.isCommand(END)) {
            boolean isEnd = playOneTurn(chessGame, command);
            if (isEnd) {
                break;
            }
            command = Command.of(InputView.inputCommand());
        }
    }

    private boolean playOneTurn(ChessGame chessGame, Command command) {
        if (command.isCommand(MOVE)) {
            return move(chessGame, command);
        }
        if (command.isCommand(START)) {
            start(chessGame);
        }
        return false;
    }

    private void start(ChessGame chessGame) {
        chessGame.start();
        OutputView.printBoard(BoardDto.of(chessGame.getBoard()));
    }

    private boolean move(ChessGame chessGame, Command command) {
        Position source = Position.of(command.getSource());
        Position target = Position.of(command.getTarget());
        Team turn = chessGame.getTurn();
        chessGame.move(source, target);
        OutputView.printBoard(BoardDto.of(chessGame.getBoard()));
        if (chessGame.isWin()) {
            OutputView.printWinner(turn);
            return true;
        }
        return false;
    }
}
