package txengine.io.load;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import txengine.io.LoadUtils;
import txengine.main.Manager;
import txengine.systems.room.RoomManager;
import txengine.ui.LogUtils;

import java.util.HashSet;

// A series of functions that return Importers. Each importer should load a specific component from the user's save file
public class Importers {

    public static void importAll(JSONObject saveObject) {
        playerData().load(saveObject);
        inventoryData().load(saveObject);
        combatResourceData().load(saveObject);
        recipeData().load(saveObject);
        abilityData().load(saveObject);
        equipmentData().load(saveObject);
        visitedRoomsData().load(saveObject);
        roomStateData().load(saveObject);
    }

    public static Importer playerData() {
        return root -> {
            JSONObject playerJSON = (JSONObject) root.get("player");
            String name = LoadUtils.asString(playerJSON,"name");
            int location = LoadUtils.asInt(playerJSON,"location");
            int money = LoadUtils.asInt(playerJSON,"money");

            Manager.player.setName(name);
            Manager.player.setLocation(location);
            Manager.player.setMoney(money);
        };
    }

    public static Importer inventoryData() {
        return root -> {
            JSONObject inventoryJSON = (JSONObject) root.get("inventory");
            int[] ids = LoadUtils.getIntArray((JSONArray) inventoryJSON.get("ids"));
            int[] quantities = LoadUtils.getIntArray((JSONArray) inventoryJSON.get("quantities"));
            for (int i = 0; i < ids.length; i++) Manager.player.getInventory().addItem(ids[i],quantities[i]);
        };
    }

    public static Importer combatResourceData() {
        return root -> {
          JSONArray combatResourceData = (JSONArray) ((JSONObject) root.get("combat_resources")).get("data");
          for (Object resource : combatResourceData) {
              JSONObject obj = (JSONObject) resource;

              String name = LoadUtils.asString(obj,"name");
              int max = LoadUtils.asInt(obj,"max");
              int current = LoadUtils.asInt(obj,"current");

              Manager.player.getResourceManager().getResources().put(name, new Integer[] {max,current});
          }
        };
    }

    public static Importer recipeData() {
        return root -> {
          JSONArray recipeJSON = (JSONArray) ((JSONObject) root.get("recipes")).get("data");
          int[] recipeIDs = LoadUtils.getIntArray(recipeJSON);
          for (int i = 0; i < recipeIDs.length; i++) Manager.player.getRecipeManager().learn(recipeIDs[i]);
        };
    }

    public static Importer abilityData() {
        return root -> {
          String[] abilityNames = LoadUtils.getStringArray((JSONArray) ((JSONObject) root.get("abilities")).get("data"));
          for (String s : abilityNames) Manager.player.getAbilityManager().learn(s);
        };
    }

    public static Importer equipmentData() {
        return root -> {
            int[] equipmentIDs = LoadUtils.getIntArray((JSONArray) ((JSONObject) root.get("equipment")).get("data"));
            for (int id : equipmentIDs) Manager.player.getEquipmentManager().equip(id);
        };
    }

    public static Importer visitedRoomsData() {
        return root -> {
          int[] visitedRoomIDs = LoadUtils.getIntArray((JSONArray) ((JSONObject) root.get("visited_rooms")).get("data"));
          HashSet<Integer> set = new HashSet<>();
          for (int id : visitedRoomIDs) set.add(id);
          RoomManager.setVisitedRooms(set);
        };
    }

    public static Importer roomStateData() {
        return root -> {
          JSONObject roomStateJSON = (JSONObject) root.get("room_states");

          for (int roomID : RoomManager.getVisitedRooms()) { // For each visited room
              JSONArray roomElement = (JSONArray) roomStateJSON.get(""+roomID);
              if (roomElement == null) LogUtils.error("Something went wrong while loading room states for room " + roomID + "!");
              int[] hiddenActions = LoadUtils.getIntArray(roomElement); // get the list of indexes of hidden actions

              Manager.roomHashMap.get(roomID).getRoomActions().forEach(action -> action.setHidden(false));
              for (int actionsIndex : hiddenActions) Manager.roomHashMap.get(roomID).getRoomActions().get(actionsIndex).setHidden(true);
          }
        };
    }
}
