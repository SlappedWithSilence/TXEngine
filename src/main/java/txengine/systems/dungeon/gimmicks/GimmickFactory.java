package txengine.systems.dungeon.gimmicks;

import txengine.systems.dungeon.Dungeon;
import txengine.systems.dungeon.DungeonGimmick;
import txengine.systems.room.action.Action;
import txengine.ui.LogUtils;
import txengine.util.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GimmickFactory {

    final private static String GIMMICK_PACKAGE = "txengine.systems.dungeon.gimmicks."; // The fully-qualified package name where Gimmicks are found
    final private static Set<String> gimmickClassSet = initClassSet();

    public GimmickFactory() {

    }


    public static DungeonGimmick build(String className, final Dungeon owner) {
        try {
            Class<?> clasz = Class.forName(GIMMICK_PACKAGE + className); // Looks up the class name passed in
            return (DungeonGimmick) clasz.getConstructors()[0].newInstance(owner);	// Returns the gimmick

        } catch (InstantiationException e) {

            LogUtils.error("Failed to build class.");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            LogUtils.error("Can't locate class: " + className);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            LogUtils.error("Something went wrong while building a Gimmick!");
            e.printStackTrace();
            return null;
        }

    }

    public static DungeonGimmick randomGimmick(final Dungeon owner) {
        return build(Utils.selectRandom(gimmickClassSet.toArray(new String[0]), owner.getRand()), owner);
    }

    private static Set<String> initClassSet() {
        Set<String> s = new HashSet<>();
        s.add("CombatGimmick");
        s.add("EmptyGimmick");
        s.add("RestGimmick");
        return s;
    }
}
