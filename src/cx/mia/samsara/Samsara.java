package cx.mia.samsara;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;

import cx.mia.samsara.api.JoinQuitListener;
import cx.mia.samsara.api.Room;
import cx.mia.samsara.api.Sound;
import cx.mia.samsara.commands.Teleport;
import cx.mia.samsara.listeners.DamageListener;
import cx.mia.samsara.listeners.PlayerRoomListener;
import cx.mia.samsara.storage.SamsaraStorageHandler;
import cx.moda.moda.module.Module;
import cx.moda.moda.module.command.ModuleCommandBuilder;

public class Samsara extends Module<SamsaraStorageHandler> {

    private static Samsara instance;

    private static final World WORLD = Bukkit.getWorld("worlds/sid");

    public static final Room[] ROOMS = {
    		new Room(
                    "start room",
                    new Location(WORLD, 273, 172, 84),
                    new Location(WORLD, 176, 221, -12),
                    List.of(Sound.GENESIS_INCEPTION2)
            ),
    		new Room(
                    "life main",
                    new Location(WORLD, 166, 94, 203),
                    new Location(WORLD, 214, 64, 173),
                    List.of(Sound.LIFE)
            ),
    		new Room(
                    "mirror room",
                    new Location(WORLD, 270, 186, 84),
                    new Location(WORLD, 249, 180, 74),
                    List.of(Sound.GENESIS_INCEPTION)
            ),
    		new Room(
                    "falling tube",
                    new Location(WORLD, 222, 253, 34),
                    new Location(WORLD, 224, 120, 36),
                    List.of(Sound.GENESIS_EMERGENCE)
            ),
    		new Room(
                    "walk toward life",
                    new Location(WORLD, 99, 227, 86),
                    new Location(WORLD, 2, 130, -71),
                    List.of(Sound.GENESIS_BIRTH)
            ),
    		 new Room(
                     "life spawn room",
                     new Location(WORLD, 259, 76, 221),
                     new Location(WORLD, 233, 63, 198),
                     List.of(Sound.LIFE_INFANCY)
             ),
    		 new Room(
                     "dark inverted pyramic",
                     new Location(WORLD, 309, 169, -14),
                     new Location(WORLD, 399, 116, 75),
                     List.of(Sound.LIFE_INFANCY2)
             ),
    		 new Room(
                     "think twice",
                     new Location(WORLD, 170, 88, 178),
                     new Location(WORLD, 168, 90, 180),
                     List.of(Sound.DEATH_SHORT)
             ),
    		 new Room(
                     "bridge",
                     new Location(WORLD, 208, 72, 190),
                     new Location(WORLD, 227, 82, 158),
                     List.of(Sound.LIFE_ADULTHOOD)
             ),
    		 new Room(
                     "the pit",
                     new Location(WORLD, 227, 81, 157),
                     new Location(WORLD, 223, 2, 1154),
                     List.of(Sound.LIFE_ELDER)
             ),
    		 new Room(
                     "regeneration",
                     new Location(WORLD, 173, 47, 123),
                     new Location(WORLD, 83, 58, 32),
                     List.of(Sound.REGENERATION)
             ),
    		 new Room(
                     "home",
                     new Location(WORLD, 700, 63, 542),
                     new Location(WORLD, 714, 78, 551),
                     List.of(Sound.LIFE_INFANCY)
             ),
    		 new Room(
                     "farm",
                     new Location(WORLD, 747, 60, 615),
                     new Location(WORLD, 604, 96, 468),
                     List.of(Sound.LIFE_INFANCY2)
             ),
    };

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

        registerListeners();
        registerCommands();
        registerListener(new JoinQuitListener());

        getLogger().debug("Finished enabling.");

    }

    /**
     * register all commands
     */
    private void registerCommands() {

        final CommandExecutor executor = (sender, command, label, args) -> {
            if (!sender.hasPermission("samsara.listworlds")) {
				return false;
			}

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
    private void registerListeners() {
        registerListener(new DamageListener());
        registerListener(new PlayerRoomListener());
        Arrays.stream(ROOMS).forEach(this::registerListener);
    }

    public static Samsara getInstance() {
        return instance;
    }
}