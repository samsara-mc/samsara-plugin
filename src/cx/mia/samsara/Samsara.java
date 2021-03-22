package cx.mia.samsara;

import cx.mia.samsara.api.entity.listeners.JoinQuitListener;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.Sound;
import cx.mia.samsara.commands.Teleport;
import cx.mia.samsara.api.entity.listeners.DamageListener;
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
                        "start room",
                        new Location(world, 273, 172, 84),
                        new Location(world, 176, 221, -12),
                        Sound.GENESIS_INCEPTION2
                )
        );

        registerListener(
                new Room(
                        "life main",
                        new Location(world, 166, 94, 203),
                        new Location(world, 214, 64, 173),
                        Sound.LIFE
                )
        );

        registerListener(
                new Room(
                        "mirror room",
                        new Location(world, 270, 186, 84),
                        new Location(world, 249, 180, 74),
                        Sound.GENESIS_INCEPTION
                )
        );

        registerListener(
                new Room(
                        "falling tube",
                        new Location(world, 222, 253, 34),
                        new Location(world, 224, 120, 36),
                        Sound.GENESIS_EMERGENCE
                )
        );

        registerListener(
                new Room(
                        "walk toward life",
                        new Location(world, 99, 227, 86),
                        new Location(world, 2, 130, -71),
                        Sound.GENESIS_BIRTH
                )
        );

        registerListener(
                new Room(
                        "life spawn room",
                        new Location(world, 259, 76, 221),
                        new Location(world, 233, 63, 198),
                        Sound.LIFE_INFANCY
                )
        );

        registerListener(
                new Room(
                        "dark inverted pyramic",
                        new Location(world, 309, 169, -14),
                        new Location(world, 399, 116, 75),
                        Sound.LIFE_INFANCY2
                )
        );

        registerListener(
                new Room(
                        "think twice",
                        new Location(world, 170, 88, 178),
                        new Location(world, 168, 90, 180),
                        Sound.DEATH_SHORT
                )
        );

        registerListener(
                new Room(
                        "bridge",
                        new Location(world, 208, 72, 190),
                        new Location(world, 227, 82, 158),
                        Sound.LIFE_ADULTHOOD
                )
        );

        registerListener(
                new Room(
                        "the pit",
                        new Location(world, 227, 81, 157),
                        new Location(world, 223, 2, 1154),
                        Sound.LIFE_ELDER
                )
        );

        registerListener(
                new Room(
                        "regeneration",
                        new Location(world, 173, 47, 123),
                        new Location(world, 83, 58, 32),
                        Sound.REGENERATION
                )
        );

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
        registerListener(new DamageListener());
        registerRooms(world);
    }

    public static Samsara getInstance() {
        return instance;
    }
}