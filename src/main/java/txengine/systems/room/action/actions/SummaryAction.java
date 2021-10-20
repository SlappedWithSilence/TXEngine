package txengine.systems.room.action.actions;

import txengine.structures.Pair;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.systems.room.action.Action;

import java.util.*;

public class SummaryAction extends Action {

    final int RESOURCES_PER_PAGE = 5;

    final String[] barOptions = {"Previous", "Done", "Next"};

    int page = 1;

    public SummaryAction() {
        super();
        menuName = "View your summary";
    }

    public String formatPlayerResource(Map.Entry<String, Integer[]> entry) {
        String output = "";

        output += entry.getKey();
        output += "\t[" + entry.getValue()[1] + "/" +entry.getValue()[0] + "]";

        return output;
    }

    public void printPage(int pageNumber, ArrayList<String> barOptions) {
        Object[] keys = Manager.player.getResourceManager().getResources().keySet().toArray(); // the keys from the entire resourceManager map

        int finalIndex;
        int startIndex = (pageNumber* RESOURCES_PER_PAGE)- RESOURCES_PER_PAGE;

        if ( (pageNumber * RESOURCES_PER_PAGE)-1 >= keys.length ) { // Checks to see if the math overshoots the length of the resourceManager map
            finalIndex = keys.length-1; // If the math overshoots the map, simply set the last key on the page to the last key in the map
        } else {
            finalIndex = pageNumber * RESOURCES_PER_PAGE -1; // Use the key at the computed index
        }

        for (int i = startIndex; i <= finalIndex; i++) {
            Map.Entry<String, Integer[]> p = new Pair<>((String) keys[i], Manager.player.getResourceManager().getResources().get((String) keys[i])); // generate a key-value pair
            System.out.println(formatPlayerResource(p));    // print the key-value pair
        }
        System.out.println(); // generate an empty line for aesthetic purposes

        Components.numberedBar(barOptions);   // generate a bottom bar
    }

    public void printLevelBox() {
        StringBuilder line1 = new StringBuilder();

        String nameText =  "Name:     " + Manager.player.getName();
        String levelText = Manager.primarySkill + ":  "   + Manager.skillManager.getPrimaryLevel();
        String moneyText = "Currency: " + Manager.player.getMoney();

        line1.append(" ").append("_".repeat(Components.HEADER_LENGTH-2));
        line1.append("\n|").append(" ".repeat(Components.HEADER_LENGTH-2)).append("|"); // Long bar top
        // The Player's name row
        line1.append("\n| ");
        line1.append(nameText);
        line1.append(" ".repeat(Components.HEADER_LENGTH - nameText.length() - 3));
        line1.append('|').append('\n');
        // Player's level row
        line1.append("| ");
        line1.append(levelText);
        line1.append(" ".repeat(Components.HEADER_LENGTH - levelText.length()- 3));
        line1.append('|').append('\n');
        // Player's money row
        line1.append("| ");
        line1.append(moneyText);
        line1.append(" ".repeat(Components.HEADER_LENGTH - moneyText.length() - 3));
        line1.append('|').append('\n');

        line1.append("|").append("_".repeat(Components.HEADER_LENGTH-2)).append("|"); // Long bar bottom

        System.out.println(line1);
    }

    @Override
    public int perform() {
        page = 1;

        boolean done = false; // Tracks if the user wants to exit the page loop

        while (!done) {
            Components.header("Summary");
            Components.subHeader(List.of(new String[]{"Name:     " + Manager.player.getName(),
                                                    "Level:    " + Manager.player.getLevel(),
                                                    "Currency: " + Manager.player.getMoney()}));

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


        return unhideIndex;
    }
}
