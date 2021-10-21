package txengine.main;

import txengine.io.CrashReporter;
import txengine.systems.dungeon.Dungeon;
import txengine.ui.LogUtils;
import txengine.ui.Out;

import java.util.ArrayList;

public class Handlers {

    public static void registerAll() {
        // Normal handlers
        ArgsHandler.getInstance().registerHandler(debugHandler().getPreferredTrigger(), debugHandler());
        ArgsHandler.getInstance().registerHandler(verbosityHandler().getPreferredTrigger(), verbosityHandler());

        // Late handlers
        ArgsHandler.getInstance().registerLateHandler(startingRoomHandler().getPreferredTrigger(), startingRoomHandler());
        ArgsHandler.getInstance().registerLateHandler(dumpDungeons().getPreferredTrigger(), dumpDungeons());
    }

    // Sets the verbosity
    public static Handler verbosityHandler() {
        return new Handler() {
            @Override
            public boolean handle(ArrayList<String> values) {
                if (values.isEmpty()) Out.setVerbosity(2);
                else {
                    try {
                        Out.setVerbosity(Integer.parseInt(values.get(0)));
                    } catch (Exception e) {
                        Out.setVerbosity(2);
                    }
                }
                return true;
            }

            @Override
            public String getPreferredTrigger() {
                return "-v";
            }
        };
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
                        Out.error("Expected an integer!","ArgsHandler");
                        return false;
                    }

                    Out.error("Expected an integer! Found " + values.get(0),"ArgsHandler");
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
    public static Handler dumpDungeons() {
        return new Handler() {
            @Override
            public boolean handle(ArrayList<String> values) {
                Out.info("Running...", "Handlers::dumpDungeons");
                int repititions = 1;
                try {
                    repititions = Integer.parseInt(values.get(0));
                } catch (Exception e) {
                    Out.info("Assuming value of 1","Handlers::dumpDungeons");
                }

                CrashReporter.getInstance().clear();
                for (int i = 0; i < repititions; i++) {
                    Dungeon d = new Dungeon();
                    d.generate();
                    CrashReporter.getInstance().append("Seed: " + d.getSeed() + "\n");
                    CrashReporter.getInstance().append(d.toString());
                }
                //CrashReporter.getInstance().write("dungeon-report.txt");
                CrashReporter.getInstance().write("dungeon-report.txt");
                return true;
            }

            @Override
            public String getPreferredTrigger() {
                return "-generatedungeons";
            }
        };
    }

}
