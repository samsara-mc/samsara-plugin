package cx.mia.samsara.rooms.life;

import cx.mia.samsara.Samsara;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.RoomEnterListener;
import org.bukkit.Location;

public class Bridge extends Room {
    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     *
     * @param name   name of the room
     * @param c1     first corner of the room (cuboid)
     * @param c2     second corner of the room (cuboid)
     * @param sounds a list of sounds sound associated with this room (these will play when entering the room, see {@link RoomEnterListener})
     */
    public Bridge(Samsara module, String name, Location c1, Location c2, String... sounds) {
        super(module, "farm", c1, c2, sounds);
    }



}
