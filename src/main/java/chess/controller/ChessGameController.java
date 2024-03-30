package chess.controller;

import chess.domain.chessgame.ChessGame;
import chess.domain.chessgame.Command;
import chess.domain.chessgame.ScoreBoard;
import chess.domain.db.ChessBoardDao;
import chess.domain.dto.BoardDto;
import chess.domain.dto.ChessBoardDto;
import chess.domain.pieceInfo.Position;
import chess.domain.pieceInfo.Team;
import chess.view.InputView;
import chess.view.OutputView;

import static chess.domain.chessgame.CommandType.*;

public class ChessGameController {
    private final ChessBoardDao chessBoardDao = new ChessBoardDao();

    public void run() {
        ChessGame chessGame = new ChessGame();

        OutputView.printChessGameStartMessage();
        OutputView.printCommandGuideMessage();

        play(chessGame);
    }

    private void play(final ChessGame chessGame) {
        try {
            playChess(chessGame);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            play(chessGame);
        }
    }

    private void playChess(final ChessGame chessGame) {
        Command command = Command.of(InputView.inputCommand());
        boolean isEnd = playOneTurn(chessGame, command);
        while (!isEnd) {
            command = Command.of(InputView.inputCommand());
            isEnd = playOneTurn(chessGame, command);
        }
    }

    private boolean playOneTurn(final ChessGame chessGame, final Command command) {
        if (command.isCommand(END)) {
            return true;
        }
        if (command.isCommand(MOVE)) {
            return move(chessGame, command);
        }
        return continueCommand(chessGame, command);
    }

    private boolean continueCommand(final ChessGame chessGame, final Command command) {
        if (command.isCommand(START)) {
            start(chessGame);
        }
        if (command.isCommand(STATUS)) {
            status(chessGame);
        }
        if (command.isCommand(SAVE)) {
            save(chessGame);
        }
        return false;
    }

    private void start(final ChessGame chessGame) {
        chessGame.start();
        OutputView.printBoard(BoardDto.of(chessGame.getBoard()));
    }

    private boolean move(final ChessGame chessGame, final Command command) {
        Position source = Position.of(command.getSource());
        Position target = Position.of(command.getTarget());
        chessGame.move(source, target);

        OutputView.printBoard(BoardDto.of(chessGame.getBoard()));
        return isFinished(chessGame);
    }

    private static boolean isFinished(ChessGame chessGame) {
        if (chessGame.isWin()) {
            OutputView.printWinner(chessGame.getTurn());
            return true;
        }
        return false;
    }

    private void status(final ChessGame chessGame) {
        ScoreBoard scoreBoard = chessGame.status();
        Team winner = chessGame.findWinner(scoreBoard);
        result(chessGame, winner, scoreBoard);
    }

    private void result(final ChessGame chessGame, final Team winner, final ScoreBoard scoreBoard) {
        double whiteScore = scoreBoard.getWhiteScore();
        double blackScore = scoreBoard.getBlackScore();
        OutputView.printBoard(BoardDto.of(chessGame.getBoard()));
        if (winner.equals(Team.NONE)) {
            OutputView.printScoreWithDraw(whiteScore, blackScore);
            return;
        }
        OutputView.printScoreWithWinner(whiteScore, blackScore, winner);
    }

    private void save(final ChessGame chessGame) {
        ChessBoardDto chessBoardDto = ChessBoardDto.of(chessGame.getBoard().values().stream().toList());
        chessBoardDto.getPieces().forEach(chessBoardDao::addPiece);
    }
}
