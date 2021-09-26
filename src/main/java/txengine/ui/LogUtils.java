package txengine.ui;

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

	/*** User Input Methods ***/

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

	// Get the user input
	public static String getEnumValue(Class<?> anEnum) {
		List<String> valuesAsStrings = Arrays.stream(anEnum.getEnumConstants()).map(Object::toString).toList();

		while(true) {
			System.out.println("Please enter " + valuesAsStrings.stream().reduce("", (part, str) -> part + ", " + str));
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();

			if (valuesAsStrings.stream().anyMatch(valuesAsStrings::contains)) return userInput;

		}
	}

	// Get text from the user
	public static String getText() {
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}

	/*** Output Methods ***/

	public static void error(String text, String source) {
		String sourceText = "";
		Optional<String> opt = Optional.ofNullable(source);

		if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

		ColorConsole.e("[Error]" + sourceText + " " +text + "\n", false);
	}

	public static void error(String text) {
		error(text, null);
	}

	public static void warn(String text,  String source) {
		String sourceText = "";

		Optional<String> opt = Optional.ofNullable(source);
		if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

		ColorConsole.i("[Warning]" + sourceText + " " + text + "\n", false);
	}

	public static void info(String text, String source) {
		String sourceText = "";

		Optional<String> opt = Optional.ofNullable(source);
		if (opt.isPresent()) sourceText = "[" + opt.get() + "]";

		ColorConsole.i("[Info]" + sourceText + " " + text + "\n", false);
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

	public static String lpad(String s, int length) {
		StringBuilder sb = new StringBuilder();

		sb.append(s).append(" ".repeat(length-s.length()));

		return sb.toString();
	}

	public static void padList(List<String> arr1, List<String> arr2) {
		int max = Math.max(arr1.size(), arr2.size());

		while(arr1.size() < max) {
			arr1.add("");
		}
		while(arr2.size() < max) {
			arr2.add("");
		}
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static int maxLength(Collection<String> strings) {
		return strings.stream().max(Comparator.comparing(String::length)).get().length();
	}

	public static String combineBlocks(String s1, String s2, boolean separateWithSpace) {
		int maxLength = 0;

		List<String> lines1 = new ArrayList<>(List.of(s1.split("\n")));
		List<String> lines2 = new ArrayList<>(List.of(s2.split("\n")));

		padList(lines1, lines2);

		maxLength = maxLength(lines1);
		for (int i = 0; i <lines1.size(); i++)
			lines1.set(i, lpad(lines1.get(i), maxLength));

		maxLength = maxLength(lines2);
		for (int i = 0; i <lines2.size(); i++)
			lines2.set(i, lpad(lines2.get(i), maxLength));

		StringBuilder sb = new StringBuilder();

		if (lines1.size() != lines2.size()) {
			LogUtils.error("Cannot combine text blocks, they have different lengths!\n", null);
			return null;
		}

		for (int i = 0; i < lines1.size(); i++) {
			sb.append(lines1.get(i));
			if (separateWithSpace) sb.append(" ");
			sb.append(lines2.get(i));
			sb.append("\n");
		}

		return sb.toString();
	}



}