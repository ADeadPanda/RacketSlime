package racketslime.deadpanda.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import racketslime.deadpanda.Main;

import java.util.UUID;

public class rematchCommand implements CommandExecutor {
    private final Main plugin = Main.getInstance();
    private final Commands commands = plugin.commands;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commands.cmd3)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID uuid = player.getUniqueId();
                if (!plugin.gameManager.isStarted()) {
                    plugin.gameManager.resetGame();
                    plugin.gameManager.setupGame();
                    plugin.gameManager.cleanUpBall();

                }
            }
        }
        return true;
    }
}

