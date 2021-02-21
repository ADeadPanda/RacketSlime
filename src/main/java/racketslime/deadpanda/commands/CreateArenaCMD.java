package racketslime.deadpanda.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import racketslime.deadpanda.ArenaManager;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.utils.color;

import java.util.ArrayList;
import java.util.UUID;

public class CreateArenaCMD implements CommandExecutor {
    private Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("arena")) {
                if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("create")) {
                        try {
                            String name = args[1];
                            int id = Integer.parseInt(args[2]);
                            boolean activated = Boolean.parseBoolean(args[3]);
                            plugin.arenaManagerHashMap.put(name, new ArenaManager(name, id, false, activated, new ArrayList<UUID>(), player.getLocation()));
                            player.sendMessage(color.Set("&aArena created!"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            player.sendMessage(color.Set("&cError Creating arena!"));
                        }
                    }
                } else {
                    player.sendMessage(color.Set("&C&LInvalid argument Length."));
                    player.sendMessage(color.Set("&CUsage: /arena create <ArenaName> <Id> <Activated(boolean)>."));
                }
            }
        }
        return true;
    }
}
