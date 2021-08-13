package mckeken.io;

import mckeken.color.ColorConsole;
import mckeken.color.Colors;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

// A class of static functions designed to stream-line getting typical forms of user input. 
public class LogUtils {

	public final static int HEADER_LENGTH = 55;
	public final static int HEADER_SPACES = 3;
	
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
		ColorConsole.e(text, false);
	}

	public static void numberedList(ArrayList<String> list) {
		System.out.println("------------------------------------------------");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("[" + i + "] " + list.get(i));
		}
		System.out.println("------------------------------------------------");
	}

	public static void numberedList(String[] list) {
		System.out.println("------------------------------------------------");
		for (int i = 0; i < list.length; i++) {
			System.out.println("[" + i + "] " + list[i]);
		}
		System.out.println("------------------------------------------------");
	}
	// Format: | [0]: Done | [1]: Next | [2]: Back |
	/*public static void numberedBar(ArrayList<String> list) {
		String bar = "|";

		for (int i = 0; i < list.size(); i++) {
			String s = "";
			s = s + " [" + i + "]: " + list.get(i) + " |";
			bar = bar + s;
		}

		System.out.println(bar);
	}*/

	// Format: | [0]: Done | [1]: Next | [2]: Back |
	public static void numberedBar(String[] list) {
		String bar = "|";

		for (int i = 0; i < list.length; i++) {
			String s = "";
			s = s + " [" + i + "]: " + list[i] + " |";
			bar = bar + s;
		}

		System.out.println(bar);
	}

	public static void numberedBar(String[] list, int length, char sep) {
		String bar = "|";

		for (int i = 0; i < list.length; i++) {
			String s = "";
			s = s + " [" + i + "]: " + list[i] + " |";
			bar = bar + s;
		}

		String spacer = "";
		for (int i = 0; i < (length-bar.length())/2; i++) {
			spacer += sep;
		}

		bar = spacer + bar + spacer;

		System.out.println(bar);
	}

	public static void numberedBar(ArrayList<String> list, int length, char sep) {
		String bar = "|";

		for (int i = 0; i < list.size(); i++) {
			String s = "";
			s = s + " [" + i + "]: " + list.get(i) + " |";
			bar = bar + s;
		}

		String spacer = "";
		for (int i = 0; i < (length-bar.length())/2; i++) {
			spacer += sep;
		}

		bar = spacer + bar + spacer;

		System.out.println(bar);
	}

	public static void numberedBar(ArrayList<String> list) {
		numberedBar(list, HEADER_LENGTH, ' ');
	}

	public static void header(String title) {
		header(title, HEADER_LENGTH, '-', Colors.WHITE);
	}

	public static void header(String title, int length, char sep, String titleColor) {

		if (title.length() >= length +2) length +=2;

		StringBuilder headerBar = new StringBuilder();
		for (int i = 0; i < length; i++) {
			headerBar.append(sep);
		}

		StringBuilder titleBar = new StringBuilder();
		for (int i = 0; i< (length - title.length())/2 - HEADER_SPACES; i++) titleBar.append(sep);
		for (int i = 0; i < HEADER_SPACES; i++) titleBar.append(' ');
		titleBar.append(title);
		for (int i = 0; i < HEADER_SPACES; i++) titleBar.append(' ');
		for (int i = 0; i< (length - title.length())/2 - HEADER_SPACES; i++) titleBar.append(sep);

		String headerBuilder = titleColor + headerBar +
				'\n' +
				titleBar +
				'\n' +
				headerBar +
				'\n' +
				Colors.RESET;

		System.out.println(headerBuilder);

	}

}