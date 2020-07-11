package mckeken.main;

import mckeken.color.*;

public class Manager 
{

	// *** Constants *** //
	private static String INTRO_TEXT = 
                     "\t********************************************\n" +
									   "\t*****            The Alley             *****\n" +
									   "\t********************************************\n" +
									   "\n\nWelcome to The Alley. This is a fantasy text-based game. All features and functions of this game" +
									   " and its story are original and fictional. Please do not modify or re-host this software without asking first.\n";

   // *** Variables *** //
   private static boolean saveExists = false;
   private static boolean createNewGame = false;
   

   
   // The class that handles the main menu, then launches the game.
    public static void main( String[] args )
    {
        intro();
    }

    private static void intro() {
    	//System.out.print(INTRO_TEXT);
      ColorConsole.d(INTRO_TEXT);

	}

}


