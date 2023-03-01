package org.game;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Shots {
    public boolean shotInBoard(String input) {
        String[] parts = input.split("");
        String[] tempCol = Arrays.copyOfRange(parts, 1, parts.length);
        int row = parts[0].charAt(0) - 65;
        int col = Integer.parseInt(String.join("", tempCol)) - 1;

        return row < 0 || row > 9 || col < 0 || col > 9;
    }

    public String validShot(String[][] board, String[][] fogBoard, String input, LinkedHashMap<String, String[]> shipsCoordinates, LinkedHashMap<String, String> shots) {
        String[] parts = input.split("");
        String[] tempCol = Arrays.copyOfRange(parts, 1, parts.length);
        int row = parts[0].charAt(0) - 65;
        int col = Integer.parseInt(String.join("", tempCol)) - 1;
        if (board[row][col].equals("O")) {
            board[row][col] = "X";
            fogBoard[row][col] = "X";

            for (String ship : shipsCoordinates.keySet()) {
                String[] shipParts = shipsCoordinates.get(ship);
                if (Arrays.asList(shipParts).contains(input)) {
                    shots.put(input, "hit");
                    if (Arrays.stream(shipParts).allMatch(shots::containsKey)) {
                        shipsCoordinates.remove(ship);
                        return ("You sank a ship! Specify a new target:");
                    }
                    return ("You hit a ship!");
                }
            }
        } else {
            board[row][col] = "M";
            fogBoard[row][col] = "M";
            shots.put(input, "miss");
            return ("You missed!");
        }

        return "";
    }
}
