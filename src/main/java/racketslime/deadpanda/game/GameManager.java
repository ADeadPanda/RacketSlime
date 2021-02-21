package racketslime.deadpanda.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.playerdata.PlayerManager;
import racketslime.deadpanda.utils.color;

import java.util.UUID;

import static racketslime.deadpanda.items.ItemManager.pole;

public class GameManager implements Listener {

    public int gameTime;
    public int playerNeeded;
    public int lobbyCountdown;
    public boolean isStarted;
    public int orangeScore = 0;
    public int blueScore = 0;
    public int maxScore = 5;
    public Location lobbySpawn;
    public Location ballSpawn;
    public Location blueSpawn;
    public Location orangeSpawn;
    private Main plugin = Main.getInstance();

    public void setupGame() {
        if (plugin.getConfig().contains("GameTimer")) {
            gameTime = plugin.getConfig().getInt("GameTimer");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&7GameTimer Found"));
        }
        if (plugin.getConfig().contains("Score")) {
            maxScore = plugin.getConfig().getInt("Score");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&7Max Score Found"));
        }
        if (plugin.getConfig().contains("PlayersNeeded")) {
            playerNeeded = plugin.getConfig().getInt("PlayersNeeded");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&7PlayersNeeded Found"));
        }
        if (plugin.getConfig().contains("LobbyCountDown")) {
            lobbyCountdown = plugin.getConfig().getInt("LobbyCountDown");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&7LobbyCountDown Found"));
        }
        if (plugin.getConfig().contains("BlueSpawn")) {
            blueSpawn = (Location) plugin.getConfig().get("BlueSpawn");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&9Blue Spawn Located"));
        }
        if (plugin.getConfig().contains("OrangeSpawn")) {
            orangeSpawn = (Location) plugin.getConfig().get("OrangeSpawn");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&6OrangeSpawn Spawn Located"));
        }

        if (plugin.getConfig().contains("LobbySpawn")) {
            lobbySpawn = (Location) plugin.getConfig().get("LobbySpawn");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&aLobby Spawn Located"));
        }

        if (plugin.getConfig().contains("BallSpawn")) {
            ballSpawn = (Location) plugin.getConfig().get("BallSpawn");
            plugin.getServer().getConsoleSender().sendMessage(color.Set("&aBall Spawn Located"));
        }

        playerCheck(Bukkit.getOnlinePlayers().size());
        for (Player online : Bukkit.getOnlinePlayers()) {
            UUID uuid = online.getUniqueId();
            plugin.playersInGame.add(uuid);
            plugin.playerManager.put(uuid, new PlayerManager(uuid, false, 0));
            lobbyWait(online);
            online.setFoodLevel(20);
            online.setHealth(20);
            plugin.playerScoreboard.scoreLobby(online);
            online.teleport(lobbySpawn);
            online.setGameMode(GameMode.ADVENTURE);
        }
    }

    public void lobbyWait(Player player) {
        int online = Bukkit.getOnlinePlayers().size();
        player.sendMessage(color.Set("&eThere are &F" + online + "&e players online &f" + online + " &e/ &c" + playerNeeded + "&e players needed to start"));
        playerCheck(online);
    }

    public void gameStart() {
        isStarted = true;
        plugin.gameMechanics.onSpawnBall(ballSpawn);
        Bukkit.getOnlinePlayers().forEach(player -> {
            plugin.playerScoreboard.scoreGame(player);
            player.setGameMode(GameMode.ADVENTURE);
            gameCountDown(player);
            player.setWalkSpeed(.5f);
            player.getInventory().clear();
            player.setInvulnerable(true);
            player.getInventory().addItem(pole);
            if (plugin.playersInTeamBlue.contains(player)) {
                player.teleport(blueSpawn);
            }
            if (plugin.playersInTeamOrange.contains(player)) {
                player.teleport(orangeSpawn);
            }
        });
    }

    public void gameStop() {
        Bukkit.getOnlinePlayers().forEach(online -> {
            online.getLocation().getWorld().getNearbyEntities(ballSpawn, 100, 100, 100).forEach(entity -> {
                if (entity.getName().contains("SlimeBall")) {
                    entity.remove();
                    entity.isDead();
                }
            });
            online.setWalkSpeed(.2f);
            online.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            online.getInventory().clear();
            online.setInvulnerable(false);
            online.setGameMode(GameMode.ADVENTURE);
            plugin.playersInGame.clear();
            plugin.playersInTeamOrange.clear();
            plugin.playersInTeamBlue.clear();
            plugin.playerManager.clear();
            online.setPlayerListName(color.Set("&f" + online.getName()));
            online.setDisplayName(color.Set("&f" + online.getName()));
            if (lobbySpawn != null) {
                online.teleport(lobbySpawn);
            }
        });
        if (blueScore == maxScore) {
            Bukkit.broadcastMessage(color.Set("&fThe winner is: &9Blue &fteam!"));
        }
        if (orangeScore == maxScore) {
            Bukkit.broadcastMessage(color.Set("&fThe winner is: &6Orange &fteam!"));
        }
        Bukkit.broadcastMessage(color.Set("&aGood Game!"));
        Bukkit.broadcastMessage(color.Set("&7The score was &f" + orangeScore + " &6Orange&7 and&f " + blueScore + " &9Blue&7!"));


    }


    public boolean playerCheck(int online) {
        if (online >= playerNeeded) {
            if (!isStarted) {
                lobbyCountdown();
                setStarted(true);
            }
            return true;
        } else {
            return false;
        }
    }


    public void lobbyCountdown() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (lobbyCountdown > 0) {
                    playerCheck(plugin.playersInGame.size());
                    if (playerCheck(plugin.playersInGame.size())) {
                        lobbyCountdown--;
                        Bukkit.getServer().broadcastMessage(color.Set("&eThe game will start in &f" + lobbyCountdown + "&e seconds"));
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        }
                    } else {
                        Bukkit.broadcastMessage(color.Set("&ePlayer(s) left. We need &f" + playerNeeded + " &eplayers online for the game to start!"));
                        this.cancel();
                        lobbyCountdown = plugin.getConfig().getInt("LobbyCountDown");
                    }
                } else {
                    this.cancel();
                    gameStart();
                    Bukkit.getServer().broadcastMessage(color.Set("&aGood Luck!"));
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void gameCountDown(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (gameTime > 0) {
                    gameTime--;
                    plugin.playerScoreboard.scoreGame(player);
                } else if (plugin.playersInGame.size() == playerNeeded) {
                    gameStop();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }


    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public int setOrangeScore() {
        orangeScore++;
        if (orangeScore == maxScore) {
            gameStop();
        }
        return orangeScore;
    }

    public int setBlueScore() {
        blueScore++;
        if (blueScore == maxScore) {
            gameStop();
        }
        return blueScore;
    }

}