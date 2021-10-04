package txengine.io.save;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import txengine.main.Manager;
import txengine.systems.ability.Ability;
import txengine.systems.crafting.Recipe;
import txengine.systems.room.RoomManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

// A collection of static methods that return Exporters with a specific purpose
public class Exporters {
    public static void registerAll() {
        SaveManager.getInstance().registerExporter(playerData());
        SaveManager.getInstance().registerExporter(inventoryData());
        SaveManager.getInstance().registerExporter(combatResourceData());
        SaveManager.getInstance().registerExporter(recipeData());
        SaveManager.getInstance().registerExporter(abilityData());
        SaveManager.getInstance().registerExporter(equipmentData());
        SaveManager.getInstance().registerExporter(roomStateData());
        SaveManager.getInstance().registerExporter(visitedRoomsData());
    }

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

                inventoryJSON.put("ids",Manager.player.getInventory().getItemIDs());
                inventoryJSON.put("quantities",Manager.player.getInventory().getItemQuantities());

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

                    arr.add(resource);
                }

                combatResourceJSON.put("data", arr);
                return combatResourceJSON;
            }

            @Override
            public String getKey() {
                return "combat_resources";
            }
        };
    }

    public static Exporter recipeData() {
        return new Exporter() {
            @Override
            @SuppressWarnings("unchecked")
            public JSONObject toJSON() {
                JSONObject recipeJSON = new JSONObject();

                List<Integer> recipeNames = new ArrayList<>();

                for (Recipe r : Manager.player.getRecipeManager().getRecipeList()) recipeNames.add(r.getId());

                recipeJSON.put("data",recipeNames);

                return recipeJSON;
            }

            @Override
            public String getKey() {
                return "recipes";
            }
        };
    }

    public static Exporter abilityData() {
       return new Exporter() {
           @Override
           @SuppressWarnings("unchecked")
           public JSONObject toJSON() {
               JSONObject abilitiesJSON = new JSONObject();

               List<String> abilityNames = Manager.player.getAbilityManager().getAbilityList().stream().map(Ability::getName).toList();
               abilitiesJSON.put("data", abilityNames);
               return abilitiesJSON;
           }

           @Override
           public String getKey() {
               return "abilities";
           }
       };
    }

    public static Exporter equipmentData() {
        return new Exporter() {
            @Override
            @SuppressWarnings("unchecked")
            public JSONObject toJSON() {
                JSONObject equipmentJSON = new JSONObject();

                List<Integer> equipmentIDs = Manager.player.getEquipmentManager().getIDs();
                equipmentJSON.put("data", equipmentIDs);

                return equipmentJSON;
            }

            @Override
            public String getKey() {
                return "equipment";
            }
        };
    }

    public static Exporter roomStateData() {
        return new Exporter() {
            @Override
            public JSONObject toJSON() {
                JSONObject roomStateJSON = new JSONObject();

                // Export only the rooms that the player has visited
                for (int roomID : RoomManager.getVisitedRooms()) {
                    List<Integer> hiddenActions = Manager.roomHashMap.get(roomID).getHiddenActions();
                    roomStateJSON.put(roomID,hiddenActions);
                }

                return roomStateJSON;
            }

            @Override
            public String getKey() {
                return "room_states";
            }
        };
    }

    public static Exporter visitedRoomsData() {
        return new Exporter() {
            @Override
            @SuppressWarnings("unchecked")
            public JSONObject toJSON() {
                JSONObject visitedRoomsJSON = new JSONObject();

                visitedRoomsJSON.put("data",RoomManager.getVisitedRooms());

                return visitedRoomsJSON;
            }

            @Override
            public String getKey() {
                return "visited_rooms";
            }
        };
    }
}
