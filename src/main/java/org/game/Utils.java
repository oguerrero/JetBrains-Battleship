package org.game;

public class Utils {
    public void printBoard(String[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0; row < 10; row++) {
            System.out.print((char) (row + 65) + " ");
            for (int col = 0; col < 10; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}
