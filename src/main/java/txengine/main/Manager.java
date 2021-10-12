package txengine.main;

import txengine.io.load.LoadManager;
import txengine.io.loaders.*;
import txengine.io.save.Exporters;
import txengine.systems.crafting.RecipeManager;
import txengine.systems.dungeon.Dungeon;
import txengine.systems.reputation.Faction;
import txengine.systems.reputation.FactionManager;
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
    private static final String LOAD_GAME_TEXT = "\nWould you like to resume from your saved game? (Y/N)\n";

    private static final String ITEM_RESOURCE_FILE = "items.json";
    private static final String ROOM_RESOURCE_FILE = "rooms.json";
    private static final String CONVERSATION_RESOURCE_FILE = "conversations.json";
    private static final String PLAYER_RESOURCE_FILE = "combat_resources.json";
    private static final String ABILITY_RESOURCE_FILE = "abilities.json";
    private static final String COMBAT_ENTITY_RESOURCE_FILE = "combat_entities.json";
    private static final String RECIPES_RESOURCE_FILE = "recipes.json";
    private static final String SKILLS_RESOURCE_FILE = "skills.json";
    private static final String FACTION_RESOURCE_FILE = "factions.json";

    // *** Variables *** //
    public static boolean debug = false;

    public static Player player;

    public static HashMap<Integer, Item> itemHashMap;
    public static HashMap<Integer, Conversation> conversationHashMap;
    public static HashMap<String, Integer[]> playerResourceMap;
    public static HashMap<String, Ability> abilityHashMap;
    public static HashMap<Integer, CombatEntity> combatEntityHashMap;

    public static String primaryResource = "Health";
    public static String primarySkill = "Combat";
    public static String primaryCurrency = "Kirea";

    public static FlagManager flagManager;
    public static SkillManager skillManager;
    public static RoomManager roomManager;
    public static RecipeManager recipeManager;
    public static FactionManager factionManager;

    // The class that handles the main menu, then launches the game.
    public static void main(String[] args ) {

        Handlers.registerAll();
        ArgsHandler.getInstance().parseArgs(args);
        ArgsHandler.getInstance().run();

        initialize();

        if (LoadManager.getInstance().hasSave()) {
            System.out.println("Do you want to resume your game?");
            boolean resumeGame = LogUtils.getAffirmative();

            if (resumeGame) LoadManager.getInstance().loadGame();
            else  LoadManager.getInstance().initializeNewGame();

        } else {
            LoadManager.getInstance().initializeNewGame();
        }

        if (debug) initDebug();

        ArgsHandler.getInstance().runLate();

        // Start the main game loop
        roomManager.roomLoop();

    }

    // **** Prompt functions ****

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
        HashMap<Integer, Room> roomHashMap = new RoomLoader().load(Resources.getResourceAsFile(ROOM_RESOURCE_FILE));
        conversationHashMap = new ConversationLoader().load(Resources.getResourceAsFile(CONVERSATION_RESOURCE_FILE));
        playerResourceMap = new CombatResourceLoader().load(Resources.getResourceAsFile(PLAYER_RESOURCE_FILE));
        abilityHashMap = new AbilityLoader().load(Resources.getResourceAsFile(ABILITY_RESOURCE_FILE));
        combatEntityHashMap = new CombatEntityLoader().load(Resources.getResourceAsFile(COMBAT_ENTITY_RESOURCE_FILE));
        HashMap<Integer, Recipe> recipeHashMap = RecipeLoader.load(Resources.getResourceAsFile(RECIPES_RESOURCE_FILE));
        HashMap<String, Skill> skillHashMap = new SkillLoader().load(Resources.getResourceAsFile(SKILLS_RESOURCE_FILE));
        HashMap<String, Faction> factionHashMap = new FactionLoader().load(Resources.getResourceAsFile(FACTION_RESOURCE_FILE));

        // Set up global managers
        flagManager = new FlagManager();
        skillManager = new SkillManager(skillHashMap);
        roomManager = new RoomManager(roomHashMap);
        recipeManager = new RecipeManager(recipeHashMap);
        factionManager = new FactionManager(factionHashMap);

        Exporters.registerAll();

        player = new Player();
    }

    private static void initDebug() {
        if (combatEntityHashMap == null) combatEntityHashMap = new HashMap<>();

        combatEntityHashMap.put(-1, new CombatEntity("Grunt", "", "",10,  new Inventory(), new AbilityManager(), new CombatResourceManager(), new EquipmentManager() ,2, 1, 15));
        combatEntityHashMap.put(-2, new CombatEntity("Smokey the Bear", "", "",10,  new Inventory(), new AbilityManager(), new CombatResourceManager(), new EquipmentManager(), 5, 25, 15));

        player.getAbilityManager().learn("Inversion");
        player.getAbilityManager().learn("Smack");
        player.getAbilityManager().learn("Kidnap");
        player.getAbilityManager().learn("Spit");
        player.getAbilityManager().learn("Bomb-Threat");
        player.getAbilityManager().learn("Blast");

        for (Recipe r : recipeManager.getRecipes()) recipeManager.learn(r);

        player.getInventory().addItem(16);
        player.getInventory().addItem(17);

        CrashReporter.getInstance().clear();
        for (int i = 0; i < 10; i++) {
            Dungeon dungeon = new Dungeon();
            dungeon.enter();
            //System.out.println(dungeon);
            CrashReporter.getInstance().append(dungeon.toString());
        }
        CrashReporter.getInstance().write();

    }

}
