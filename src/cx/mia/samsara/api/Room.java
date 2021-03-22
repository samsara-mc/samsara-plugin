package cx.mia.samsara.api;

import cx.mia.samsara.Samsara;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import xyz.derkades.derkutils.bukkit.LocationUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Room implements Listener {

    private final String name;
    private RoomEnterListener roomEnterListener;
    private RoomLeaveListener roomLeaveListener;
    private Location[] corners;
    private final ArrayList<Sound> sounds;
    private final ArrayList<Player> players;

    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     *
     * @param name   name of the room
     * @param c1     first corner of the room (cuboid)
     * @param c2     second corner of the room (cuboid)
     * @param sounds a list of sounds sound associated with this room (these will play when entering the room, see {@link cx.mia.samsara.api.RoomEnterListener})
     */
    public Room(String name, Location c1, Location c2, Sound... sounds) {

        // declare all local variables for the instance

        this.name = name;
        this.corners = sortCorners(c1, c2);
        this.sounds = new ArrayList<>(Arrays.asList(sounds));

        this.players = new ArrayList<>();

        this.roomEnterListener = new RoomEnterListener(this);
        this.roomLeaveListener = new RoomLeaveListener(this);

        // register the Listeners
        getModule().registerListener(roomEnterListener);
        getModule().registerListener(roomLeaveListener);

        getModule().getLogger().debug("Declared room with name: " + getName());
        getModule().getLogger().debug("Room " + getName() + " has corners: " + getCorners()[0].getX() + " " + getCorners()[0].getY() + " " + getCorners()[0].getZ() + " and " + getCorners()[1].getX() + " " + getCorners()[1].getY() + " " + getCorners()[1].getZ());
        getModule().getLogger().debug("Room " + getName() + " has sounds: " + getSounds());
        getModule().getLogger().debug("Room " + getName() + " has listener: " + getRoomEnterListener().toString());

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRoomEnter(RoomEnterEvent event) {
        Player player = event.getPlayer();

        if (event.getRoom().equals(this)) {
            playSounds(player);
            getModule().getLogger().debug(player.getName() + " entered " + event.getRoom().getName() + ", so all its sounds were triggered");
            addPlayer(player);
        } else {
            if (getPlayers().contains(player)) {
                stopSounds(player);
                getModule().getLogger().debug(player.getName() + " entered a room other than " + this.getName() + ", so all its sounds were stopped");
            }
        }

//        if (!event.getRoom().equals(this) && getPlayers().contains(player)) {
//            stopSounds(player);
//            getModule().getLogger().debug(player.getName() + " entered a room other than " + this.getName() + ", so all its sounds were stopped");
//            return;
//        } else {
//            playSounds(player);
//            getModule().getLogger().debug(player.getName() + " entered " + event.getRoom().getName() + ", so all its sounds were triggered");
//            addPlayer(player);
//        }

    }

    @EventHandler
    public void onRoomLeave(RoomLeaveEvent event) {
        Player player = event.getPlayer();

        if (event.getRoom().equals(this)) {
            stopSounds(player);
            getModule().getLogger().debug(player.getName() + " left " + event.getRoom().getName() + ", so all its sounds were stopped");
            removePlayer(player);
        } else {
            if (getPlayers().contains(player)) {
                playSounds(player);
                getModule().getLogger().debug(player.getName() + " left a room other than " + this.getName() + ", but remains in this room, so all its sounds were triggered");
            }
        }

//        if (!event.getRoom().equals(this) && getPlayers().contains(player)) {
//            playSounds(player);
//            getModule().getLogger().debug(player.getName() + " left a room other than " + this.getName() + ", but remains in this room, so all its sounds were triggered");
//            return;
//        } else {
//            stopSounds(player);
//            getModule().getLogger().debug(player.getName() + " left " + event.getRoom().getName() + ", so all its sounds were stopped");
//            removePlayer(player);
//        }
    }

    public void playSounds(Player player) {
        sounds.forEach(sound -> {
            sound.playSound(player);
            getModule().getLogger().debug("Sound " + sound.getSound() + " was triggered by " + player.getName());
        });
    }

    public void stopSounds(Player player) {
        sounds.forEach(sound -> {
            sound.stopSound(player);
            getModule().getLogger().debug("Stopping sound " + sound.getSound() + " for player " + player.getName());
        });
    }

    public boolean playerInsideRoom(Player player) {
        return LocationUtils.isIn3dBounds(player.getLocation(), corners[0], corners[1]);
    }

    private Samsara getModule() {
        return Samsara.getInstance();
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

    public ArrayList<Sound> getSounds() {
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

    public RoomLeaveListener getRoomLeaveListener() {
        return this.roomLeaveListener;
    }

    private void setRoomLeaveListener(RoomLeaveListener roomLeaveListener) {

        RoomLeaveEvent.getHandlerList().unregister(getRoomLeaveListener());

        this.roomLeaveListener = roomLeaveListener;
        getModule().registerListener(getRoomLeaveListener());

    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player player) {
        getPlayers().add(player);
    }

    private void removePlayer(Player player) {
        getPlayers().remove(player);
    }

    public static Location[] sortCorners(Location corner1, Location corner2) {

        return new Location[]{LocationUtils.minCorner(corner1, corner2),
                LocationUtils.maxCorner(corner1, corner2)};

    }
}
