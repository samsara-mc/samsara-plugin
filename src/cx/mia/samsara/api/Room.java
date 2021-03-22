package cx.mia.samsara.api;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import cx.mia.samsara.Samsara;
import xyz.derkades.derkutils.bukkit.LocationUtils;

public class Room implements Listener {

    private final String name;
    private Location[] corners;
    private final List<Sound> sounds;
//    private final Set<UUID> playersInRoom = new HashSet<>();

    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     *
     * @param name   name of the room
     * @param c1     first corner of the room (cuboid)
     * @param c2     second corner of the room (cuboid)
     * @param sounds a list of sounds sound associated with this room (these will play when entering the room, see {@link RoomEnterListener})
     */
    public Room(final String name, final Location c1, final Location c2, final List<Sound> sounds) {
        this.name = name;
        this.corners = sortCorners(c1, c2);
        this.sounds = sounds;

        getModule().getLogger().debug("Declared room with name: " + getName());
        getModule().getLogger().debug("Room " + getName() + " has corners: " + getCorners()[0].getX() + " " + getCorners()[0].getY() + " " + getCorners()[0].getZ() + " and " + getCorners()[1].getX() + " " + getCorners()[1].getY() + " " + getCorners()[1].getZ());
        getModule().getLogger().debug("Room " + getName() + " has sounds: " + getSounds());
    }

	public void onPlayerEnter(final Player player, final boolean wasAlreadyInBounds) {
		playSounds(player);
		getModule().getLogger().debug(player.getName() + " entered " + this.getName() + ", so all its sounds were triggered");
	}

    public void onPlayerLeave(final Player player, final Optional<Room> newSubRoom) {
    	stopSounds(player);
    	getModule().getLogger().debug(player.getName() + " entered a room other than " + this.getName() + ", so all its sounds were stopped");

    	if (newSubRoom.isPresent()) {
    		// doe iets als de speler nog steeds in deze kamer is en naar een subroom is gegaan
    	} else {
    		// doe iets als de speler uit deze kamer is gegaan zonder naar een subroom te gaan
    	}
    }

    public void playSounds(final Player player) {
        this.sounds.forEach(sound -> {
            sound.playSound(player);
            getModule().getLogger().debug("Sound " + sound.getSound() + " was triggered by " + player.getName());
        });
    }

    public void stopSounds(final Player player) {
        this.sounds.forEach(sound -> {
            sound.stopSound(player);
            getModule().getLogger().debug("Stopping sound " + sound.getSound() + " for player " + player.getName());
        });
    }

    public boolean isInRoomBounds(final Player player) {
       return isInRoomBounds(player.getLocation());
    }

    public boolean isInRoomBounds(final Location location) {
        return LocationUtils.isIn3dBounds(location, this.corners[0], this.corners[1]);
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

    public void setCorners(final Location c1, final Location c2) {
        this.corners = sortCorners(c1, c2);
    }

    public List<Sound> getSounds() {
        return this.sounds;
    }

//    public boolean isInRoom(final Player player) {
//    	return this.playersInRoom.contains(player.getUniqueId());
//    }
//
//    public void addPlayer(final Player player) {
//        this.playersInRoom.add(player.getUniqueId());
//    }
//
//    public void removePlayer(final Player player) {
//    	this.playersInRoom.remove(player.getUniqueId());
//    }

    public static Location[] sortCorners(final Location corner1, final Location corner2) {

        return new Location[]{LocationUtils.minCorner(corner1, corner2),
                LocationUtils.maxCorner(corner1, corner2)};

    }
}
