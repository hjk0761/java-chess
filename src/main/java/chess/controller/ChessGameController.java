package chess.controller;

import static chess.domain.CommandType.END;
import static chess.domain.CommandType.START;

import chess.domain.ChessGame;
import chess.domain.PieceInfo;
import chess.domain.Position;
import chess.domain.dto.BoardDto;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChessGameController {

    public void run() {
        ChessGame chessGame = new ChessGame();

        playChess(chessGame);
    }

    private void playChess(ChessGame chessGame) {
        List<String> commands = InputView.inputCommand();
        String command = commands.get(0);
        while(!command.equals(END.getCommandType())) {
            playOneTurn(chessGame, commands);
            commands = InputView.inputCommand();
            command = commands.get(0);
        }
    }

    private void playOneTurn(ChessGame chessGame, List<String> commands) {
        String command = commands.get(0);
        if (command.equals(START.getCommandType())) {
            playOneTurnForStart(chessGame);
            return;
        }
        playOneTurnForMove(chessGame, commands);
    }

    private void playOneTurnForStart(ChessGame chessGame) {
        OutputView.printBoard(makeBoardDto(chessGame.start()));
    }

    private void playOneTurnForMove(ChessGame chessGame, List<String> commands) {
        Position source = Position.of(commands.get(1));
        Position target = Position.of(commands.get(2));
        OutputView.printBoard(makeBoardDto(chessGame.move(source, target)));
    }

    private BoardDto makeBoardDto(Map<Position, Piece> board) {
        List<List<String>> rawBoard = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            List<String> row = new ArrayList<>(Collections.nCopies(8, "."));
            rawBoard.add(row);
        }

        for (var entrySet : board.entrySet()) {
            Position position = entrySet.getKey();
            Piece piece = entrySet.getValue();
            makeBoardDtoPiece(position, piece, rawBoard);
        }
        return new BoardDto(rawBoard);
    }

    private void makeBoardDtoPiece(Position position, Piece piece, List<List<String>> rawBoard) {
        int realYPosition = position.getY() - 1;
        int realXPosition = position.getX() - 1;
        PieceType pieceType = piece.getType();
        PieceInfo pieceInfo = piece.getPieceInfo();

        rawBoard.get(realYPosition).set(realXPosition, pieceType.getPieceLetter(pieceInfo.getTeam()));
    }
}
