package mckeken.room;

import java.util.ArrayList;

import mckeken.main.Manager;
import mckeken.player.Player;
import mckeken.room.Room;

public class RoomManager {

    // The main game loop that puts players in rooms and starts their internal loops
   public static void roomLoop() {
       while (true) {

           // Start the internal action loop of the room that the player is currently in.
           // Ideally, this should never be repeat the same room. The room that the player is currently in should update the player's location before breaking out
           // of its internal loop and back up into this one.
           Manager.roomList.get(Manager.player.getLocation()).enter();

       }
   }


}