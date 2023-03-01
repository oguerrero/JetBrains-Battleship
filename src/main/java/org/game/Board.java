package org.game;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Board {
    private final String[][] board;
    private final String[][] fogBoard;
    private final LinkedHashMap<String, Integer> ships;
    private final LinkedHashMap<String, String[]> shipsCoordinates;
    private final LinkedHashMap<String, String> shots;
    private final Utils utils = new Utils();
    private final Ships shipsGenerator = new Ships();
    private final Shots shotsGenerator = new Shots();
    private LinkedHashMap<String, String[]> enemyCoordinates;

    public Board() {
        this.board = generateBoard();
        this.fogBoard = generateBoard();
        this.ships = new LinkedHashMap<>();
        this.shipsCoordinates = new LinkedHashMap<>();
        this.shots = new LinkedHashMap<>();
        this.enemyCoordinates = new LinkedHashMap<>();
    }

    public void init(Scanner scanner) {
        utils.printBoard(board);
        shipsGenerator.generateShips(scanner, board, ships, shipsCoordinates);
    }

    public void turn(Scanner scanner, String input, String[][] enemyBoard) {
        while (shotsGenerator.shotInBoard(input)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            input = scanner.nextLine();
        }

        if (shots.containsKey(input)) {
            if (shots.get(input).equals("hit")) System.out.println("You hit a ship!");
            else if (shots.get(input).equals("miss")) System.out.println("You missed!");
        } else {
            String result = shotsGenerator.validShot(enemyBoard, fogBoard, input, enemyCoordinates, shots);
            System.out.println("result: " + result);
            System.out.println(result);
        }

        if (enemyCoordinates.size() == 0) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        }
    }

    public String[][] generateBoard() {
        String[][] board = new String[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = "~";
            }
        }

        return board;
    }

    public void printGame() {
        utils.printBoard(fogBoard);
        System.out.println("---------------------");
        utils.printBoard(board);
    }

    public boolean dead() {
        return shipsCoordinates.size() > 0;
    }

    public LinkedHashMap<String, String[]> getShipsCoordinates() {
        return shipsCoordinates;
    }

    public void setShipsCoordinates(LinkedHashMap<String, String[]> shipsCoordinates) {
        this.enemyCoordinates = shipsCoordinates;
    }

    public String[][] getBoard() {
        return board;
    }
}
