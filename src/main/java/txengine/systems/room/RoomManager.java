package txengine.systems.room;

import txengine.main.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RoomManager {

    private static HashSet<Integer> visitedRooms = new HashSet<>();
    private HashMap<Integer,Room> roomHashMap;

    public RoomManager(HashMap<Integer,Room> roomHashMap) {
        this.roomHashMap = roomHashMap;
    }

    public void roomLoop() {

        // The main game loop that puts players in rooms and starts their internal loops
       while (true) {

           // Start the internal action loop of the room that the player is currently in.
           // Ideally, this should never be repeat the same room. The room that the player is currently in should update the player's location before breaking out
           // of its internal loop and back up into this one.
           visitedRooms.add(Manager.player.getLocation());
           roomHashMap.get(Manager.player.getLocation()).enter();

       }
    }

    public final Room get(int id) {
        return roomHashMap.get(id);
    }

    public final HashSet<Integer> getVisitedRooms() {
        return visitedRooms;
    }

    public void setVisitedRooms(HashSet<Integer> visitedRooms) {
        RoomManager.visitedRooms = visitedRooms;
    }
    }