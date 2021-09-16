package txengine.ui.component;

import txengine.ui.LogUtils;
import txengine.ui.color.Colors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static txengine.ui.LogUtils.centerString;

// A collection of static UI components
public class Components {

    /*************
     * Constants *
     *************/
    public final static int HEADER_LENGTH = 105;
    public final static int HEADER_SPACES = 3;

    /**********************
     * Titling Components *
     **********************/

    public static void header(String title) {
        header(title, HEADER_LENGTH, '-', Colors.WHITE);
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

        line1.append("\n|").append("_".repeat(Components.HEADER_LENGTH-2)).append("|"); // Long bar bottom

        System.out.println(line1);
    }

    /*******************
     * List Components *
     *******************/

    public static void numberedList(List<String> list) {
        System.out.println("-".repeat(HEADER_LENGTH));
        for (int i = 0; i < list.size(); i++) {
            System.out.println("[" + i + "] " + list.get(i));
        }
        System.out.println("-".repeat(HEADER_LENGTH));
    }
    public static void numberedList(String[] list) {
        numberedList(new ArrayList<>(List.of(list)));
    }

    /******************
     * Bar Components *
     ******************/

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

    public static void bar() {
        bar('-', HEADER_LENGTH);
    }

    public static void bar (char sep, int length) {
        String bar = ""+sep;
        System.out.println(bar.repeat(length));
    }

    /******************
     * Tab Components *
     ******************/

    public interface Tabable {
        Collection<String> getTabData();
    }

