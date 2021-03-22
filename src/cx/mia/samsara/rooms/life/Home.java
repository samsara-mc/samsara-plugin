package cx.mia.samsara.rooms.life;

import cx.mia.samsara.Samsara;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.RoomEnterListener;
import org.bukkit.Location;

public class Home extends Room {
    /**
     * A Room is a defined space within a minecraft world that will play sounds while the player resides within them.
     *
     * @param c1     first corner of the room (cuboid)
     * @param c2     second corner of the room (cuboid)
     */
    public Home(Samsara module, Location c1, Location c2) {
        super(module, "home", c1, c2, "samsara:life.infancy");
    }

}
