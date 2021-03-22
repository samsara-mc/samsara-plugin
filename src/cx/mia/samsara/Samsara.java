package cx.mia.samsara;

import cx.mia.samsara.api.JoinQuitListener;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.Sound;
import cx.moda.moda.module.Module;
import cx.mia.samsara.storage.SamsaraStorageHandler;
import cx.moda.moda.module.command.ModuleCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;

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

        // register all rooms
        registerRooms(Bukkit.getWorld("worlds/sid"));
        registerListener(new JoinQuitListener());

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

    public static Samsara getInstance() {
        return instance;
    }
}