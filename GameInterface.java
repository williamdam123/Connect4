public interface GameInterface {
    void printBoard();
    boolean placeMark(int row, int col);
    boolean checkWin();
    boolean isBoardFull();
    void switchPlayer();
    char getCurrentPlayer();
}
