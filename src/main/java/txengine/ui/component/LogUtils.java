package txengine.ui.component;

import txengine.ui.color.ColorConsole;
import txengine.ui.color.Colors;

import java.util.*;

// A class of static functions designed to stream-line getting typical forms of user input. 
public class LogUtils {



	public final static long READING_DELAY_SECONDS = 1;

	// Delay execution for 'n' seconds. This is intended to be used to let the user read new information as it is printed
	public static void readingDelay(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Call readingDelay using a default value as the delay
	public static void readingDelay() {
		readingDelay(READING_DELAY_SECONDS);
	}

	//Wait for the user to press any key
	public static void getAnyKey() {
		System.out.println("Press the enter key to continue...");
		new Scanner(System.in).nextLine();
	}
	
	// Gets a yes or no answer from the user
	public static boolean getAffirmative() {
		String userInput;
		Scanner scan;
		do {
			scan = new Scanner(System.in);
			userInput = scan.next();

			switch(userInput.toLowerCase()) {
				case "y":
				case "yes":
					return true;
				case "n":
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
				return userInput;			// If it's successful, return the digit

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
		ColorConsole.e(text, false);
	}

	public static String centerString(String s, int length) {
		if (s.length() >= length) return s;

		String spacer = " ".repeat((length-s.length())/2);

		if (s.length()%2==0 && length%2==0) return spacer + s + spacer; // Case: even string, even length
		if (s.length()%2!=0 && length%2!=0) return spacer + s + spacer; // Case: odd string, odd length
		if (s.length()%2==0 && length%2!=0) return spacer + ' ' + s + spacer; // Case: even string, odd length
		if (s.length()%2!=0 && length%2==0) return spacer + ' ' + s + spacer; // Case: odd string, even length
		return spacer + s + spacer;											  // Default
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}



}