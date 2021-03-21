package cx.mia.samsara.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RoomEnterListener implements Listener {

    private Room room;

    public RoomEnterListener(Room room) {
        this.room = room;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRoomEnter(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();



    }
}
