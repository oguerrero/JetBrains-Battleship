package org.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Ships {
    Utils utils = new Utils();
    public ArrayList<String> placeShip(String[][] board, ArrayList<Integer> coordinates) {
        int row1 = coordinates.get(0);
        int col1 = coordinates.get(1);
        int row2 = coordinates.get(2);
        int col2 = coordinates.get(3);
        ArrayList<String> shipParts = new ArrayList<>();

        if (row1 == row2) {
            for (int col = col1; col <= col2; col++) {
                shipParts.add((char) (row1 + 65) + Integer.toString(col + 1));
                board[row1][col] = "O";
            }
            for (int col = col2; col <= col1; col++) {
                shipParts.add((char) (row1 + 65) + Integer.toString(col + 1));
                board[row1][col] = "O";
            }
        } else if (col1 == col2) {
            for (int row = row1; row <= row2; row++) {
                shipParts.add((char) (row + 65) + Integer.toString(col1 + 1));
                board[row][col1] = "O";
            }
            for (int row = row2; row <= row1; row++) {
                shipParts.add((char) (row + 65) + Integer.toString(col1 + 1));
                board[row][col1] = "O";
            }
        }

        return shipParts;
    }

    public void generateShips(Scanner scanner, String[][] board, LinkedHashMap<String, Integer> ships, LinkedHashMap<String, String[]> shipsCoordinates) {
        ships.put("Aircraft Carrier", 5);
        ships.put("Battleship", 4);
        ships.put("Submarine", 3);
        ships.put("Cruiser", 3);
        ships.put("Destroyer", 2);

        for (String ship : ships.keySet()) {
            System.out.println("Enter the coordinates of the " + ship + " (" + ships.get(ship) + " cells):");
            System.out.print("> ");
            String input = scanner.nextLine();

            ArrayList<Integer> coordinates = transformCoordinates(input);
            int shipLength = ships.get(ship);

            while (checkCoordinates(coordinates, shipLength) || checkCloseShip(coordinates, board)) {
                if (checkCoordinates(coordinates, shipLength))
                    System.out.println("Error! Wrong length of the " + ship + "! Try again:");
                else if (checkCloseShip(coordinates, board))
                    System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.print("> ");
                input = scanner.nextLine();
                coordinates = transformCoordinates(input);
            }

            ArrayList<String> shipParts = placeShip(board, coordinates);
            shipsCoordinates.put(ship, shipParts.toArray(new String[0]));
            utils.printBoard(board);
        }
    }

    public static ArrayList<Integer> transformCoordinates(String coordinates) {
        String[] parts = coordinates.split(" ");
        String[] first = parts[0].split("");
        String[] second = parts[1].split("");

        int row1 = first[0].charAt(0) - 65;
        int row2 = second[0].charAt(0) - 65;
        String[] tempCol1 = Arrays.copyOfRange(first, 1, first.length);
        String[] tempCol2 = Arrays.copyOfRange(second, 1, second.length);
        int col1 = Integer.parseInt(String.join("", tempCol1)) - 1;
        int col2 = Integer.parseInt(String.join("", tempCol2)) - 1;

        ArrayList<Integer> result = new ArrayList<>();
        result.add(row1);
        result.add(col1);
        result.add(row2);
        result.add(col2);

        return result;
    }

    private boolean checkCoordinates(ArrayList<Integer> coordinates, int shipLength) {
        int row1 = coordinates.get(0);
        int col1 = coordinates.get(1);
        int row2 = coordinates.get(2);
        int col2 = coordinates.get(3);

        if (row1 == row2) {
            return Math.abs(col1 - col2) + 1 != shipLength;
        } else if (col1 == col2) {
            return Math.abs(row1 - row2) + 1 != shipLength;
        } else {
            return true;
        }
    }

    private boolean checkCloseShip(ArrayList<Integer> coordinates, String[][] board) {
        int row1 = coordinates.get(0);
        int col1 = coordinates.get(1);
        int row2 = coordinates.get(2);
        int col2 = coordinates.get(3);

        // Check if there is another ship in a range of 1 cell in all directions
        for (int col = col1 - 1; col <= col2 + 1; col++) {
            if (col >= 0 && col < 10) {
                if (row1 - 1 >= 0 && board[row1 - 1][col].equals("O")) {
                    return true;
                }
                if (board[row1][col].equals("O")) {
                    return true;
                }
                if (row1 + 1 < 10 && board[row1 + 1][col].equals("O")) {
                    return true;
                }
            }
        }
        for (int row = row1 - 1; row <= row2 + 1; row++) {
            if (row >= 0 && row < 10) {
                if (col1 - 1 >= 0 && board[row][col1 - 1].equals("O")) {
                    return true;
                }
                if (board[row][col1].equals("O")) {
                    return true;
                }
                if (col1 + 1 < 10 && board[row][col1 + 1].equals("O")) {
                    return true;
                }
            }
        }
        return false;
    }
}
