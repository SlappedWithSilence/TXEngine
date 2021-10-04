package txengine.main;

import java.util.ArrayList;

public interface Handler {

    boolean handle(ArrayList<String> values);
    String getPreferredTrigger();

}
