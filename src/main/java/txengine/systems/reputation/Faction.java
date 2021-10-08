package txengine.systems.reputation;

import txengine.ui.component.Components;

import java.util.ArrayList;
import java.util.Collection;

// A representation of how well-liked the player is by a designer-defined faction.
// Faction "reputation" may be gained or lost by triggering in-game events via the Event system.
public class Faction implements Components.Tabable {
    String name;
    int level;
    int xp;
    int levelUpXP;
    float levelUpRatio;

    /*** Constructors ***/
    public Faction(String name, int level, int xp, int levelUpXP, float levelUpRatio) {
        this.name = name;
        this.xp = xp;
        this. level = level;
        this.levelUpXP = levelUpXP;
        this.levelUpRatio = levelUpRatio;
    }

    public Faction() {
        name = "Default Faction";
        level = 0;
        xp = 0;
        levelUpXP = 1;
        levelUpRatio = 1f;
    }

    public void changeXP(int XPChange) {
        xp = xp + XPChange;
        if (xp > levelUpXP) levelUp();
    }

    public void levelUp() {
        xp = 0;
        level++;
        levelUpXP = (int) (levelUpXP * levelUpRatio);
    }

    /*** Accessor Methods ***/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevelUpXP() {
        return levelUpXP;
    }

    public void setLevelUpXP(int levelUpXP) {
        this.levelUpXP = levelUpXP;
    }

    public float getLevelUpRatio() {
        return levelUpRatio;
    }

    public void setLevelUpRatio(float levelUpRatio) {
        this.levelUpRatio = levelUpRatio;
    }

    @Override
    public Collection<String> getTabData() {
        ArrayList<String> s = new ArrayList<>();

        s.add(name);
        s.add("XP: " + xp + "/" + levelUpXP);

        return s;
    }
}
