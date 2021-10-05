package txengine.main;

import txengine.ui.LogUtils;

import java.util.ArrayList;

public class Handlers {

    public static void registerAll() {
        // Normal handlers
        ArgsHandler.getInstance().registerHandler(debugHandler().getPreferredTrigger(), debugHandler());


        // Late handlers
        ArgsHandler.getInstance().registerLateHandler(startingRoomHandler().getPreferredTrigger(), startingRoomHandler());
    }

    public static Handler debugHandler() {
        return new Handler() {
            @Override
            public boolean handle(ArrayList<String> values) {
                Manager.debug = true;
                return true;
            }

            @Override
            public String getPreferredTrigger() {
                return "-D";
            }
        };
    }
    public static Handler startingRoomHandler() {
        return new Handler() {
            @Override
            public boolean handle(ArrayList<String> values) {

                try {

                    int roomID = Integer.parseInt(values.get(0));
                    Manager.player.setLocation(roomID);

                } catch (Exception e) {
                    if (values.get(0) == null) {
                        LogUtils.error("Expected an integer!","ArgsHandler");
                        return false;
                    }

                    LogUtils.error("Expected an integer! Found " + values.get(0),"ArgsHandler");
                    return false;
                }

                return true;
            }

            @Override
            public String getPreferredTrigger() {
                return "-startingroom";
            }
        };
    }

}
