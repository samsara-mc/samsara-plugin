package cx.mia.samsara.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Room implements Listener {

    private String name;
    private Location[] corners;
    private ArrayList<String> sounds;

    private RoomEnterListener roomListener;

    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     * @param name          name of the room
     * @param c1       first corner of the room (cuboid)
     * @param c2       second corner of the room (cuboid)
     * @param sounds        a list of sounds sound associated with this room (these will play when entering the room, see {@link cx.mia.samsara.api.RoomEnterListener})
     */
    public Room(String name, Location c1, Location c2, String... sounds) {

        this.name = name;
        this.corners = sortCorners(c1, c2);
        this.sounds = new ArrayList<>(Arrays.asList(sounds));

        // register movelistener
        this.roomListener = new RoomEnterListener(this);
    }

//    @EventHandler(priority = EventPriority.LOWEST)




    public String getName() {
        return this.name;
    }

    public HashMap<Integer, Location> getCorners() {




    }

    public boolean playerInsideRoom(Player player) {
        if (    player.getLocation().getX() >= corners[0].getX() &&
                player.getLocation().getX() < corners[1].getX() &&

                player.getLocation().getY() >= corners[0].getY() &&
                player.getLocation().getY() < corners[1].getY() &&

                player.getLocation().getZ() >= corners[0].getZ() &&
                player.getLocation().getZ() < corners[1].getZ()
        )
    }

    public ArrayList<String> getSounds() {
        return this.sounds;
    }

    public static Location[] sortCorners(Location corner1, Location corner2) {



        Location newCorner1 = new Location(corner1.getWorld(), newX1, newY1, newZ1);
        Location newCorner2 = new Location(corner2.getWorld(), newX2, newY2, newZ2);

        return new Location[]{newCorner1, newCorner2};

    }

}
