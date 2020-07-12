package mckeken.main;

import mckeken.color.*;
import mckeken.io.*;



public class Manager 
{

	// *** Constants *** //
	private static String INTRO_TEXT = 
                     "\t********************************************\n" +
									   "\t*****            The Alley             *****\n" +
									   "\t********************************************\n" +
									   "\n\nWelcome to The Alley. This is a fantasy text-based game. All features and functions of this game" +
									   " and its story are original and fictional. Please do not modify or re-host this software without\n"   +
                     " askisng first.\n";
                     
  private static String LOAD_GAME_TEXT = "\nWould you like to resume from your saved game? (Y/N)\n";

   // *** Variables *** //
   private static boolean saveExists = false;
   private static boolean createNewGame = true;
   


   // The class that handles the main menu, then launches the game.
    public static void main( String[] args )
    {
        intro();
        saveExists = Load.hasSave();

        if (saveExists) {
            promptLoadGame();
            if (LogUtils.getAffirmative()) Load.loadGame();
        }

    }

    private static void intro() {
    	//System.out.print(INTRO_TEXT);
      ColorConsole.d(INTRO_TEXT);

	}

  // Prompts the user if they want to resume their saved game
  private static void promptLoadGame() {
    ColorConsole.d(LOAD_GAME_TEXT);


  }

}


