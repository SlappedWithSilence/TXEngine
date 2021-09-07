package mckeken.color;

public class ColorConsole {

    //Dialog
    public static String d(String text, Boolean silent) {

        if (!silent) System.out.print(Colors.WHITE + text + Colors.RESET);

        return Colors.WHITE + text + Colors.RESET;
    }

    //Item
    public static String i(String text, Boolean silent) {

        if (!silent)System.out.print(Colors.YELLOW + text + Colors.RESET);

        return Colors.YELLOW + text + Colors.RESET;
    }

    //Player's name
    public static String p(String text, Boolean silent) {

        if (!silent)System.out.print(Colors.GREEN + text + Colors.RESET);

        return Colors.GREEN + text + Colors.RESET;
    }

    //Enemy
    public static String e(String text, Boolean silent) {

        if (!silent)System.out.print(Colors.RED + text + Colors.RESET);

        return Colors.RED + text + Colors.RESET;
    }

    //NPC name
    public static String n(String text, Boolean silent) {

        if (!silent)System.out.print(Colors.BLUE + text + Colors.RESET);

        return Colors.BLUE + text + Colors.RESET;
    }

    

}