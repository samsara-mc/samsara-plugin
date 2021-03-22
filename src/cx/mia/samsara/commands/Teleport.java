package cx.mia.samsara.commands;

import cx.moda.moda.module.Module;
import cx.moda.moda.module.command.ModuleCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class Teleport extends ModuleCommandExecutor implements TabCompleter {

    public Teleport(Module module) {
        super(module);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("samsara.teleport")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Too few arguments");
            return false;
        }

        if (args.length == 1) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "As a console you can only teleport others.");
                return false;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "Could not find target.");
                return false;
            }

            Player source = (Player) sender;

            Player target = Bukkit.getPlayer(args[0]);

            Vector targetVelocity = target.getVelocity();

            source.teleport(target);
            source.setVelocity(targetVelocity);

            source.sendMessage(ChatColor.GREEN + "You were teleported to " + target.getName());
            target.sendMessage(ChatColor.GOLD + source.getName() + " teleported to you.");

        }

        if (args.length == 2) {

            if (Bukkit.getPlayer(args[0]) == null || Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(ChatColor.RED + "Could not find player(s).");
                return false;
            }

            Player source = Bukkit.getPlayer(args[0]);
            Player target = Bukkit.getPlayer(args[1]);

            Vector targetVelocity = target.getVelocity();

            source.teleport(target);
            source.setVelocity(targetVelocity);

            if (!sender.equals(source)) {
                sender.sendMessage(ChatColor.GREEN + "Teleported " + source.getName() + " to " + target.getName() + ".");
            }
            source.sendMessage(ChatColor.GOLD + "You were teleported to " + target.getName() + ".");
            target.sendMessage(ChatColor.GOLD + source.getName() + " was teleported to you.");

        }

        if (args.length == 3) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "As a console you can only teleport others.");
                return false;
            }

            if (    (!isDouble(args[0]) && !args[0].startsWith("~")) ||
                    (!isDouble(args[1]) && !args[1].startsWith("~")) ||
                    (!isDouble(args[2]) && !args[2].startsWith("~"))) {
                sender.sendMessage(ChatColor.RED + "Please provide valid coordinates.");
                return false;
            }

            Player source = (Player) sender;

            Vector targetVelocity = source.getVelocity();

            Location targetLocation = new Location(
                    source.getWorld(),
                    args[0].startsWith("~") ?
                            source.getLocation().getX() + parseDouble(args[0].replace("~", "")) :
                            parseDouble(args[0]),
                    args[1].startsWith("~") ?
                            source.getLocation().getY() + parseDouble(args[1].replace("~", "")) :
                            parseDouble(args[1]),
                    args[2].startsWith("~") ?
                            source.getLocation().getZ() + parseDouble(args[2].replace("~", "")) :
                            parseDouble(args[2]));

            source.teleport(targetLocation);
            source.setVelocity(targetVelocity);

            source.sendMessage(ChatColor.GREEN + "You were teleported to " +
                    targetLocation.getX() + ", " +
                    targetLocation.getY() + ", " +
                    targetLocation.getZ() + ".");

        }

        if (args.length == 4) {

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "Could not find target.");
                return false;
            }

            if (!isDouble(args[1]) || !isDouble(args[2]) || !isDouble(args[3])) {
                sender.sendMessage(ChatColor.RED + "Please provide valid coordinates.");
                return false;
            }

            Player source = Bukkit.getPlayer(args[0]);

            Vector targetVelocity = source.getVelocity();

            Location targetLocation = new Location(
                    source.getWorld(),
                    args[1].startsWith("~") ?
                            source.getLocation().getX() + parseDouble(args[1].replace("~", "")) :
                            parseDouble(args[1]),
                    args[2].startsWith("~") ?
                            source.getLocation().getY() + parseDouble(args[2].replace("~", "")) :
                            parseDouble(args[2]),
                    args[3].startsWith("~") ?
                            source.getLocation().getZ() + parseDouble(args[3].replace("~", "")) :
                            parseDouble(args[3]));

            source.teleport(targetLocation);
            source.setVelocity(targetVelocity);

            if (!sender.equals(source)) {
                sender.sendMessage(ChatColor.GREEN + "Teleported " + source.getName() + " to " +
                        targetLocation.getX() + ", " +
                        targetLocation.getY() + ", " +
                        targetLocation.getZ() + ".");
            }
            source.sendMessage(ChatColor.GOLD + "You were teleported to " + source.getName() + " to " +
                    targetLocation.getX() + ", " +
                    targetLocation.getY() + ", " +
                    targetLocation.getZ() + ".");

        }

        if (args.length == 5) {

            Player source;

            if (args[0].equals("!world")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "As a console you can only teleport others.");
                    return false;
                }

                source = (Player) sender;

            } else {

                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find target.");
                    return false;
                }

                source = Bukkit.getPlayer(args[0]);
            }

            if (Bukkit.getWorld(args[1]) == null) {
                sender.sendMessage(ChatColor.RED + "Could not find the target world.");
                return false;
            }

            World targetWorld = Bukkit.getWorld(args[1]);

            Vector targetVelocity = source.getVelocity();

            Location targetLocation = new Location(
                    source.getWorld(),
                    args[2].startsWith("~") ?
                            source.getLocation().getX() + parseDouble(args[2].replace("~", "")) :
                            parseDouble(args[2]),
                    args[3].startsWith("~") ?
                            source.getLocation().getY() + parseDouble(args[3].replace("~", "")) :
                            parseDouble(args[3]),
                    args[4].startsWith("~") ?
                            source.getLocation().getZ() + parseDouble(args[4].replace("~", "")) :
                            parseDouble(args[4]));

            source.teleport(targetLocation);
            source.setVelocity(targetVelocity);

            source.sendMessage(ChatColor.GREEN + "You were teleported to " +
                    targetLocation.getX() + ", " +
                    targetLocation.getY() + ", " +
                    targetLocation.getZ() + ", " +
                    "in the world " + targetWorld.getName() + ".");

        }

        if (args.length > 5) {
            sender.sendMessage(ChatColor.RED + "Too many arguments.");
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    private double parseDouble(String s) {
        if (s == null || s.equals("")) return 0;
        else return Double.parseDouble(s);
    }

    private boolean isDouble(String s) {

        if (s == null) return false;

        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

}
