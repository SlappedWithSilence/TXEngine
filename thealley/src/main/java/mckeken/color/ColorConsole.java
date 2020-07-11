package mckeken.color;

public class ColorConsole {

    //Dialog
    public static void d(String text) {
        System.out.println(Colors.WHITE + text + Colors.RESET);
    }

    //Item
    public static void i(String text) {
       System.out.println(Colors.YELLOW + text + Colors.RESET);
    }

    //Player's name
    public static void p(String text) {
        System.out.println(Colors.GREEN + text + Colors.RESET);
    }

    //Enemy
    public static void e(String text) {
        System.out.println(Colors.RED + text + Colors.RESET);
    }

    //NPC name
    public static void n(String text) {
        System.out.println(Colors.BLUE + text + Colors.RESET);
    }

    

}