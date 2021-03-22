package cx.mia.samsara.api.entity;

import cx.mia.samsara.Samsara;
import cx.mia.samsara.api.Room;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RoomLeaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Room room;
    private final Player player;

    public RoomLeaveEvent(Room room, Player player) {
        this.room = room;
        this.player = player;

        Samsara.getInstance().getLogger().debug("RoomLeaveEvent called in room " + getRoom().getName() + " by " + getPlayer().getName());
    }

    public Room getRoom() {
        return this.room;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
