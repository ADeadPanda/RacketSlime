package racketslime.deadpanda;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import racketslime.deadpanda.commands.Commands;
import racketslime.deadpanda.commands.CreateArenaCMD;
import racketslime.deadpanda.game.GameManager;
import racketslime.deadpanda.game.GameMechanics;
import racketslime.deadpanda.items.ItemManager;
import racketslime.deadpanda.playerdata.PlayerManager;
import racketslime.deadpanda.playerdata.playerScoreboard;
import racketslime.deadpanda.utils.color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main instance;
    public HashMap<String, ArenaManager> arenaManagerHashMap = new HashMap<>();
    public HashMap<UUID, PlayerManager> playerManager = new HashMap<>();
    public ArrayList<UUID> playersInGame = new ArrayList<>();
    public ArrayList<Player> playersInTeamBlue = new ArrayList<>();
    public ArrayList<Player> playersInTeamOrange = new ArrayList<>();


    public GameMechanics gameMechanics;
    public GameManager gameManager;
    public Commands commands;
    public racketslime.deadpanda.playerdata.playerScoreboard playerScoreboard;

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }

    @Override
    public void onEnable() {
        setInstance(this);
        loadConfig();
        instanceClasses();
        ItemManager.init();
        commands.onEnable();
        gameManager.setupGame();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (playersInTeamOrange.size() < gameManager.playerNeeded / 2) {
                playersInTeamOrange.add(player);
                player.setDisplayName(player.getName());
                Bukkit.broadcastMessage(color.Set("&6" + player.getDisplayName() + "&f has joined team &6Orange!"));
                player.setDisplayName(color.Set("&6" + player.getDisplayName() + "&f"));
                player.setPlayerListName(color.Set("&6" + player.getDisplayName()));
            } else if (playersInTeamBlue.size() < gameManager.playerNeeded / 2) {
                playersInTeamBlue.add(player);
                player.setDisplayName(player.getName());
                Bukkit.broadcastMessage(color.Set("&9" + player.getDisplayName() + "&f has joined team &9Blue!"));
                player.setDisplayName(color.Set("&9" + player.getDisplayName() + "&f"));
                player.setPlayerListName(color.Set("&9" + player.getDisplayName()));
            }
        });

        PluginManager PM = this.getServer().getPluginManager();
        PM.registerEvents(new GameMechanics(), this);
        this.getCommand("arena").setExecutor(new CreateArenaCMD());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(color.Set("&aRacketSlime has been&c disabled"));
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void instanceClasses() {
        gameMechanics = new GameMechanics();
        gameManager = new GameManager();
        playerScoreboard = new playerScoreboard();
        commands = new Commands();
    }
}