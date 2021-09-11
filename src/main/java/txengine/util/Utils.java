package txengine.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

	// Gets all occurences of a number in an arraylist
	public static ArrayList<Integer> getAllInstances(ArrayList list, int value) {
		ArrayList<Integer> indexes = new ArrayList<Integer>();


		for (int i = 0; i < list.size(); i++) if ( (int) list.get(i) == value) indexes.add(i);

		return indexes;
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
}