    // returns a single tab with its text aligned vertically
    public static String verticalTab(Tabable element) {
        if (element == null) return verticalTab(new ArrayList<>());
        return verticalTab(element.getTabData());
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

    // returns two horizontally-adjacent VerticalTabs
    public static String parallelVerticalTab(Collection<String> left, Collection<String> right) {
        ArrayList<String> leftInternal = new ArrayList<>(left);
        ArrayList<String> rightInternal = new ArrayList<>(right);

        StringBuilder sb = new StringBuilder();

        String topBar = "-".repeat(HEADER_LENGTH/2);
        String bottomBar = "_".repeat((HEADER_LENGTH-2)/2);
        final char SIDE_WALL = '|';

        int mode = 0;

        if (left.size() == right.size()) mode = 0; // The tab lengths are the same
        if (left.size() > right.size())  mode = 1; // left tab is longer
        if (left.size() < right.size())  mode = 2; // right tab is longer

        if (mode == 1) { // Normalize the tab sizes by inserting empty lines into the right tab
            for (int i = 0; i < left.size() - right.size(); i++) rightInternal.add("");
        }
        if (mode == 2) { // Normalize the tab sizes by inserting empty lines into the left tab
            for (int i = 0; i < right.size() - left.size(); i++) leftInternal.add("");
        }

        sb.append(topBar + "   " + topBar).append('\n');

        int leftSpaceOffset = 0;

        for (int i = 0; i < Math.max(rightInternal.size(), leftInternal.size()); i++) {
            sb.append(SIDE_WALL).append(centerString(leftInternal.get(i), (HEADER_LENGTH-2)/2)).append(SIDE_WALL); // Print the text for the r

            if (HEADER_LENGTH % 2 != 0) {
                sb.append(" ");
                leftSpaceOffset = 2;
            }

            sb.append(SIDE_WALL).append(centerString(rightInternal.get(i), (HEADER_LENGTH-2)/2)).append(SIDE_WALL);
            sb.append('\n');
        }

        sb.append(SIDE_WALL).append(bottomBar).append(SIDE_WALL).append(" ").append(SIDE_WALL).append(bottomBar).append(SIDE_WALL);
        return sb.toString();
    }

    // returns a vertical list of VerticalTabs
    public static void verticalTabList(List<List<String>> strings) {
        for (List<String> arr : strings) {
            System.out.println(verticalTab(arr));
        }
    }

    public static void verticalTabList(Collection<Tabable> elements) {
        for (Tabable element : elements)  System.out.println(verticalTab(element));
    }

    // returns a vertical list of horizontal VerticalTab pairs
    public static void parallelVerticalTabList(List<List<String>> left, List<List<String>> right, int length, String lefTitle, String rightTitle) {
        List<List<String>> leftInternal = new ArrayList<>(left);
        List<List<String>> rightInternal = new ArrayList<>(right);

        int mode = 0;

        if (left.size() == right.size()) mode = 0; // The tab lengths are the same
        if (left.size() > right.size())  mode = 1; // left tab is longer
        if (left.size() < right.size())  mode = 2; // right tab is longer

        if (mode == 1) { // Normalize the tab sizes by inserting empty lines into the right tab
            for (int i = 0; i < left.size() - right.size(); i++) rightInternal.add(new ArrayList<String>());
        }
        if (mode == 2) { // Normalize the tab sizes by inserting empty lines into the left tab
            for (int i = 0; i < right.size() - left.size(); i++) leftInternal.add(new ArrayList<String>());
        }

        if (lefTitle != null && rightTitle != null && !rightTitle.equals("") && !lefTitle.equals("")) { // Print headers
            StringBuilder sb = new StringBuilder();

            String topBar = "-".repeat(HEADER_LENGTH/2);
            sb.append(topBar).append("   ").append(topBar).append('\n');
            sb.append('|').append(centerString(lefTitle, HEADER_LENGTH/2 - 2)).append("|").append("   ").append("|").append(centerString(rightTitle, HEADER_LENGTH/2 - 2)).append("|");

            System.out.println(sb);
        }

        for (int i = 0; i < right.size(); i++) {
            System.out.println(parallelVerticalTab(leftInternal.get(i), rightInternal.get(i)));
        }
    }

    public static void parallelVerticalTabList(List<Tabable> left, List<Tabable> right, String lefTitle, String rightTitle, int spaces) {
        List<Tabable> leftInternal = new ArrayList<>(left);
        List<Tabable> rightInternal = new ArrayList<>(right);

        int mode = 0;

        if (left.size() == right.size()) mode = 0; // The tab lengths are the same
        if (left.size() > right.size())  mode = 1; // left tab is longer
        if (left.size() < right.size())  mode = 2; // right tab is longer

        if (mode == 1) { // Normalize the tab sizes by inserting empty lines into the right tab
            for (int i = 0; i < left.size() - right.size(); i++) rightInternal.add(ArrayList::new);
        }
        if (mode == 2) { // Normalize the tab sizes by inserting empty lines into the left tab
            for (int i = 0; i < right.size() - left.size(); i++) leftInternal.add(ArrayList::new);
        }

        if (lefTitle != null && rightTitle != null && !rightTitle.equals("") && !lefTitle.equals("")) { // Print headers
            StringBuilder sb = new StringBuilder();

            String topBar = "-".repeat(HEADER_LENGTH/2);
            sb.append(topBar).append("   ").append(topBar).append('\n');
            sb.append('|').append(centerString(lefTitle, HEADER_LENGTH/2 - 2)).append("|").append("   ").append("|").append(centerString(rightTitle, HEADER_LENGTH/2 - 2)).append("|");

            System.out.println(sb);
        }

        for (int i = 0; i < right.size(); i++) {
            System.out.println(parallelVerticalTab(leftInternal.get(i).getTabData(), rightInternal.get(i).getTabData()));
        }
    }


   /* public static void parallelVerticalTabList(List<Tabable> left, List<Tabable> right, String lefTitle, String rightTitle) {
        List<Tabable> leftInternal = new ArrayList<>(left);
        List<Tabable> rightInternal = new ArrayList<>(right);

        int mode = 0;

        if (left.size() == right.size()) mode = 0; // The tab lengths are the same
        if (left.size() > right.size())  mode = 1; // left tab is longer
        if (left.size() < right.size())  mode = 2; // right tab is longer

        if (mode == 1) { // Normalize the tab sizes by inserting empty lines into the right tab
            for (int i = 0; i < left.size() - right.size(); i++) rightInternal.add(ArrayList::new);
        }
        if (mode == 2) { // Normalize the tab sizes by inserting empty lines into the left tab
            for (int i = 0; i < right.size() - left.size(); i++) leftInternal.add(ArrayList::new);
        }

        if (lefTitle != null && rightTitle != null && !rightTitle.equals("") && !lefTitle.equals("")) { // Print headers
            StringBuilder sb = new StringBuilder();

            String topBar = "-".repeat(HEADER_LENGTH/2);
            sb.append(topBar).append("   ").append(topBar).append('\n');
            sb.append('|').append(centerString(lefTitle, HEADER_LENGTH/2 - 2)).append("|").append("   ").append("|").append(centerString(rightTitle, HEADER_LENGTH/2 - 2)).append("|");

            System.out.println(sb);
        }

        for (int i = 0; i < Math.max(leftInternal.size(), rightInternal.size()); i++) {
            System.out.println(LogUtils.combineBlocks(verticalTab(leftInternal.get(i)), verticalTab(rightInternal.get(i)), true));
        }
    }*/

    public static void parallelVerticalTabList(List<List<String>> left, List<List<String>> right, String leftTitle, String rightTitle) {
        parallelVerticalTabList(left, right, HEADER_LENGTH, leftTitle, rightTitle);
    }

    public static void parallelVerticalTabList(List<List<String>> left, List<List<String>> right) {
        parallelVerticalTabList(left, right, HEADER_LENGTH, null, null);
    }

}
