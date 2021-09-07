package mckeken.util;

import java.util.ArrayList;

public class Utils {

	// Gets all occurences of a number in an arraylist
	public static ArrayList<Integer> getAllInstances(ArrayList list, int value) {
		ArrayList<Integer> indexes = new ArrayList<Integer>();


		for (int i = 0; i < list.size(); i++) if ( (int) list.get(i) == value) indexes.add(i);

		return indexes;
	}
}