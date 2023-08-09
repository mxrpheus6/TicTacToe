import java.util.Scanner;

public class Console {

    private Scanner sc = new Scanner(System.in);
    private Board board;



    Console() {
        board = new Board();
    }

    private void play() {
        System.out.println("Starting a new game...");

        while (true) {
            printGameStatus();
            makeMove();

            if (board.isGameOver()) {
                printWinner();

                if (!tryAgain()) {
                    break;
                }
            }
        }

    }

    private void makeMove() {
        System.out.print("Your move index:");

        int index = sc.nextInt() - 1;

        if (index < 0 || index >= Board.BOARD_LENGTH * Board.BOARD_LENGTH) {
            System.out.println("Invalid index");
            System.out.println("Value must be between 1 and 9");
            return;
        } else if (!board.move(index)) {
            System.out.println("Invalid position");
            System.out.println("This position is already played");
        }

    }

    private void printGameStatus() {
        Board.State[][] tempBoard = board.getBoard();
        for (int row = 0; row < Board.BOARD_LENGTH; row++) {
            for (int col = 0; col < Board.BOARD_LENGTH; col++) {
                if (tempBoard[row][col] == Board.State.Empty) {
                    System.out.print('-');
                } else {
                    System.out.print(tempBoard[row][col]);
                }
            }
            System.out.println();
        }
    }

    private void printWinner() {
        Board.State winner = board.getWinner();

        printGameStatus();

        if (winner == Board.State.Empty) {
            System.out.println("Draw!");
        } else {
            System.out.println("The winner is " + winner + '!');
        }

    }

    private boolean tryAgain() {
        if (promptTryAgain()) {
            board.reset();
            System.out.println("It's X's turn");
            System.out.println("The game started");
            return true;
        }
        return false;
    }

    private boolean promptTryAgain() {
        while (true) {
            System.out.println("Would you like to start a new game? (y/n):");
            String choice = sc.next();
            if (choice.equals("y")) {
                return true;
            } else if (choice.equals("n")) {
                return false;
            }
            System.out.println("Invalid input");
        }
    }

    public static void main(String[] args) {
        Console ticTacToe = new Console();
        ticTacToe.play();
    }
}