package cx.mia.samsara;

import cx.mia.samsara.rooms.life.Home;
import cx.moda.moda.module.Module;
import cx.mia.samsara.storage.SamsaraStorageHandler;
import cx.moda.moda.module.command.ModuleCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;

public class Samsara extends Module<SamsaraStorageHandler> {



    @Override
    public String getName() {
        return "Samsara";
    }

    @Override
    public void onEnable() {

        World mainWorld = Bukkit.getWorld("worlds/sid");

        /**
         * debug command for listing world names
         */
        CommandExecutor executor = (sender, command, label, args) -> {
            if (!sender.hasPermission("samsara.listworlds")) return false;

            Bukkit.getWorlds().forEach(world -> {
                sender.sendMessage(world.getName());
            });
            return true;
        };

        new ModuleCommandBuilder("listworlds").withExecutor(executor).withPermission("samsara.listworlds").withAliases("world", "worlds").register(this);

        Home home = new Home(this, new Location(mainWorld, 700, 63, 541), new Location(mainWorld, 714, 78, 551));

    }



}