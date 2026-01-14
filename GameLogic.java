public class GameLogic implements GameInterface {
    private char[][] board;
    private char currentPlayer;

    public GameLogic() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '-';
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
    }

    public boolean placeMark(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        return (checkRows() || checkCols() || checkDiagonals());
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++)
            if (board[i][0] == currentPlayer &&
                board[i][1] == currentPlayer &&
                board[i][2] == currentPlayer)
                return true;
        return false;
    }

    private boolean checkCols() {
        for (int i = 0; i < 3; i++)
            if (board[0][i] == currentPlayer &&
                board[1][i] == currentPlayer &&
                board[2][i] == currentPlayer)
                return true;
        return false;
    }

    private boolean checkDiagonals() {
        return ((board[0][0] == currentPlayer &&
                 board[1][1] == currentPlayer &&
                 board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer &&
                 board[1][1] == currentPlayer &&
                 board[2][0] == currentPlayer));
    }

    public boolean isBoardFull() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == '-')
                    return false;
        return true;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
}
