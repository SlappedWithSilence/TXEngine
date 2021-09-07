package mckeken.main;

import mckeken.color.*;
import mckeken.combat.CombatEngine;
import mckeken.combat.CombatEntity;
import mckeken.combat.CombatResourceManager;
import mckeken.combat.ability.Ability;
import mckeken.combat.ability.AbilityManager;
import mckeken.inventory.Inventory;
import mckeken.io.*;
import mckeken.combat.Player;

import java.util.*;

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
    private static final String PLAYER_RESOURCE_FILE = "combat_resources.json";
    private static final String ABILITY_RESOURCE_FILE = "abilities.json";
    private static final String COMBAT_ENTITY_RESOURCE_FILE = "combat_entities.json";

    // *** Variables *** //
    private static boolean saveExists = false;
    private static boolean createNewGame = true;

    public static Player player;

    public static HashMap<Integer, Item> itemList;
    public static HashMap<Integer, Room> roomList;
    public static HashMap<Integer, Conversation> conversationList;
    public static HashMap<String, Integer[]> playerResourceList;
    public static HashMap<String, Ability> abilityList;
    public static HashMap<Integer, CombatEntity> combatEntityList;
    public static String primaryResource = "Health";

    // The class that handles the main menu, then launches the game.
    public static void main( String[] args )
    {


        initialize();

        saveExists = Load.hasSave(); // Check for a saved game
        if (saveExists) {            // If the save exists
            promptLoadGame();        // Ask the user if they want to resume from that save
            if (LogUtils.getAffirmative()) Load.loadGame(); // If the user says yes, load the game
        } else {                        // if no save exists
            Load.initializeNewGame();   // Set up a new game
        }

        initDebug();

        // Start the main game loop
        if (args.length > 0 && args[0].equals("-D")) return; //TODO: Remove debugging break

        RoomManager.roomLoop();

    }

    // **** Prompt functions ****
    private static void intro() {
        //System.out.print(INTRO_TEXT);
        ColorConsole.d(INTRO_TEXT, false);
    }

    // Prompts the user if they want to resume their saved game
    private static void promptLoadGame() {
    ColorConsole.d(LOAD_GAME_TEXT, false);
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
        playerResourceList = new CombatResourceLoader().load(Resources.getResourceAsFile(PLAYER_RESOURCE_FILE));
        abilityList = AbilityLoader.load(Resources.getResourceAsFile(ABILITY_RESOURCE_FILE));
        combatEntityList = new CombatEntityLoader().load(Resources.getResourceAsFile(COMBAT_ENTITY_RESOURCE_FILE));

        // Declare Player Resources
    }

    private static void initDebug() {
        if (combatEntityList == null) combatEntityList = new HashMap<>();

        // name,  openingDialog,  closingDialog, int inventorySize, inventory,  abilityManager,  resourceManager, int speed, int level)
        combatEntityList.put(-1, new CombatEntity("Grunt", "", "",10,  new Inventory(), new AbilityManager(), new CombatResourceManager(), 2, 1));
        combatEntityList.put(-2, new CombatEntity("Smokey the Bear", "", "",10,  new Inventory(), new AbilityManager(), new CombatResourceManager(), 5, 25));

        player.getAbilityManager().learn("Smack");
        player.getAbilityManager().learn("Love Tap");
        player.getAbilityManager().learn("Spit");
        player.getAbilityManager().learn("Bomb-Threat");
    }

}
