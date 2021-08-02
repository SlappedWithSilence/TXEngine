package mckeken.room.action.actions;

import mckeken.io.LogUtils;
import mckeken.main.Manager;
import mckeken.room.action.Action;

import java.util.*;

public class SummaryAction extends Action {

    final int RESOURCES_PER_PAGE = 5;

    final int HEADER_LENGTH = 55;
    final String HEADER =
    "-------------------------------------------------------\n"+
	"------------           Summary           --------------\n"+
	"-------------------------------------------------------";

    final String[] barOptions = {"Previous", "Done", "Next"};

    int page = 1;

    public String formatPlayerResource(Map.Entry<String, Integer[]> entry) {
        String output = "";

        output += entry.getKey();
        output += "\t[" + entry.getValue()[1] + "/" +entry.getValue()[0] + "]";

        return output;
    }

    // Prints one page of Player Resources and their associated quantities.
    public void printPage(int pageNumber, ArrayList<String> barOptions) {
        Object[] keys = Manager.player.getResourceManager().getResources().keySet().toArray(); // the keys from the entire resourceManager map
        String firstKey = (String) keys[  (pageNumber* RESOURCES_PER_PAGE)- RESOURCES_PER_PAGE  ]; // the first key from the current page (the name of the first resource on the page
        String finalKey; // the last key from the current page (the name of the last resource on the page)

        if ( (pageNumber * RESOURCES_PER_PAGE)-1 >= keys.length ) { // Checks to see if the math overshoots the length of the resourceManager map
            finalKey = (String) keys[keys.length-1]; // If the math overshoots the map, simply set the last key on the page to the last key in the map
        } else {
            finalKey = (String) keys[pageNumber * RESOURCES_PER_PAGE -1]; // Use the key at the computed index
        }

        SortedMap<String, Integer[]> map = Manager.player.getResourceManager().getResources().subMap(firstKey, true, finalKey, true); // Generate a sub-selection of the resourceManager map that only contains the resources on the current page
        Set<String> keySet = map.keySet(); // get the keys from the sub-selection

        for (String k : keySet) { // iterate through the sub-selection's keys
           Map.Entry<String, Integer[]> p = new AbstractMap.SimpleEntry<>(k, map.get(k)); // generate a key-value pair
            System.out.println(formatPlayerResource(p));    // print the key-value pair
        }
        System.out.println(); // generate an empty line for aesthetic purposes

        LogUtils.numberedBar(barOptions, HEADER_LENGTH, ' ');   // generate a bottom bar
    }

    @Override
    public int perform() {
        page = 1;

        boolean done = false; // Tracks if the user wants to exit the page loop

        while (!done) {

            ArrayList<String> bottomBarOptions = new ArrayList<>();

            if (page > 1) bottomBarOptions.add(barOptions[0]); // If you aren't on the first page, add a back button
            bottomBarOptions.add(barOptions[1]); // Add a done option
            if (page * RESOURCES_PER_PAGE < Manager.player.getResourceManager().numberOfResources()) bottomBarOptions.add(barOptions[2]); // If there are unviewed resources, add a next-page button

            printPage(page, bottomBarOptions);
            int choice = LogUtils.getNumber(0, bottomBarOptions.size()-1);

            if (bottomBarOptions.get(choice).equals(barOptions[0])) { // If the user chose "previous"
                if (page > 1) page = page - 1;
            } else if (bottomBarOptions.get(choice).equals(barOptions[1])) {// If the user chose "done"
                done = true;
            } else if (bottomBarOptions.get(choice).equals(barOptions[2])) { // If the user chose "next"
                page = page + 1;
            }
        }


        return 0;
    }
}
