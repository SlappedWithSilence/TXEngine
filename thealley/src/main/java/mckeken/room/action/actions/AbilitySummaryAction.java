package mckeken.room.action.actions;

import mckeken.io.LogUtils;
import mckeken.main.Manager;
import mckeken.room.action.Action;

public class AbilitySummaryAction extends Action {

    final String prompt = "Which ability would you like to inspect? (-1 to exit)";

    @Override
    public int perform() {

        while (true) {
            Manager.player.getAbilityManager().setOwner(Manager.player);

            LogUtils.header("Abilities");

            System.out.println(prompt);

            Manager.player.getAbilityManager().printAbilities();
            LogUtils.bar();
            int userChoice = LogUtils.getNumber(-1, Manager.player.getAbilityManager().getAbilityQuantity()-1);

            if (userChoice == -1) break;

            LogUtils.header("Ability Description");
            System.out.println(Manager.player.getAbilityManager().getAbilityList().get(userChoice).getDescription()); // Print Ability desc
            System.out.println("Damage: " + Manager.player.getAbilityManager().getAbilityList().get(userChoice).getDamage());
            System.out.println("Resource Costs: " + Manager.player.getAbilityManager().resourceCostsAsString(Manager.player.getAbilityManager().getAbilityList().get(userChoice))); // Print ability costs

            LogUtils.bar();
            System.out.println();
        }


        return unlockIndex;
    }
}