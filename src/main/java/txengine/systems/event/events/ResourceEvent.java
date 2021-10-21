package txengine.systems.event.events;

import txengine.systems.event.Event;
import txengine.ui.LogUtils;
import txengine.ui.Out;
import txengine.ui.color.Colors;

public class ResourceEvent extends Event {
    String resourceName;
    String mode;
    Integer flat;
    Double percentage;

    public ResourceEvent(String[] properties) {
        setProperties(properties);
    }

    @Override
    protected void execute() {
        resourceName = getProperties()[0];
        try {
            percentage = Double.parseDouble(getProperties()[1]);
            mode = "percentage";
        } catch (Exception ignored) {
            percentage = null;
        }
        if (percentage == null) {
            try {
                flat = Integer.parseInt(getProperties()[1]);
                mode = "flat";
            } catch (Exception e) {
                e.printStackTrace();
                Out.error("Couldn't parse" + getProperties()[1] + "!", "ResourceEvent::execute");
            }
        }
    }

    @Override
    protected String print() {
        if (mode.equals("flat")) return Colors.GREEN_BRIGHT + "You restored " + resourceName + " by " + flat + Colors.RESET;
        else return Colors.GREEN_BRIGHT + "You regained " + percentage*100 + "% of your " + resourceName + Colors.RESET;
    }
}
