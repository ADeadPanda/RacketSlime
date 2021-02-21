package racketslime.deadpanda.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.utils.color;

public class SpawnBall implements CommandExecutor {

    private Main plugin = Main.getInstance();
    private Commands commands = plugin.commands;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commands.cmd4)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location location = player.getLocation();
                if (player.hasPermission("RacketSlime.set.BallSpawn")) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("spawn")) {
                            plugin.getConfig().set("BallSpawn", location);
                            plugin.saveConfig();
                            player.sendMessage(color.Set("&aConfig set for Ball Spawn."));
                        }

                    } else {
                        player.sendMessage(color.Set("&cNot enough arguments."));
                    }
                }
            } else {
                sender.sendMessage(color.Set("&cOnly PLAYERS can send this command"));
            }
        }
        return true;
    }
}
