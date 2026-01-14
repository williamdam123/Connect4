import java.util.Random;
import java.util.Scanner;

public class Game {
    private final char[][] board;
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final boolean isAI;

    public Game(Player p1, Player p2, boolean isAI) {
        board = new char[ROWS][COLUMNS];
        player1 = p1;
        player2 = p2;
        currentPlayer = player1;
        this.isAI = isAI;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                board[r][c] = '.';
            }
        }
    }

    public int play(Scanner scanner) {
        boolean gameOver = false;

        while (!gameOver) {
            printBoard();
            int col;

            if (isAI && currentPlayer == player2) {
                col = getAIMove(); // Bot move
                System.out.println(currentPlayer.getName() + " (AI) chooses column: " + col);
            } else {
                System.out.print(currentPlayer.getName() + "'s turn (" + currentPlayer.getSymbol() + "). Choose column (0-6): ");
                try {
                    col = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a number between 0 and 6.");
                    continue;
                }
            }

            if (col < 0 || col >= COLUMNS || !dropPiece(col)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            if (checkWin()) {
                printBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                return currentPlayer == player1 ? 1 : 2;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a tie!");
                return 0;
            } else {
                switchPlayer();
            }
        }

        return 0;
    }

    private boolean dropPiece(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == '.') {
                board[row][col] = currentPlayer.getSymbol();
                return true;
            }
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6");
    }

    private boolean isBoardFull() {
        for (int c = 0; c < COLUMNS; c++) {
            if (board[0][c] == '.') {
                return false;
            }
        }
        return true;
    }

    private boolean checkWin() {
        char symbol = currentPlayer.getSymbol();

        // Horizontal
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= COLUMNS - 4; c++) {
                if (board[r][c] == symbol && board[r][c+1] == symbol &&
                    board[r][c+2] == symbol && board[r][c+3] == symbol) {
                    return true;
                }
            }
        }

        // Vertical
        for (int c = 0; c < COLUMNS; c++) {
            for (int r = 0; r <= ROWS - 4; r++) {
                if (board[r][c] == symbol && board[r+1][c] == symbol &&
                    board[r+2][c] == symbol && board[r+3][c] == symbol) {
                    return true;
                }
            }
        }

        // Diagonal (bottom-left to top-right)
        for (int r = 3; r < ROWS; r++) {
            for (int c = 0; c <= COLUMNS - 4; c++) {
                if (board[r][c] == symbol && board[r-1][c+1] == symbol &&
                    board[r-2][c+2] == symbol && board[r-3][c+3] == symbol) {
                    return true;
                }
            }
        }

        // Diagonal (top-left to bottom-right)
        for (int r = 0; r <= ROWS - 4; r++) {
            for (int c = 0; c <= COLUMNS - 4; c++) {
                if (board[r][c] == symbol && board[r+1][c+1] == symbol &&
                    board[r+2][c+2] == symbol && board[r+3][c+3] == symbol) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getAIMove() {
        Random random = new Random();

        // Try to make a smart move (e.g., pick center if available)
        if (board[0][3] == '.') {
            return 3;
        }

        // Random valid column
        int col;
        do {
            col = random.nextInt(COLUMNS);
        } while (board[0][col] != '.');

        return col;
    }
}
