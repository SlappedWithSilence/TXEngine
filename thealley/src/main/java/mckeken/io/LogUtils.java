package mckeken.io;

import mckeken.color.ColorConsole;
import mckeken.color.Colors;
import mckeken.main.Manager;

import java.util.Collection;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

// A class of static functions designed to stream-line getting typical forms of user input. 
public class LogUtils {

	public final static int HEADER_LENGTH = 105;
	public final static int HEADER_SPACES = 3;
	
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
		StringBuilder bar = new StringBuilder("|");

		for (int i = 0; i < list.length; i++) {
			String s = "";
			s = s + " [" + i + "]: " + list[i] + " |";
			bar.append(s);
		}

		System.out.println(bar);
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

	public static String verticalTab(Collection<String> strings) {
		String topBar = "-".repeat(HEADER_LENGTH);
		final char SIDE_WALL = '|';

		StringBuilder sb = new StringBuilder();

		sb.append(topBar).append('\n');
		for (String s : strings) sb.append(SIDE_WALL).append(centerString(s, HEADER_LENGTH-2)).append(SIDE_WALL).append('\n');
		sb.append(SIDE_WALL).append("_".repeat(HEADER_LENGTH-2)).append(SIDE_WALL);

		return sb.toString();
	}

	public static String verticalTab(Collection<String> strings, int length) {
		String topBar = "-".repeat(length);
		final char SIDE_WALL = '|';

		StringBuilder sb = new StringBuilder();

		sb.append(topBar).append('\n');
		for (String s : strings) sb.append(SIDE_WALL).append(centerString(s, length-2)).append(SIDE_WALL).append('\n');
		sb.append(SIDE_WALL).append("_".repeat(length-2)).append(SIDE_WALL);

		return sb.toString();
	}


	public static String parallelVerticalTab(ArrayList<String> left, ArrayList<String> right) {
		StringBuilder sb = new StringBuilder();

		String topBar = "-".repeat(HEADER_LENGTH);
		final char SIDE_WALL = '|';

		int mode = 0;

		if (left.size() == right.size()) mode = 0; // The tab lengths are the same
		if (left.size() > right.size())  mode = 1; // left tab is longer
		if (left.size() < right.size())  mode = 2; // right tab is longer

		if (mode == 1) { // Normalize the tab sizes by inserting empty lines into the right tab
			for (int i = 0; i < left.size() - right.size(); i++) right.add("");
		}
		if (mode == 2) { // Normalize the tab sizes by inserting empty lines into the left tab
			for (int i = 0; i < right.size() - left.size(); i++) right.add("");
		}

		sb.append(topBar).append('\n');

		int leftSpaceOffset = 0;

		for (int i = 0; i < right.size(); i++) {
			sb.append(SIDE_WALL).append(centerString(right.get(i), (HEADER_LENGTH-2)/2)).append(SIDE_WALL);

			if (HEADER_LENGTH % 2 != 0) {
				sb.append(" ");
				leftSpaceOffset = 2;
			}

			sb.append(SIDE_WALL).append(centerString(left.get(i), (HEADER_LENGTH-2)/2 - leftSpaceOffset)).append(SIDE_WALL);
			sb.append('\n');
		}

		sb.append(SIDE_WALL).append("_".repeat(HEADER_LENGTH-2)).append(SIDE_WALL).append('\n');
		return sb.toString();
	}

	public static void verticalTabList(ArrayList<ArrayList<String>> strings) {
		for (ArrayList<String> arr : strings) {
			System.out.println(verticalTab(arr));
		}
	}

	public static void parallelVerticalTabList(ArrayList<ArrayList<String>> left, ArrayList<ArrayList<String>> right, int length) {
		int mode = 0;

		if (left.size() == right.size()) mode = 0; // The tab lengths are the same
		if (left.size() > right.size())  mode = 1; // left tab is longer
		if (left.size() < right.size())  mode = 2; // right tab is longer

		if (mode == 1) { // Normalize the tab sizes by inserting empty lines into the right tab
			for (int i = 0; i < left.size() - right.size(); i++) right.add(new ArrayList<String>());
		}
		if (mode == 2) { // Normalize the tab sizes by inserting empty lines into the left tab
			for (int i = 0; i < right.size() - left.size(); i++) right.add(new ArrayList<String>());
		}
	}

	public static void numberedBar(String[] list, int length, char sep) {
		StringBuilder bar = new StringBuilder("|");

		for (int i = 0; i < list.length; i++) {
			String s = "";
			s = s + " [" + i + "]: " + list[i] + " |";
			bar.append(s);
		}

		StringBuilder spacer = new StringBuilder();
		for (int i = 0; i < (length-bar.length())/2; i++) {
			spacer.append(sep);
		}

		bar = new StringBuilder(spacer.toString() + bar + spacer);

		System.out.println(bar);
	}

	public static void numberedBar(ArrayList<String> list, int length, char sep) {
		StringBuilder bar = new StringBuilder("|");

		for (int i = 0; i < list.size(); i++) {
			String s = "";
			s = s + " [" + i + "]: " + list.get(i) + " |";
			bar.append(s);
		}

		StringBuilder spacer = new StringBuilder();
		for (int i = 0; i < (length-bar.length())/2; i++) {
			spacer.append(sep);
		}

		bar = new StringBuilder(spacer.toString() + bar + spacer);

		System.out.println(bar);
	}

	public static void numberedBar(ArrayList<String> list) {
		numberedBar(list, HEADER_LENGTH, ' ');
	}

	public static void header(String title) {
		header(title, HEADER_LENGTH, '-', Colors.WHITE);
	}

	public static void bar() {
		bar('-', HEADER_LENGTH);
	}

	public static void bar (char sep, int length) {
		String bar = ""+sep;
		System.out.println(bar.repeat(length));
	}

	public static void header(String title, int length, char sep, String titleColor) {

		if (title.length() >= length +2) length +=2;

		StringBuilder headerBar = new StringBuilder();
		headerBar.append(String.valueOf(sep).repeat(Math.max(0, length)));

		final String repeat = String.valueOf(sep).repeat(Math.max(0, (length - title.length()) / 2 - HEADER_SPACES));
		String titleBar = repeat +
				" ".repeat(HEADER_SPACES) +
				title +
				" ".repeat(HEADER_SPACES) +
				repeat;

		String headerBuilder = titleColor + headerBar +
				'\n' +
				titleBar +
				'\n' +
				headerBar +
				Colors.RESET;

		System.out.println(headerBuilder);

	}

	public static void subHeader(Collection<String> strings) {
		subHeader(strings, HEADER_LENGTH);
	}

	public static void subHeader(Collection<String> strings, int length) {
		StringBuilder line1 = new StringBuilder();

		line1.append(" ").append("_".repeat(length-2));
		line1.append("\n|").append(" ".repeat(length-2)).append("|"); // Long bar top

		for (String s : strings) {
			line1.append("\n| ");
			line1.append(s);
			line1.append(" ".repeat(length - s.length() - 3));
			line1.append('|');
		}

		line1.append("\n|").append("_".repeat(LogUtils.HEADER_LENGTH-2)).append("|"); // Long bar bottom

		System.out.println(line1);
	}

}