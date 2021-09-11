package txengine.systems.combat;

import txengine.systems.ability.Ability;
import txengine.io.LogUtils;
import txengine.systems.item.Item;
import txengine.main.Manager;
import txengine.systems.inventory.Inventory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Player extends CombatEntity {

	enum Gender {
		MALE,
		FEMALE
	}

	private final String DEFAULT_PLAYER_NAME = "Player";

	private int level;
	private int levelXP; // TODO: Implement XP system
	private int levelXPMax;
	private int nextLevelXPModifier;

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
	public AbstractMap.SimpleEntry<Ability, Item> makeChoice(CombatEngine engine) {
		AbstractMap.SimpleEntry<Ability, Item> choice = null;

		LogUtils.header("Combat - Make a Choice");

		ArrayList<String> data = new ArrayList<>();
		data.add(name);
		data.add("Lvl: " + Manager.player.level);
		data.add(Manager.primaryResource + ": " + Manager.player.getResourceManager().getResourceQuantity(Manager.primaryResource));

		String[] options = {"Use an Ability", "Use an Item", "Inspect an Entity"};

		LogUtils.subHeader(data);

		List<List<String>> friendlyData = engine.getEntities(CombatEngine.EntityType.FRIENDLY).stream().filter(combatEntity -> !(combatEntity instanceof Player)).map(CombatEntity::getData).toList();
		List<List<String>> hostileData = engine.getEntities(CombatEngine.EntityType.HOSTILE).stream().map(CombatEntity::getData).toList();

		LogUtils.parallelVerticalTabList(friendlyData, hostileData, "Friendly", "Hostile");

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

					if (abilityChoice > -1 && abilityManager.isSatisfied(abilityManager.getAbilityList().get(abilityChoice))) {
						Ability ab = abilityManager.getAbilityList().get(abilityChoice);

						if (ab.getTargetMode() == CombatEngine.TargetMode.SINGLE || ab.getTargetMode() == CombatEngine.TargetMode.SINGLE_ENEMY || ab.getTargetMode() == CombatEngine.TargetMode.SINGLE_FRIENDLY) {
							ab.setTarget(chooseTarget(engine.getValidTargets(ab)));
						}

						return new AbstractMap.SimpleEntry<>(ab, null);
					} else if (abilityChoice == -1) {
						break;
					}

					if (!abilityManager.isSatisfied(abilityManager.getAbilityList().get(abilityChoice))) System.out.println("You don't have the resources to use that ability!");

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

	private static CombatEntity chooseTarget(List<CombatEntity> entityArrayList) {
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