package txengine.systems.room;

import txengine.main.Manager;

import java.util.ArrayList;

public class RoomManager {

    private static ArrayList<Integer> visitedRooms = new ArrayList<>();

    // The main game loop that puts players in rooms and starts their internal loops
   public static void roomLoop() {


       while (true) {

           // Start the internal action loop of the room that the player is currently in.
           // Ideally, this should never be repeat the same room. The room that the player is currently in should update the player's location before breaking out
           // of its internal loop and back up into this one.
           Manager.roomHashMap.get(Manager.player.getLocation()).enter();
           visitedRooms.add(Manager.player.getLocation());
       }
   }

    public static ArrayList<Integer> getVisitedRooms() {
        return visitedRooms;
    }

    public static void setVisitedRooms(ArrayList<Integer> visitedRooms) {
        RoomManager.visitedRooms = visitedRooms;
    }
}