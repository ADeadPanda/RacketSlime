package racketslime.deadpanda.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.utils.color;

public class SetCommands implements CommandExecutor {
    private Main plugin = Main.getInstance();
    private Commands commands = plugin.commands;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commands.cmd2)) {
            Player player = (Player) sender;
            if (player.hasPermission("RacketSlime.set.GameTimer")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("GameTime")) {
                        plugin.getConfig().set("GameTime", Integer.parseInt(args[1]));
                        player.sendMessage(color.Set("&aGame time set."));
                    }
                } else {
                    player.sendMessage(color.Set("&cNot enough arguments."));
                }
            }
            if (player.hasPermission("RacketSlime.set.PlayersNeeded")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("Players")) {
                        plugin.getConfig().set("PlayersNeeded", Integer.parseInt(args[1]));
                        player.sendMessage(color.Set("&aAmount of players needed set."));
                    }
                } else {
                    player.sendMessage(color.Set("&cNot enough arguments."));
                }
            }
            if (player.hasPermission("RacketSlime.set.LobbyCountDown")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("LobbyCountDown")) {
                        plugin.getConfig().set("LobbyCountDown", Integer.parseInt(args[1]));
                        player.sendMessage(color.Set("&aLobby count down set."));
                    }
                } else {
                    player.sendMessage(color.Set("&cNot enough arguments."));
                }
            }
            if (player.hasPermission("RacketSlime.set.MaxScore")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("MaxScore")) {
                        plugin.getConfig().set("Score", Integer.parseInt(args[1]));
                        player.sendMessage(color.Set("&aMax score set."));
                    }
                } else {
                    player.sendMessage(color.Set("&cNot enough arguments."));
                }
            }
            plugin.saveConfig();
        }

        return true;
    }
}
