package org.game;

import java.util.Scanner;

public class Game {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Board player1 = new Board();
        Board player2 = new Board();

        boardInit(scanner, player1, player2); // Create and place ships
        gameInit(scanner, player1, player2); // Start the game
    }

    public void boardInit(Scanner scanner, Board player1, Board player2) {
        System.out.println("Player 1, place your ships on the game field");
        player1.init(scanner);

        passTurn(scanner);

        System.out.println("Player 2, place your ships to the game field");
        player2.init(scanner);

        passTurn(scanner);

        player1.setShipsCoordinates(player2.getShipsCoordinates());
        player2.setShipsCoordinates(player1.getShipsCoordinates());
    }

    public void gameInit(Scanner scanner, Board player1, Board player2) {
        while (player1.dead() || player2.dead()) {
            playerTurn(scanner, player1, player2, "Player 1");
            playerTurn(scanner, player2, player1, "Player 2");
        }
    }

    public void passTurn(Scanner scanner) {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

    public void playerTurn(Scanner scanner, Board player, Board enemy, String name) {
        player.printGame();
        System.out.println(name + ", it's your turn:");
        System.out.print("> ");
        String input = scanner.nextLine();
        player.turn(scanner, input, enemy.getBoard());

        passTurn(scanner);
    }
}
