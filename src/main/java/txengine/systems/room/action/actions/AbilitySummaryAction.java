package txengine.systems.room.action.actions;

import txengine.systems.integration.Requirement;
import txengine.ui.component.Components;
import txengine.ui.LogUtils;
import txengine.main.Manager;
import txengine.systems.room.action.Action;

public class AbilitySummaryAction extends Action {

    final String prompt = "Which ability would you like to inspect? (-1 to exit)";

    @Override
    public int perform() {

        while (true) {
            Manager.player.getAbilityManager().setOwner(Manager.player);

            Components.header("Abilities");

            System.out.println(prompt);

            Manager.player.getAbilityManager().printAbilities();
            Components.bar();
            int userChoice = LogUtils.getNumber(-1, Manager.player.getAbilityManager().getAbilityQuantity()-1);

            if (userChoice == -1) break;

            Components.header("Ability Description");
            System.out.println(Manager.player.getAbilityManager().getAbilityList().get(userChoice).getDescription()); // Print Ability desc
            System.out.println("Damage: " + Manager.player.getAbilityManager().getAbilityList().get(userChoice).getDamage());
            System.out.println("Resource Costs: " + Manager.player.getAbilityManager().resourceCostsAsString(Manager.player.getAbilityManager().getAbilityList().get(userChoice))); // Print ability costs
            System.out.println("Requirements: ");
            for (String s : Requirement.asStrings(Manager.player.getAbilityManager().getAbilityList().get(userChoice).getRequirements())) System.out.println(s);

            Components.bar();
            System.out.println();
        }


        return unhideIndex;
    }
}
