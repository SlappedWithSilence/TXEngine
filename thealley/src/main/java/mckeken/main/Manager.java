package mckeken.main;

import mckeken.color.*;
import mckeken.io.*;
import mckeken.player.Player;



public class Manager {

	// *** Constants *** //
	private static String INTRO_TEXT = 
                     "\t********************************************\n" +
									   "\t*****            " + Colors.PURPLE_UNDERLINED + "The Alley" + Colors.RESET + "             *****\n" +
									   "\t********************************************\n" +
									   "\n\nWelcome to The Alley. This is a fantasy text-based game. All features and functions of this game" +
									   " and its story are\noriginal and fictional. Please do not modify or re-host this software without"   +
                     "asking first.\n";

  private static String LOAD_GAME_TEXT = "\nWould you like to resume from your saved game? (Y/N)\n";

   // *** Variables *** //
   private static boolean saveExists = false;
   private static boolean createNewGame = true;
   
   public static Player player;


   // The class that handles the main menu, then launches the game.
    public static void main( String[] args )
    {
        intro();                     // Display the intro text
        saveExists = Load.hasSave(); // Check for a saved game

        if (saveExists) {            // If the save exists
            promptLoadGame();        // Ask the user if they want to resume from that save
            if (LogUtils.getAffirmative()) Load.loadGame(); // If the user says yes, load the game
        } else {                     // if no save exists
            Load.initializeNewGame();     // Set up a new game
        }

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


