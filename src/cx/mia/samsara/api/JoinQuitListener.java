package cx.mia.samsara.api;

import cx.mia.samsara.Samsara;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Sound.getLoopers().put(player, new HashMap<>());
        Samsara.getInstance().getLogger().debug(player.getName() + " joined with gamemode " + player.getGameMode());
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            player.teleport(new Location(Bukkit.getWorld("worlds/sid"), 256, 180, 81));
        }
        Samsara.getInstance().getLogger().debug(player.getName() + " was added to the Looper HashMap");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Sound.getLoopers().remove(event.getPlayer());
        Samsara.getInstance().getLogger().debug(event.getPlayer().getName() + " was removed from the Looper HashMap");
    }
}
