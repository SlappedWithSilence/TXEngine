package mckeken.combat;

import mckeken.combat.ability.Ability;
import mckeken.inventory.*;
import mckeken.io.LogUtils;
import mckeken.item.Item;
import mckeken.main.Manager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player extends CombatEntity {

	enum Gender {
		MALE,
		FEMALE
	}

	private final String DEFAULT_PLAYER_NAME = "Player";

	private int level;

	private int money;

	// The room that the player is currently in. This value must always be a valid room id.
	private int location;

	//*** Constructors ***//

	// Default constructor
	public Player() {
		name = DEFAULT_PLAYER_NAME;
		resourceManager = new CombatResourceManager(Manager.playerResourceList);
		level = 1;

		inventory = new Inventory();
		money = 0;
		location = 0;
	}

	// Specific constructor
	public Player(String name, int level, Inventory inventory, int location, int money) {
		this.name = name;

		this.level = level;

		this.money = money;

		this.inventory = inventory;

		this.location = location;

		resourceManager = new CombatResourceManager(Manager.playerResourceList);
	}

	// Member functions
	@Override
	public AbstractMap.SimpleEntry<Ability, Item> makeChoice(CombatEngine engine) { //TODO: Write helper functions for UI
		AbstractMap.SimpleEntry<Ability, Item> choice = null;

		LogUtils.header("Combat - Make a Choice");

		ArrayList<String> data = new ArrayList<>();
		data.add("Lvl: " + Manager.player.level);
		data.add(Manager.primaryResource + ": " + Manager.player.getResourceManager().getResourceQuantity(Manager.primaryResource));

		String[] options = {"Use an Ability", "Use an Item", "Inspect an Entity"};

		LogUtils.subHeader(data);

		List<List<String>> friendlyData = engine.getEntities(CombatEngine.EntityType.FRIENDLY).stream().map(CombatEntity::getData).toList();
		List<List<String>> hostileData = engine.getEntities(CombatEngine.EntityType.HOSTILE).stream().map(CombatEntity::getData).toList();

		LogUtils.parallelVerticalTabList(friendlyData, hostileData);

		while (true) {
			System.out.println("What would you like to do?");
			LogUtils.numberedList(options);
			int userChoice = LogUtils.getNumber(0, options.length);

			switch(userChoice) {
				case 0:
					LogUtils.header("Choose an Ability");
					System.out.println("What ability do you want to use? (-1 to exit)");
					abilityManager.printAbilities();
					int abilityChoice = LogUtils.getNumber(-1, abilityManager.getAbilityQuantity()-1);

					if (abilityChoice > -1) {
						Ability ab = abilityManager.getAbilityList().get(abilityChoice);
						ab.setTarget(chooseTarget(engine.getValidTargets(ab)));
						return new AbstractMap.SimpleEntry<>(ab, null);
					}
					break;
				case 1:
					LogUtils.header("Choose an Item");
					System.out.println("What item would you like to use? (-1 to exit)");

					inventory.display();
					int itemChoice = LogUtils.getNumber(-1, inventory.getUsage());

					if (itemChoice > -1) {
						return new AbstractMap.SimpleEntry<>(null, Manager.itemList.get(itemChoice));
					}

					break;
				case 2:
					break;
				default:
			}
		}

	}

	private static CombatEntity chooseTarget(ArrayList<CombatEntity> entityArrayList) {
		if (entityArrayList == null) return null;

		System.out.println("What is your target?");

		LogUtils.verticalTabList(entityArrayList.stream().map(CombatEntity::getData).toList());

		return entityArrayList.get(LogUtils.getNumber(0,entityArrayList.size()-1));
	}

	public static boolean isPlayer(CombatEntity entity) {
		return entity instanceof Player;
	}

	public CombatResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(CombatResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public  String getName() {
		return name;
	}

	public  int getLocation() {
		return location;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}