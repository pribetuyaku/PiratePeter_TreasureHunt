package piratepetertreasurehunt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

import model.Player;
import model.TreasureLocation;

/**
 *
 * @author Priscilla Betuyaku
 */

public final class PiratePeterTreasureHunt {

    int turn;
    static int[][] boardgame;
    static ArrayList<Player> players = null;
    static ArrayList<TreasureLocation> treasureLocations = null;

    static Scanner sc = new Scanner(System.in);

    public void printWelcome() {
        System.out.println("Welcome to the Pirate Peter's Treasure Hunt Game, are you ready for this adventure? \n");
    } // print a welcome message

    public PiratePeterTreasureHunt() {
        turn = 1; // starting the game in this turn

        // load treasure locations
        treasureLocations = Utils.getTreasureLocations();
        setUpBoardGame();
        printWelcome();
        playerData();
        rules();
        printGameBoard();
        playGame();

        sc.close();
    }

    public void setUpBoardGame() {
        int i;
        int j;

        boardgame = new int[10][10]; // standard 10 x 10 board 

        for (i = 0; i < boardgame.length; i++) {
            // rows

            for (j = 0; j < boardgame[i].length; j++) {
                // columns
                boardgame[i][j] = -1;
            }
        }
    }

    public static void playerData() {

        int numberPlayer = 0;
        boolean validPlayers = false;
        int digPoint;

        players = new ArrayList<>();

        while (!validPlayers) { //getting number of players
            System.out.print("How many Pirates are going to play? (2-4): ");
            numberPlayer = sc.nextInt();
            sc.nextLine();
            validPlayers = (numberPlayer >= 2 && numberPlayer <= 4);
        }

        for (int i = 0; i < numberPlayer; i++) {

            Player player = new Player();
            // get name and surname

            String playerName = null;
            do {    //getting the name and surname
                System.out.println("WOW! Enter the " + (i + 1) + "º Pirate name and Surname: ");
                playerName = sc.nextLine();
            } while (playerName.length() == 0);

            String[] nameParts = playerName.split(" "); //validate if there is a space

            player.setNamePlayer(nameParts[0]);
            player.setSurPlayer(nameParts[1]);

            int playerAge = 0;
            do { //getting the age anda check if it is over 12
                System.out.println("Enter your age: ");
                playerAge = sc.nextInt();
            } while (playerAge < 12);

            player.setAgePlayer(playerAge);
            sc.nextLine();

            // Assign Dig Points to the player
            digPoint = utils.Utils.getDigPoints();

            player.setDigPoints(digPoint);

            // calculating the pirate points to subtract
            int piratePointsToSubstract = digPoint * 5;

            // calculating the Pirate Points
            player.setPiratePoints(100 - piratePointsToSubstract);

            // show calculating message
            System.out.println("\n... Calculating DigPoints and PiratePoints...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PiratePeterTreasureHunt.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println();

            players.add(player);

        }

        // show the players information on the screen
        for (int i = 0; i < numberPlayer; i++) {
            System.out.println((i + 1) + "º Pirate: " + players.get(i).getNamePlayer() + " "
                    + players.get(i).getSurPlayer() + " Age: " + players.get(i).getAgePlayer());
            System.out.println("Your DigPoints: " + players.get(i).getDigPoints());
            System.out.println("Your PiratePoints: " + players.get(i).getPiratePoints());
            System.out.println();
        }

    }

    public void rules() { //show the game rules
        System.out.println("RULES: ");
        System.out.println("You have to select a square that has NOT already been used");
        System.out.println("A USED square shows as O on the map and a BLANK square is free and can be chosen.");
    }

    public static void printGameBoard() { //print board game
        System.out.println();
        System.out.println("   A B C D E F G H I J ");

        // -1 is empty, 0 is for 'used cell' and 1 is for 'used cell that had treasure'
        for (int i = 0; i < boardgame.length; i++) {
            if (i < 9) {
                System.out.print("0" + (i + 1) + "|");
            } else {
                System.out.print((i + 1) + "|");
            }

            for (int j = 0; j < boardgame[i].length; j++) {

                if (boardgame[i][j] == -1) {
                    // empty slot
                    System.out.print("_|");

                } else if (boardgame[i][j] == 0) {

                    System.out.print("O|");

                } else if (boardgame[i][j] == 1) {

                    System.out.print("$|");
                }
            }
            System.out.println("");
        }
    }

    public static void playGame() {

        boolean isGameOver = false;

        while (!isGameOver) { 
            for (int i = 0; i < players.size(); i++) {
                System.out.println();
                System.out.println("Argh..Pirate " + players.get(i).getNamePlayer() + " "
                        + players.get(i).getSurPlayer() + "...it be your turn to dig for me treasure.");
                if (players.get(i).getDigPoints() > 0) {
					// validate the input row and column value.
                    // validate if the location is already dug or not
                    int row = -1, column = -1;

                    do {
                        System.out.println("Enter row (1-10):");
                        row = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Enter column (A-J):");
                        String columnLetter = sc.nextLine();
                        column = Utils.getColumnNumber(columnLetter);
                    } while (!Utils.validateRowColumn(boardgame, row, column));

                    // check if the digged location has treasure.
                    boolean isTreasure = false;

                    for (TreasureLocation treasureLocation : treasureLocations) {

                        if (treasureLocation.getI() == row) {
                            if (Utils.getColumnNumber(treasureLocation.getJ()) == column) {
                                isTreasure = true;

                                // set the location as discovered. Will be used in checking game over condition.
                                treasureLocation.setDiscovered(true);
                            }
                        }
                    }

                    if (isTreasure) {
                        System.out.println();
                        System.out.println("Yo-ho-ho and a bottle of rum. I found me some pieces of eight.");

                        // add 20 bonus pirate points
                        players.get(i).setPiratePoints(players.get(i).getPiratePoints() + 20);

                        // set appropriate value in array
                        boardgame[row - 1][column] = 1;

                    } else {
                        System.out.println();
                        System.out.println("Walk the plank! There be no treasure here!");

                        // set appropriate value in array
                        boardgame[row - 1][column] = 0;
                    }

                    // reduce dig point by 1
                    players.get(i).setDigPoints(players.get(i).getDigPoints() - 1);

                    // draw the map again
                    printGameBoard();

                } else {
                    // the player has no more dig points to play
                    System.out.println("Argh, Captain, me shovel has broken!");
                }
            }

			// check if the game is over.
            // We have 2 conditions,
            // 1. if all players run out of dig points.
            int digPointCount = 0;
            for (Player player : players) {

                if (player.getDigPoints() > 0) {
                    digPointCount = digPointCount + 1;
                }
            }

            // 2. if all treasure locations are discovered.
            int discoveredPoints = 0;
            for (TreasureLocation treasureLocation : treasureLocations) {
                if (!treasureLocation.isDiscovered()) {
                    discoveredPoints = discoveredPoints + 1;
                }

            }

            System.out.println(digPointCount);
            System.out.println(discoveredPoints);

            if (digPointCount == 0 || discoveredPoints == 0) {
                isGameOver = true;
            }

        }

        System.out.println("Game Over!!!");
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("Score Board");
        System.out.println("-------------------------------------------");
        System.out.println("Player Name | Pirate Points | Dig Points");

        // sorting players based on pirate points and dig points
        players.sort(Comparator.comparingInt(Player::getPiratePoints).thenComparingInt(Player::getDigPoints));

        for (Player player : players) {
            System.out.println(player.getNamePlayer() + " " + player.getSurPlayer() + " | " + player.getPiratePoints()
                    + " | " + player.getDigPoints());
        }

        System.out.println();
        if (players.get(players.size() - 1).getPiratePoints() == players.get(players.size() - 2).getPiratePoints()) {
            if (players.get(players.size() - 1).getDigPoints() > players.get(players.size() - 2).getDigPoints()) {
                System.out.println("The Winner is : " + players.get(players.size() - 1).getNamePlayer() + " "
                        + players.get(players.size() - 1).getSurPlayer());
            } else if (players.get(players.size() - 2).getDigPoints() > players.get(players.size() - 1)
                    .getDigPoints()) {
                System.out.println("The Winner is : " + players.get(players.size() - 2).getNamePlayer() + " "
                        + players.get(players.size() - 2).getSurPlayer());
            } else if (players.get(players.size() - 2).getDigPoints() == players.get(players.size() - 1)
                    .getDigPoints()) {
                System.out.println("Its a draw between " + players.get(players.size() - 2).getNamePlayer() + " "
                        + players.get(players.size() - 2).getSurPlayer() + " and "
                        + players.get(players.size() - 1).getNamePlayer() + " "
                        + players.get(players.size() - 1).getSurPlayer());
            }
        } else { //print the winner
            System.out.println("The Winner is : " + players.get(players.size() - 1).getNamePlayer() + " "
                    + players.get(players.size() - 1).getSurPlayer());
        }
        sc.close();
    }

    public static void main(String args[]) { //start a new game
        new PiratePeterTreasureHunt();
    }

}
