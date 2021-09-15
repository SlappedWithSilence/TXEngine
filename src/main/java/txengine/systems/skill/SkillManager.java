package txengine.systems.skill;

import txengine.main.Manager;

import java.util.HashMap;

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

    /* Accessor Methods */

    public int getSkillLevel(String skillName) {
        return skills.get(skillName).getLevel();
    }

    public int getPrimaryLevel() {
        return skills.get(Manager.primarySkill).getLevel();
    }

}
