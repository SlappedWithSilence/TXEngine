package txengine.systems.room.action.actions;

import txengine.main.Manager;
import txengine.systems.room.action.Action;

public class SkillSummaryAction extends Action {

    public SkillSummaryAction() {


    }

    @Override
    public int perform() {

        Manager.skillManager.printSkills();

        return unhideIndex;
    }


}
