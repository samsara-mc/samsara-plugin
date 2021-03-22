package cx.mia.samsara;

import cx.mia.samsara.api.JoinQuitListener;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.Sound;
import cx.mia.samsara.commands.Teleport;
import cx.moda.moda.module.Module;
import cx.mia.samsara.storage.SamsaraStorageHandler;
import cx.moda.moda.module.command.ModuleCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;

import java.util.HashMap;

public class Samsara extends Module<SamsaraStorageHandler> {

    private static Samsara instance;

    public Samsara() {
        instance = this;
    }

    @Override
    public String getName() {
        return "Samsara";
    }

    @Override
    public void onEnable() {

        Bukkit.getOnlinePlayers().forEach(player -> {
            Sound.getLoopers().put(player, new HashMap<>());
        });

        registerListeners(Bukkit.getWorld("worlds/sid"));
        registerCommands();
        registerListener(new JoinQuitListener());

        getLogger().debug("Finished enabling.");

    }

    /**
     * regsiters all rooms
     *
     * @param world the world to look for locations of the rooms in.
     */
    private void registerRooms(World world) {

        registerListener(
                new Room(
                        "home",
                        new Location(world, 700, 63, 542),
                        new Location(world, 714, 78, 551),
                        Sound.LIFE_INFANCY
                )
        );

        registerListener(
                new Room(
                        "farm",
                        new Location(world, 747, 60, 615),
                        new Location(world, 604, 96, 468),
                        Sound.LIFE_INFANCY2
                )
        );
    }

    /**
     * register all commands
     */
    private void registerCommands() {

        CommandExecutor executor = (sender, command, label, args) -> {
            if (!sender.hasPermission("samsara.listworlds")) return false;

            Bukkit.getWorlds().forEach(world -> sender.sendMessage(world.getName()));
            return true;
        };

        new ModuleCommandBuilder("listworlds")
                .withExecutor(executor)
                .withPermission("samsara.listworlds")
                .withAliases("world", "worlds")
                .register(this);

        getLogger().debug("registered listworlds command");

        new ModuleCommandBuilder("teleport")
                .withExecutor(new Teleport(this))
                .withPermission("samsara.teleport")
                .withAliases("tp")
                .register(this);
    }

    /**
     * register all listeners
     */
    private void registerListeners(World world) {
        registerRooms(world);
    }

    public static Samsara getInstance() {
        return instance;
    }
}