package cx.mia.samsara.api;

import cx.mia.samsara.Samsara;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RoomLeaveListener implements Listener {

    private final Room room;

    public RoomLeaveListener(Room room) {
        this.room = room;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (room.getPlayers().contains(player)) {
            if (!room.playerInsideRoom(player)) {
                RoomLeaveEvent roomLeaveEvent = new RoomLeaveEvent(getRoom(), player);

                Bukkit.getPluginManager().callEvent(roomLeaveEvent);
                Samsara.getInstance().getLogger().debug(player.getName() + " left room " + getRoom().getName() + " and a RoomLeaveEvent was called.");
            }
        }
    }

    public Room getRoom() {
        return room;
    }
}
