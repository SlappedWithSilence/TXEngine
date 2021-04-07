package mckeken.main;

import mckeken.color.*;
import mckeken.io.*;
import mckeken.item.Usable;
import mckeken.player.Player;
import java.util.HashMap;
import mckeken.item.Item;
import mckeken.room.*;

import mckeken.item.effect.*;

public class Manager {

	// *** Constants *** //
	private static final String INTRO_TEXT =
                     "\t********************************************\n" +
									   "\t*****            " + Colors.PURPLE_UNDERLINED + "The Alley" + Colors.RESET + "             *****\n" +
									   "\t********************************************\n" +
									   "\n\nWelcome to The Alley. This is a fantasy text-based game. All features and functions of this game " +
									   "and its story are\noriginal and fictional. Please do not modify or re-host this software without "   +
                     "asking first.\n";

   private static final String LOAD_GAME_TEXT = "\nWould you like to resume from your saved game? (Y/N)\n";

   private static final String ITEM_RESOURCE_FILE = "items.json";

   // *** Variables *** //
   private static boolean saveExists = false;
   private static boolean createNewGame = true;

   public static Player player;

   public static HashMap<Integer, Item> itemList;
   public static HashMap<Integer, Room> roomList;

   // The class that handles the main menu, then launches the game.
    public static void main( String[] args )
    {
        intro();                     // Display the intro text
        saveExists = Load.hasSave(); // Check for a saved game
        itemList = Load.loadItems(Resources.getResourceAsFile(ITEM_RESOURCE_FILE));
        roomList = Load.loadRooms();


        if (saveExists) {            // If the save exists
            promptLoadGame();        // Ask the user if they want to resume from that save
            if (LogUtils.getAffirmative()) Load.loadGame(); // If the user says yes, load the game
        } else {                        // if no save exists
            Load.initializeNewGame();   // Set up a new game
        }

        LogUtils.error("HP:" + player.getHealth() + "\n");
        LogUtils.error("STA:" + player.getStamina()+ "\n");
        ((Usable) itemList.get(4)).use();
        LogUtils.error("HP:" + player.getHealth()+ "\n");
        LogUtils.error("STA:" + player.getStamina()+ "\n");

    }

  // **** Prompt functions ****
  private static void intro() {
  	//System.out.print(INTRO_TEXT);
    ColorConsole.d(INTRO_TEXT);
	}

  // Prompts the user if they want to resume their saved game
  private static void promptLoadGame() {
    ColorConsole.d(LOAD_GAME_TEXT);
  }


}
