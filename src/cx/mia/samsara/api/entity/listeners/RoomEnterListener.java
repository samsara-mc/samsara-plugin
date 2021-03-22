package cx.mia.samsara.api.entity.listeners;

import cx.mia.samsara.Samsara;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.entity.RoomEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RoomEnterListener implements Listener {

    private final Room room;

    public RoomEnterListener(Room room) {
        this.room = room;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!room.getPlayers().contains(player)) {
            if (room.playerInsideRoom(player)) {
                RoomEnterEvent roomEnterEvent = new RoomEnterEvent(getRoom(), player);

                Bukkit.getPluginManager().callEvent(roomEnterEvent);
                Samsara.getInstance().getLogger().debug(player.getName() + " entered room " + getRoom().getName() + " and a RoomEnterEvent was called.");
            }
        }
    }

    public Room getRoom() {
        return room;
    }
}
