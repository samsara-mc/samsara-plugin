package cx.mia.samsara.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import cx.mia.samsara.Samsara;
import cx.mia.samsara.api.Room;

public class PlayerRoomListener implements Listener {

	private static Map<UUID, Room> PRIMARY_ROOM = new HashMap<>();

	@EventHandler
	public void onMove(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		Room newRoom = null; // highest priority room
//		final int newRoomPriority;
		for (int i = 0; i < Samsara.ROOMS.length; i++) {
			final Room room = Samsara.ROOMS[i];
			if (room.isInRoomBounds(player)) {
				newRoom = room;
//				newRoomPriority = i;
				break;
			}
		}

		final Room previousPrimaryRoom = PRIMARY_ROOM.get(player.getUniqueId());

		if (previousPrimaryRoom == newRoom) {
			// Previous primary room is still the highest priority room.
			// or, if both are null, player wasn't in a room and still isn't in a room
			// Nothing to do.
			return;
		}

		if (previousPrimaryRoom == null) {
			// Player wasn't in any room and entered a new room
//			newRoom.addPlayer(player);
			newRoom.onPlayerEnter(player, false);
			PRIMARY_ROOM.put(player.getUniqueId(), newRoom);
			return;
		}

		// Player was in a room

		if (newRoom == null) {
			// Player left room and didn't enter any other rooms
//			previousPrimaryRoom.removePlayer(player);
			previousPrimaryRoom.onPlayerLeave(player, Optional.empty());
			PRIMARY_ROOM.remove(player.getUniqueId());
			return;
		}

		// Player entered a new room, but are they still in the old room bounds?
		if (previousPrimaryRoom.isInRoomBounds(player)) {
			// yes, the player moved to a nested room / subroom
			previousPrimaryRoom.onPlayerLeave(player, Optional.of(newRoom));
//			newRoom.addPlayer(player);
			newRoom.onPlayerEnter(player, false);
			PRIMARY_ROOM.put(player.getUniqueId(), newRoom);
			return;
		}

		previousPrimaryRoom.onPlayerLeave(player, Optional.empty());

		if (newRoom.isInRoomBounds(event.getFrom())) {
			// player was already in this room (just not the primary room)
			// child->parent
			newRoom.onPlayerEnter(player, true);
		} else {
			// two rooms sharing a border
			newRoom.onPlayerEnter(player, false);
		}

		newRoom.onPlayerEnter(player, newRoom.isInRoomBounds(event.getFrom()));

		PRIMARY_ROOM.put(player.getUniqueId(), newRoom);





//		final Player player = event.getPlayer();
//
//		final Room previousPrimaryRoom = PRIMARY_ROOM.get(player.getUniqueId());
//
//		if (previousPrimaryRoom == null) {
//			// player not in any room, simply enter first found room if in bounds
//			for (final Room room : Samsara.ROOMS) {
//				if (room.isInRoomBounds(player)) {
//					room.addPlayer(player);
//					room.onPlayerEnter(player);
//				}
//			}
//			return;
//		}
//
//
//		// player is in room, possibly nested room
//
//		// is player still in this room?
//		if (previousPrimaryRoom.isInRoomBounds(player)) {
//			// yes, nothing to do
//			return;
//		}
//
//		// player left the room (don't call event just yet)
//		previousPrimaryRoom.removePlayer(player);
//
//		// are we still in other rooms?
//		Room otherInBoundsRoom = null;
//		for (final Room room : Samsara.ROOMS) {
//			if (room.isInRoom(player)) {
//				// yes, set this room as primary
//				otherInBoundsRoom = room;
//				break;
//			}
//		}
//
//		if (otherInBoundsRoom == null) {
//			// moved back to a previous room
//			previousPrimaryRoom.onPlayerLeave(player, Optional.empty());
//			otherInBoundsRoom.onPlayerEnter(player, true);
//			return;
//		}

		// the player is not in any rooms.

//		final Deque<Room> roomsLeft = new ArrayDeque<>();
//		for (final Room room : Samsara.ROOMS) {
//			if (room.isInRoom(player) && !room.isInRoomBounds(player)) {
//				roomsLeft.add(room);
//			}
//		}

//		Room newlyEnteredRoom = null;
//
//		for (final Room room : Samsara.ROOMS) {
//			if (!room.isInRoom(player) && room.isInRoomBounds(player)) {
//				newlyEnteredRoom = room;
//				break;
//			}
//		}
//
//		// Leave all previous rooms
//		for (final Room room : Samsara.ROOMS) {
//			if (room == newlyEnteredRoom) {
//				continue;
//			}
//
//			if (room.isInRoom(player)) {
//				// rooms can be nested, a player entering a new room doesn't mean they left the previous room
//				if (!room.isInRoomBounds(player)) {
//					room.removePlayer(player);
//				}
//				room.onPlayerLeave(player, Optional.ofNullable(newlyEnteredRoom));
//
//			}
////				newlyEnteredRoom = Optional.of(room);
////				break;
//		}

//		while (!roomsLeft.isEmpty()) {
//			final Room previousRoom = roomsLeft.pop();
//			if (previousRoom.isInRoom(player)) {
//				previousRoom.removePlayer(player);
//			}
//			previousRoom.onPlayerLeave(player, newlyEnteredRoom);
//		}

		// send enter event for new room after sending leave event for previous room(s)
//		newlyEnteredRoom.ifPresent(room -> {
//			room.addPlayer(player);
//			room.onPlayerEnter(player);
//			PRIMARY_ROOM.put(player.getUniqueId(), room);
//		});
	}

	public static Room getCurrentRoom(final Player player) {
		return PRIMARY_ROOM.get(player.getUniqueId());
	}

}
