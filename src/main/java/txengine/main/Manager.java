package txengine.main;

import txengine.ui.color.*;
import txengine.systems.combat.CombatEntity;
import txengine.systems.combat.CombatResourceManager;
import txengine.systems.ability.Ability;
import txengine.systems.ability.AbilityManager;
import txengine.systems.combat.EquipmentManager;
import txengine.systems.crafting.Recipe;
import txengine.systems.flag.FlagManager;
import txengine.systems.inventory.Inventory;
import txengine.io.*;
import txengine.systems.combat.Player;

import java.util.*;

import txengine.systems.item.Item;
import txengine.systems.room.Room;
import txengine.systems.conversation.Conversation;
import txengine.systems.room.RoomManager;
import txengine.systems.skill.Skill;
import txengine.systems.skill.SkillManager;
import txengine.ui.LogUtils;

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
    private static final String RECIPES_RESOURCE_FILE = "recipes.json";
    private static final String SKILLS_RESOURCE_FILE = "skills.json";

    // *** Variables *** //
    private static boolean saveExists = false;
    private static boolean createNewGame = true;

    public static Player player;

    public static HashMap<Integer, Item> itemHashMap;
    public static HashMap<Integer, Room> roomHashMap;
    public static HashMap<Integer, Conversation> conversationHashMap;
    public static HashMap<String, Integer[]> playerResourceMap;
    public static HashMap<String, Ability> abilityHashMap;
    public static HashMap<Integer, CombatEntity> combatEntityHashMap;
    public static HashMap<Integer, Recipe> recipeHashMap;
    private static HashMap<String, Skill> skillHashMap;

    public static String primaryResource = "Health";
    public static String primarySkill = "Combat";
    public static String primaryCurrency = "Kirea";

    public static FlagManager flagManager;
    public static SkillManager skillManager;

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
        itemHashMap = new ItemLoader().load(Resources.getResourceAsFile(ITEM_RESOURCE_FILE));
        roomHashMap = new RoomLoader().load(Resources.getResourceAsFile(ROOM_RESOURCE_FILE));
        conversationHashMap = new ConversationLoader().load(Resources.getResourceAsFile(CONVERSATION_RESOURCE_FILE));
        playerResourceMap = new CombatResourceLoader().load(Resources.getResourceAsFile(PLAYER_RESOURCE_FILE));
        abilityHashMap = AbilityLoader.load(Resources.getResourceAsFile(ABILITY_RESOURCE_FILE));
        combatEntityHashMap = new CombatEntityLoader().load(Resources.getResourceAsFile(COMBAT_ENTITY_RESOURCE_FILE));
        recipeHashMap = RecipeLoader.load(Resources.getResourceAsFile(RECIPES_RESOURCE_FILE));
        skillHashMap = new SkillLoader().load(Resources.getResourceAsFile(SKILLS_RESOURCE_FILE));

        flagManager = new FlagManager();
        skillManager = new SkillManager(skillHashMap);
    }

    private static void initDebug() {
        if (combatEntityHashMap == null) combatEntityHashMap = new HashMap<>();

        // name,  openingDialog,  closingDialog, int inventorySize, inventory,  abilityManager,  resourceManager, int speed, int level)
        combatEntityHashMap.put(-1, new CombatEntity("Grunt", "", "",10,  new Inventory(), new AbilityManager(), new CombatResourceManager(), new EquipmentManager() ,2, 1, 15));
        combatEntityHashMap.put(-2, new CombatEntity("Smokey the Bear", "", "",10,  new Inventory(), new AbilityManager(), new CombatResourceManager(), new EquipmentManager(), 5, 25, 15));

        player.getAbilityManager().learn("Smack");
        player.getAbilityManager().learn("Love Tap");
        player.getAbilityManager().learn("Spit");
        player.getAbilityManager().learn("Bomb-Threat");
        player.getAbilityManager().learn("Blast");

        for (Recipe r : recipeHashMap.values()) player.getRecipeManager().learn(r);

        player.getInventory().addItem(16);
    }

}
