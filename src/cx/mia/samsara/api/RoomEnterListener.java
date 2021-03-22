package cx.mia.samsara.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.derkades.derkutils.bukkit.LocationUtils;

public class RoomEnterListener implements Listener {

    private Room room;

    public RoomEnterListener(Room room) {
        this.room = room;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (room.getPlayers().contains(player)) return;

        if (room.playerInsideRoom(player)) {
            RoomEnterEvent roomEnterEvent = new RoomEnterEvent(player, room);

            room.onRoomEnter(roomEnterEvent);

            Bukkit.getServer().getPluginManager().callEvent(roomEnterEvent);
        }


    }
}
