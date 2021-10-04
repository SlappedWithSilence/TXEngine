package txengine.systems.room;

import txengine.main.Manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RoomManager {

    private static HashSet<Integer> visitedRooms = new HashSet<>();

    // The main game loop that puts players in rooms and starts their internal loops
   public static void roomLoop() {


       while (true) {

           // Start the internal action loop of the room that the player is currently in.
           // Ideally, this should never be repeat the same room. The room that the player is currently in should update the player's location before breaking out
           // of its internal loop and back up into this one.
           visitedRooms.add(Manager.player.getLocation());
           Manager.roomHashMap.get(Manager.player.getLocation()).enter();

       }
   }

    public static HashSet<Integer> getVisitedRooms() {
        return visitedRooms;
    }

    public static void setVisitedRooms(HashSet<Integer> visitedRooms) {
        RoomManager.visitedRooms = visitedRooms;
    }
}