import java.util.HashSet;

public class Board {

    final static int BOARD_LENGTH = 3;

    public enum State {X, O, Empty};
    private State[][] board;
    private State playerTurn;
    private State winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;

    Board() {
        board = new State[BOARD_LENGTH][BOARD_LENGTH];
        movesAvailable = new HashSet<>();
        reset();
    }

    void initialize() {
        for (int row = 0; row < BOARD_LENGTH; row++) {
            for (int col = 0; col < BOARD_LENGTH; col++) {
                board[row][col] = State.Empty;
            }
        }

        movesAvailable.clear();

        for (int i = 0; i <  BOARD_LENGTH * BOARD_LENGTH; i++) {
            movesAvailable.add(i);
        }
    }

    void reset() {
        playerTurn = State.X;
        winner = State.Empty;
        moveCount = 0;
        gameOver = false;
        initialize();
    }

    public boolean move(int index) {
        return move(index / 3, index % BOARD_LENGTH);
    }

    public boolean move (int x, int y) {
        if (gameOver) {
            throw new IllegalStateException("Game over! There is no other moves.");
        }

        if (board[x][y] == State.Empty) {
            board[x][y] = playerTurn;
        } else {
            return false;
        }

        checkRow(x);
        checkCol(y);
        checkDiagFromTopRight();
        checkDiagFromTopLeft();

        playerTurn = (playerTurn == State.X) ? State.O : State.X;
        return true;
    }

    public State[][] getBoard() {
        return board;
    }

    public State getPlayerTurn() {
        return playerTurn;
    }

    public State getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("Game is not over yet!");
        }
        return winner;
    }

    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    void checkRow(int row) {
        for (int i = 1; i < BOARD_LENGTH; i++) {
            if (board[row][i] != board[row][i - 1]) {
                break;
            }
            if (i == BOARD_LENGTH - 1) {
                gameOver = true;
                winner = playerTurn;
            }
        }
    }

    void checkCol(int col) {
        for (int i = 1; i < BOARD_LENGTH; i++) {
            if (board[i][col] != board[i - 1][col]) {
                break;
            }
            if (i == BOARD_LENGTH - 1) {
                gameOver = true;
                winner = playerTurn;
            }
        }
    }

    void checkDiagFromTopRight() {
        for (int i = 1; i < BOARD_LENGTH; i++) {
            if (board[BOARD_LENGTH - i][i - 1] != board[BOARD_LENGTH - i - 1][i])  {
                break;
            }
            if (board[BOARD_LENGTH - i][i - 1] == State.Empty || board[BOARD_LENGTH - i - 1][i] == State.Empty) {
                break;
            }
            if (i == BOARD_LENGTH - 1) {
                gameOver = true;
                winner = playerTurn;
            }
        }
    }

    void checkDiagFromTopLeft() {
        for (int i = 1; i < BOARD_LENGTH; i++) {
            if (board[i][i] != board[i - 1][i - 1]) {
                break;
            }
            if (board[i][i] == State.Empty || board[i - 1][i - 1] == State.Empty) {
                break;
            }
            if (i == BOARD_LENGTH - 1) {
                gameOver = true;
                winner = playerTurn;
            }
        }
    }
}