package cx.mia.samsara.api;

import cx.mia.samsara.Samsara;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import xyz.derkades.derkutils.bukkit.LocationUtils;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Room implements Listener {

    private final Samsara module;

    private final String name;
    private final RoomLeaveListener roomLeaveListener;
    private Location[] corners;
    private ArrayList<String> sounds;
    private ArrayList<Player> players;

    private RoomEnterListener roomEnterListener;

    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     * @param name          name of the room
     * @param c1       first corner of the room (cuboid)
     * @param c2       second corner of the room (cuboid)
     * @param sounds        a list of sounds sound associated with this room (these will play when entering the room, see {@link cx.mia.samsara.api.RoomEnterListener})
     */
    public Room(Samsara module, String name, Location c1, Location c2, String... sounds) {

        // declare all local variables for the instance
        this.module = module;

        this.name = name;
        this.corners = sortCorners(c1, c2);
        this.sounds = new ArrayList<>(Arrays.asList(sounds));

        this.roomEnterListener = new RoomEnterListener(this);
        this.roomLeaveListener = new RoomLeaveListener(this);

        // register the Listeners
        module.registerListener(roomEnterListener);

        module.getLogger().debug("Declared room with name: ", getName());
        module.getLogger().debug("Room ", getName(), " has corners: ", getCorners());
        module.getLogger().debug("Room ", getName(), " has sounds: ", getSounds());
        module.getLogger().debug("Room ", getName(), " has listener: ", getRoomEnterListener());

    }

//    @EventHandler(priority = EventPriority.LOWEST)
    public void onRoomEnter(RoomEnterEvent event) {
        Player player = event.getPlayer();

        this.players.add(player);

        sounds.forEach(s -> {
            player.playSound(player.getLocation(), s, SoundCategory.VOICE, 1000f, 1f);
        });
    }

//    @EventHandler
    public void onRoomLeave(RoomLeaveEvent event) {


        Player player = event.getPlayer();
        sounds.forEach(player::stopSound);
    }

    private Samsara getModule() {
        return this.module;
    }

    public String getName() {
        return this.name;
    }

    public Location[] getCorners() {
        return this.corners;
    }

    public void setCorners(Location c1, Location c2) {
        this.corners = sortCorners(c1, c2);
    }

    public ArrayList<String> getSounds() {
        return this.sounds;
    }

    public RoomEnterListener getRoomEnterListener() {
        return this.roomEnterListener;
    }

    private void setRoomEnterListener(RoomEnterListener roomEnterListener) {

        RoomEnterEvent.getHandlerList().unregister(getRoomEnterListener());

        this.roomEnterListener = roomEnterListener;
        getModule().registerListener(getRoomEnterListener());

    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public boolean playerInsideRoom(Player player) {
        return LocationUtils.isIn3dBounds(player.getLocation(), corners[0], corners[1]);
    }

    public static Location[] sortCorners(Location corner1, Location corner2) {

        return new Location[]{  LocationUtils.minCorner(corner1, corner2),
                                LocationUtils.maxCorner(corner1, corner2)};

    }
}
