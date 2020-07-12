package mckeken.io;

import mckeken.color.ColorConsole;

import java.util.Scanner;
import java.util.InputMismatchException;

// A class of static functions designed to stream-line getting typical forms of user input. 
public class LogUtils {
	
	// Gets a yes or no answer from the user
	public static boolean getAffirmative() {
		String userInput = "";
		Scanner scan;
		do {
			scan = new Scanner(System.in);
			userInput = scan.next();

			switch(userInput.toLowerCase()) {
				case "y":
					return true;
				case "n":
					return false;
				case "yes":
					return true;
				case "no":
					return false;
				default:
					System.out.println("Input not recognized. Please enter 'y' or 'n'.");
			}
		} while (true);
	}

	// Get a number from the user
	public static int getNumber() {

		int userInput;
		Scanner scan;

		// Try to get a digit from the user
		do {
			scan = new Scanner(System.in);

			try {
				userInput = scan.nextInt(); // Get the inout
				return userInput;			// If its succesful, return the digit

			} catch (InputMismatchException e) { // Prompt the user to enter the correct input
				System.out.println("Input not recognized. Please enter a digit between 1 and 99,999");
			}
		} while (true);
	}

	// Get a number from the user. Reject if its out of bounds (inclusively)
	public static int getNumber(int lowerBound, int upperBound) {

		int userInput;
		Scanner scan;

		// Try to get a digit from the user
		do {
			scan = new Scanner(System.in);

			try {
				userInput = scan.nextInt(); // Get the inout
				if (userInput >= lowerBound && userInput <= upperBound) {
					return userInput;			// If its succesful, return the digit
				} else {
					System.out.println("That number is out of bounds. Please enter a number between " + lowerBound + " and " + upperBound + ".");
				}

			} catch (InputMismatchException e) { // Prompt the user to enter the correct input
				System.out.println("Input not recognized. Please enter a digit between 1 and 99,999");
			}
		} while (true);
	}

	// Get text from the user
	public static String getText() {
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}

	public static void error(String text) {
		ColorConsole.e(text);
	}
}