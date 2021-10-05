package txengine.systems.skill;

import txengine.main.Manager;
import txengine.ui.component.Components;

import java.util.HashMap;
import java.util.List;

public class SkillManager {

    /* Member Variables */

    private HashMap<String, Skill> skills;

    /* Constructors */

    public SkillManager(HashMap<String, Skill> skills) {
        this.skills = skills;
    }

    /* Member Methods */

    public void gainXP(String skillName, int quantity) {
        skills.get(skillName).gainXP(quantity);
    }

    public void incrementLevel(String skillName, int quantity) {
        skills.get(skillName).levelUp();
    }

    public void printSkills() {
        Components.header("Skills");
        Components.verticalTabList(skills.values().stream().map(s -> (Components.Tabable) s).toList());
    }

    /* Accessor Methods */

    public int getSkillLevel(String skillName) {
        return skills.get(skillName).getLevel();
    }
    public final int getSkillXP(String skillName) { return skills.get(skillName).xp; }
    public final int getSkillMaxXP(String skillName) { return skills.get(skillName).levelUpXP; }

    public List<String> getSkillNames() {
        return skills.values().stream().map(Skill::getName).toList();
    }

    public int getPrimaryLevel() {
        return skills.get(Manager.primarySkill).getLevel();
    }

}
