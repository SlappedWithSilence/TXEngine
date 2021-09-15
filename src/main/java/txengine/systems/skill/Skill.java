package txengine.systems.skill;

import com.sun.source.tree.Tree;
import txengine.systems.event.Event;

import java.util.List;
import java.util.TreeMap;

public class Skill {


    /* Member Variables */
    String name;
    String description;

    int level;

    int xp; // Amount of current xp
    int levelUpXP; // Amount of xp required to level up
    float levelRatio; // How much the levelUpXP increases per level.

    TreeMap<Integer, List<Event>> levelUpEvents;

    /* Constructors */
    public Skill() {
        level = 1;
        xp = 0;
        levelUpXP = 5;
        levelRatio = 1.3f;

        name = "A Skill";
        description = "A generic skill.";

        levelUpEvents = new TreeMap<>();
    }

    public Skill(String name, String desc, int level, int xp, int levelUpXP, float levelRatio, TreeMap<Integer, List<Event>> levelEvents) {
        this.name = name;
        this.description = desc;
        this.level = level;
        this.xp = xp;
        this.levelUpXP = levelUpXP;
        this.levelRatio = levelRatio;
        this.levelUpEvents = levelEvents;
    }

    /* Member Methods */

    // Distribute the given XP, leveling up as many times as necessary before the XP runs out.
    public void gainXP(int xp) {
        int remainingXP = xp;

        while (remainingXP > 0) {
            if (levelUpXP - xp <= remainingXP) {
                remainingXP = remainingXP - (levelUpXP - xp);
                levelUp();
            } else {
                this.xp = this.xp + remainingXP;
                remainingXP = 0;
            }
        }

    }

    // Increase the level of the skill and adjusts the skills maxXP
    // Executes any events
    public void levelUp() {
        level = level + 1;
        xp = 0;
        levelUpXP = (int)(levelUpXP * levelRatio);

        if (levelUpEvents.containsKey(level)) {
            levelUpEvents.get(level).forEach(Event::perform);
        }

    }

    /* Accessor Methods */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
