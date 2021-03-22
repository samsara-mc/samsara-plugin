package cx.mia.samsara.api.entity.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player || event.getEntity() instanceof ArmorStand) {
            if (!event.getCause().equals(EntityDamageEvent.DamageCause.CUSTOM) && !event.getCause().equals(EntityDamageEvent.DamageCause.SUICIDE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
