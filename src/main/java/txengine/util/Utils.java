package txengine.util;

import java.util.*;

public class Utils {

	// Gets all occurrences of a number in an arraylist
	public static ArrayList<Integer> getAllInstances(ArrayList list, int value) {
		ArrayList<Integer> indexes = new ArrayList<Integer>();


		for (int i = 0; i < list.size(); i++) if ( (int) list.get(i) == value) indexes.add(i);

		return indexes;
	}

	public static List<AbstractMap.SimpleEntry<Integer, Integer>> parseIntPairs(String separatedPairs) {
		String[] pairs = separatedPairs.split(" ");
		return Arrays.stream(pairs).map(pair -> {
			String[] s = pair.split(",");
			return new AbstractMap.SimpleEntry<Integer, Integer>(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
		}).toList();
	}

	public static int[] parseInts(String separatedValues, String sep) {
		String[] rawValues = separatedValues.split(sep);
		int[] values = new int[rawValues.length];

		for (int i = 0; i < rawValues.length; i++) values[i] = Integer.parseInt(rawValues[i]);

		return values;
	}

	public static double[] parseDoubles(String separatedValues, String sep) {
		String[] rawValues = separatedValues.split(sep);
		double[] values = new double[rawValues.length];

		for (int i = 0; i < rawValues.length; i++) values[i] = Double.parseDouble(rawValues[i]);

		return values;
	}

	public static List<Integer> toInts(Collection<String> collection) {
		return collection.stream().map(Integer::parseInt).toList();
	}

	// Parses a pair of string-int values into a list of pairs. pair values are stored in a single string with
	// the following format:
	// "<String><sep><Int>"
	// Ex: sep = "," | "Health,40"
	// Ex: sep = " " | "Health 40"
	public static List<AbstractMap.SimpleEntry<String, Integer>> parseStringIntPairs(Collection<String> rawPairs, String sep, boolean invert) {
		if (!invert) return rawPairs.stream().map(pair -> {
			String[] sArr = pair.split(sep);
			return new AbstractMap.SimpleEntry<>(sArr[0], Integer.parseInt(sArr[1]));
		}).toList();

		else return rawPairs.stream().map(pair -> {
			String[] sArr = pair.split(sep);
			return new AbstractMap.SimpleEntry<>(sArr[1], Integer.parseInt(sArr[0]));
		}).toList();
	}

	public static List<AbstractMap.SimpleEntry<String, Integer>> parseStringIntPairs(Collection<String> rawPairs) {
		return parseStringIntPairs(rawPairs, ",", false);
	}
}