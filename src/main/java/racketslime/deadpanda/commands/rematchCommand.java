package racketslime.deadpanda.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import racketslime.deadpanda.Main;

public class rematchCommand implements CommandExecutor {
    private Main plugin = Main.getInstance();
    private Commands commands = plugin.commands;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commands.cmd3)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (plugin.gameManager.isStarted) {
                    Bukkit.reload();
                    player.sendMessage("rematch initiated");
                }
            }
        }
        return true;
    }
}
