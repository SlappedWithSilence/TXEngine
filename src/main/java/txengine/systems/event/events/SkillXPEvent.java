package txengine.systems.event.events;

import txengine.io.LoadUtils;
import txengine.main.Manager;
import txengine.systems.event.Event;
import txengine.util.Utils;

import java.util.AbstractMap;
import java.util.List;

public class SkillXPEvent extends Event {

    public SkillXPEvent() {

    }

    @Override
    protected void execute() {
        String skillName = getProperties()[0];
        int xp = Integer.parseInt(getProperties()[1]);

        Manager.skillManager.gainXP(skillName, xp);
    }

    @Override
    protected String print() {
        String skillName = getProperties()[0];
        int xp = Integer.parseInt(getProperties()[1]);

        return "You gained " + xp + " " + skillName + " experience!";
    }
}
