package mckeken.color;

public class ColorConsole {

    //Dialog
    public static void d(String text) {
        System.out.print(Colors.WHITE + text + Colors.RESET);
    }

    //Item
    public static void i(String text) {
       System.out.print(Colors.YELLOW + text + Colors.RESET);
    }

    //Player's name
    public static void p(String text) {
        System.out.print(Colors.GREEN + text + Colors.RESET);
    }

    //Enemy
    public static void e(String text) {
        System.out.print(Colors.RED + text + Colors.RESET);
    }

    //NPC name
    public static void n(String text) {
        System.out.print(Colors.BLUE + text + Colors.RESET);
    }

    

}