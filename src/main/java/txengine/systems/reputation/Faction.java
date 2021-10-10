package txengine.systems.reputation;

import txengine.ui.component.Components;

import java.util.ArrayList;
import java.util.Collection;

// A representation of how well-liked the player is by a designer-defined faction.
// Faction "reputation" may be gained or lost by triggering in-game events via the Event system.
public class Faction implements Components.Tabable {

    public enum Growth {
        MAJOR,
        AVERAGE,
        MINOR
    }

    public enum GrowthMode {
        GAIN,
        LOSS
    }

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

    public int changeXP(int XPChange) {
        xp = xp + XPChange;
        if (xp > levelUpXP) levelUp();

        return XPChange;
    }

    public int changeXP(Growth growth, GrowthMode mode) {
        int mod = 1;
        if (mode == GrowthMode.LOSS) mod = -1;
        xp = xp + (int)(levelUpXP * growthMap(growth) * mod);
        if (xp > levelUpXP) levelUp();

        return (int)(levelUpXP * growthMap(growth) * mod);
    }

    public void levelUp() {
        xp = 0;
        level++;
        levelUpXP = (int) (levelUpXP * levelUpRatio);
    }

    /*** Helper Methods ***/
    private float growthMap(Growth g) {
        switch (g) {
            case MAJOR -> {
                return 0.33f;
            }
            case AVERAGE -> {
                return 0.2f;
            }
            case MINOR -> {
                return 0.1f;
            }
            default -> {
                return 0.0f;
            }
        }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append('\t').append(xp).append('/').append(levelUpXP).append('\t').append(Components.percentBar(((double) xp / (double) levelUpXP)*100, '#'));
        return sb.toString();
    }
}
