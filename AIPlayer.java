import java.util.Random;

public class AIPlayer extends Player {
    private final Random random;

    public AIPlayer(String name, char symbol) {
        super(name, symbol);
        this.random = new Random();
    }

    public int getMove(char[][] board, char opponentSymbol) {
        int columns = board[0].length;

        // Try to win
        for (int col = 0; col < columns; col++) {
            if (isValidMove(board, col)) {
                char[][] temp = copyBoard(board);
                makeMove(temp, col, getSymbol());
                if (isWinningMove(temp, getSymbol())) {
                    return col;
                }
            }
        }

        // Try to block opponent
        for (int col = 0; col < columns; col++) {
            if (isValidMove(board, col)) {
                char[][] temp = copyBoard(board);
                makeMove(temp, col, opponentSymbol);
                if (isWinningMove(temp, opponentSymbol)) {
                    return col;
                }
            }
        }

        // Center-most valid
        int center = columns / 2;
        for (int offset = 0; offset < columns; offset++) {
            int left = center - offset;
            int right = center + offset;
            if (left >= 0 && isValidMove(board, left)) return left;
            if (right < columns && isValidMove(board, right)) return right;
        }

        // Fallback random
        int col;
        do {
            col = random.nextInt(columns);
        } while (!isValidMove(board, col));
        return col;
    }

    private boolean isValidMove(char[][] board, int col) {
        return board[0][col] == ' ';
    }

    private void makeMove(char[][] board, int col, char symbol) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == ' ') {
                board[row][col] = symbol;
                break;
            }
        }
    }

    private char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[0].length);
        }
        return copy;
    }

    private boolean isWinningMove(char[][] board, char symbol) {
        int rows = board.length;
        int cols = board[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] != symbol) continue;

                if (c + 3 < cols &&
                    board[r][c + 1] == symbol &&
                    board[r][c + 2] == symbol &&
                    board[r][c + 3] == symbol) return true;

                if (r + 3 < rows &&
                    board[r + 1][c] == symbol &&
                    board[r + 2][c] == symbol &&
                    board[r + 3][c] == symbol) return true;

                if (r + 3 < rows && c + 3 < cols &&
                    board[r + 1][c + 1] == symbol &&
                    board[r + 2][c + 2] == symbol &&
                    board[r + 3][c + 3] == symbol) return true;

                if (r + 3 < rows && c - 3 >= 0 &&
                    board[r + 1][c - 1] == symbol &&
                    board[r + 2][c - 2] == symbol &&
                    board[r + 3][c - 3] == symbol) return true;
            }
        }
        return false;
    }
}
