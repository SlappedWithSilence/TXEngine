package txengine.io.save;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import txengine.main.Manager;

import java.util.TreeMap;

// A collection of static methods that return Exporters with a specific purpose
public class Exporters {

    // Exports player-specific data to JSON object
    public static Exporter playerData() {
        return (new Exporter() {

            @Override
            @SuppressWarnings("unchecked")
            public JSONObject toJSON() {
                JSONObject playerJSON = new JSONObject();

                playerJSON.put("name", Manager.player.getName());
                playerJSON.put("location", Manager.player.getLocation());
                playerJSON.put("money", Manager.player.getMoney());

                return playerJSON;
            }

            @Override
            public String getKey() {
                return "player";
            }
        });
    }

    // Exports the player's inventory data to a JSON object
    public static Exporter inventoryData() {
        return new Exporter() {
            @Override
            @SuppressWarnings("unchecked")
            public JSONObject toJSON() {
                JSONObject inventoryJSON = new JSONObject();

                inventoryJSON.put("ids",JSONValue.toJSONString(Manager.player.getInventory().getItemIDs()));
                inventoryJSON.put("quantities",JSONValue.toJSONString(Manager.player.getInventory().getItemQuantities()));

                return inventoryJSON;
            }

            @Override
            public String getKey() {
                return "inventory";
            }
        };
    }

    public static Exporter combatResourceData() {
        return new Exporter() {
            @Override
            public JSONObject toJSON() {
                JSONObject combatResourceJSON = new JSONObject();

                JSONArray arr = new JSONArray();

                TreeMap<String, Integer[]> map = Manager.player.getResourceManager().getResources();

                for (String key : Manager.player.getResourceManager().getResources().keySet()) {
                    JSONObject resource = new JSONObject();
                    resource.put("name", key);
                    resource.put("max", map.get(key)[0]);
                    resource.put("current", map.get(key)[1]);
                }

                return combatResourceJSON;
            }

            @Override
            public String getKey() {
                return "combat_resources";
            }
        };
    }


}
