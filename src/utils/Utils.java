package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.TreasureLocation;

/**
 *
 * @author Priscilla Betuyaku
 */
public class Utils {

	public static ArrayList<TreasureLocation> getTreasureLocations() { // create an arraylist to store the values of  the treasure location
		ArrayList<TreasureLocation> treasureLocations = new ArrayList<>();

		File resourceFile = new File("./PiratePete.txt"); // create a file to read  the Treasure Map data

		Scanner sc;
		try {
			sc = new Scanner(resourceFile);

			while (sc.hasNextLine()) { //check the scanner has next value
				TreasureLocation treasureLocation = new TreasureLocation(); //create a new file to store the new location and split the data with comma

				String[] splits = sc.nextLine().split(",");

				treasureLocation.setI(Integer.parseInt(splits[0]));
				treasureLocation.setJ(splits[1]);

				treasureLocations.add(treasureLocation);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return treasureLocations; //return the treasure location
	}

	public static int getDigPoints() { // calculate the digpoints randon number between 4 and 7

		Random digran = new Random();
		int minimum = 4;
		int maximum = 7;

		return minimum + digran.nextInt(maximum - minimum + 1);
	}

	public static void main(String[] args) { //method to print the digpoints after calculate it
		System.out.println("Digpoints: " + getDigPoints()); 
	}

	public static int getColumnNumber(String column) { //method to read a letter and convert into a number
		int playerColumn = -1;

		if (column.equalsIgnoreCase("A")) {
			playerColumn = 0;
		} else if (column.equalsIgnoreCase("B")) {
			playerColumn = 1;
		} else if (column.equalsIgnoreCase("C")) {
			playerColumn = 2;
		} else if (column.equalsIgnoreCase("D")) {
			playerColumn = 3;
		} else if (column.equalsIgnoreCase("E")) {
			playerColumn = 4;
		} else if (column.equalsIgnoreCase("F")) {
			playerColumn = 5;
		} else if (column.equalsIgnoreCase("G")) {
			playerColumn = 6;
		} else if (column.equalsIgnoreCase("H")) {
			playerColumn = 7;
		} else if (column.equalsIgnoreCase("I")) {
			playerColumn = 8;
		} else if (column.equalsIgnoreCase("J")) {
			playerColumn = 9;
		}

		return playerColumn; //return the item chosen on the map
	}

	public static boolean validateRowColumn(int[][] boardgame, int row, int column) { //method to validate the columns
		if ((row >= 1 && row <= 10)) {
			if (column >= 0 && column <= 9) {
				if (boardgame[row-1][column] == -1) { //column already chosen
					return true;
				} else {
					System.out.println("The location has been digged already. Choose another location.");
					return false;
				}
			} else {
				System.out.println("Please enter valid value for column"); //invalid input for column, ask to another value
				return false;
			}

		} else {
			System.out.println("Please enter valid value for row"); //invalid input for row, ask to another value
			return false;
		}
	}

}
