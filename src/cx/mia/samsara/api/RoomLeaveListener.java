package cx.mia.samsara.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class RoomLeaveListener {

    private Room room;

    public RoomLeaveListener(Room room) {

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!room.getPlayers().contains(player)) return;

        if (!room.playerInsideRoom(player)) {
            RoomLeaveEvent roomLeaveEvent = new RoomLeaveEvent(player, room);

            room.onRoomLeave(roomLeaveEvent);

            Bukkit.getServer().getPluginManager().callEvent(roomLeaveEvent);
        }

    }
}
