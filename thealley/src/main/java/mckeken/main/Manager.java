package mckeken.main;

import mckeken.color.*;
import mckeken.io.*;
import mckeken.player.Player;
import java.util.HashMap;
import mckeken.item.Item;
import mckeken.room.*;
import mckeken.room.action.actions.conversation.Conversation;

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
    private static final String ROOM_RESOURCE_FILE = "rooms.json";
    private static final String CONVERSATION_RESOURCE_FILE = "conversations.json";
    private static final String PLAYER_RESOURCE_FILE = "player_resources.json";

    // *** Variables *** //
    private static boolean saveExists = false;
    private static boolean createNewGame = true;

    public static Player player;

    public static HashMap<Integer, Item> itemList;
    public static HashMap<Integer, Room> roomList;
    public static HashMap<Integer, Conversation> conversationList;
    public static HashMap<String, Integer[]> playerResourceList;

    // The class that handles the main menu, then launches the game.
    public static void main( String[] args )
    {
        initialize();
        intro();                     // Display the intro text

        saveExists = Load.hasSave(); // Check for a saved game
        if (saveExists) {            // If the save exists
            promptLoadGame();        // Ask the user if they want to resume from that save
            if (LogUtils.getAffirmative()) Load.loadGame(); // If the user says yes, load the game
        } else {                        // if no save exists
            Load.initializeNewGame();   // Set up a new game
        }

        // Start the main game loop
        RoomManager.roomLoop();

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

    // Perform pre-game initialization.
    // - Loading resource files
    // - Declaring Player Resources
    // - etc
    private static void initialize() {

        // Load resource files
        itemList = new ItemLoader().load(Resources.getResourceAsFile(ITEM_RESOURCE_FILE));
        roomList = new RoomLoader().load(Resources.getResourceAsFile(ROOM_RESOURCE_FILE));
        conversationList = new ConversationLoader().load(Resources.getResourceAsFile(CONVERSATION_RESOURCE_FILE));
        playerResourceList = new PlayerResourceLoader().load(Resources.getResourceAsFile(PLAYER_RESOURCE_FILE));

        // Declare Player Resources
    }

}
