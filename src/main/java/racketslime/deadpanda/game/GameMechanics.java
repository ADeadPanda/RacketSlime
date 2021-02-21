package racketslime.deadpanda.game;

import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.mobs.SlimeBall;
import racketslime.deadpanda.playerdata.PlayerManager;
import racketslime.deadpanda.utils.color;

import java.util.UUID;

public class GameMechanics implements Listener {

    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!plugin.gameManager.isStarted()) {
            if (plugin.playersInTeamOrange.size() < plugin.gameManager.playerNeeded / 2) {
                plugin.playersInTeamOrange.add(player);
                player.setDisplayName(player.getName());
                Bukkit.broadcastMessage(color.Set("&6" + player.getDisplayName() + "&f has joined team &6Orange!"));
                player.setDisplayName(color.Set("&6" + player.getDisplayName() + "&f"));
                player.setPlayerListName(color.Set("&6" + player.getDisplayName()));
            } else if (plugin.playersInTeamBlue.size() < plugin.gameManager.playerNeeded / 2) {
                plugin.playersInTeamBlue.add(player);
                player.setDisplayName(player.getName());
                Bukkit.broadcastMessage(color.Set("&9" + player.getDisplayName() + "&f has joined team &9Blue!"));
                player.setDisplayName(color.Set("&9" + player.getDisplayName() + "&f"));
                player.setPlayerListName(color.Set("&9" + player.getDisplayName()));
            }
            plugin.playerManager.put(uuid, new PlayerManager(uuid, false, 0));
            plugin.playersInGame.add(uuid);
            plugin.gameManager.lobbyWait(player);
            Bukkit.getOnlinePlayers().forEach(online -> {
                plugin.playerScoreboard.scoreLobby(online);
                if (!plugin.playersInTeamBlue.contains(online) || !plugin.playersInTeamOrange.contains(online)) {
                    if (plugin.playersInTeamOrange.size() < plugin.gameManager.playerNeeded / 2) {
                        plugin.playersInTeamOrange.add(online);
                        online.setDisplayName(online.getName());
                        Bukkit.broadcastMessage(color.Set("&6" + online.getDisplayName() + "&f has joined team &6Orange!"));
                        online.setDisplayName(color.Set("&6" + online.getDisplayName() + "&f"));
                        online.setPlayerListName(color.Set("&6" + online.getDisplayName()));
                    } else if (plugin.playersInTeamBlue.size() < plugin.gameManager.playerNeeded / 2) {
                        plugin.playersInTeamBlue.add(online);
                        online.setDisplayName(online.getName());
                        Bukkit.broadcastMessage(color.Set("&9" + online.getDisplayName() + "&f has joined team &9Blue!"));
                        online.setDisplayName(color.Set("&9" + online.getDisplayName() + "&f"));
                        online.setPlayerListName(color.Set("&9" + online.getDisplayName()));
                    }
                }
            });

            if (plugin.gameManager.lobbySpawn != null) {
                player.teleport(plugin.gameManager.lobbySpawn);
            }
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        event.setQuitMessage(color.Set("&c" + player.getName() + " &7RageQuit!"));

        if (plugin.playerManager.containsKey(uuid) && plugin.playersInGame.contains(uuid) && plugin.playersInTeamOrange.contains(player) || plugin.playersInTeamBlue.contains(player)) {
            plugin.playerManager.remove(uuid);
            plugin.playersInGame.remove(uuid);
            plugin.playersInTeamBlue.remove(player);
            plugin.playersInTeamOrange.remove(player);
            plugin.gameManager.gameStop();

        }

        Bukkit.getOnlinePlayers().forEach(online -> {
            plugin.playersInTeamOrange.remove(online);
            plugin.playersInTeamBlue.remove(online);
        });
    }

    @EventHandler
    public void onGoal(EntityDamageByEntityEvent event) {
        Entity ent = event.getEntity();
        Entity player = event.getDamager();
        if (ent.getName().contains("SlimeBall")) {
            event.setDamage(0);
            if (ent.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.LAPIS_BLOCK) {
                if (plugin.playersInTeamOrange.contains(player)) {
                    plugin.gameManager.setOrangeScore();
                    Bukkit.broadcastMessage(color.Set("&6Orange &fteam got a GOAL!"));
                    ent.teleport(plugin.gameManager.ballSpawn);
                    event.setCancelled(true);
                    Bukkit.getOnlinePlayers().forEach(players -> {
                        players.playSound(players.getLocation(), Sound.BLOCK_BELL_USE, 1, 0);
                        if (plugin.playersInTeamBlue.contains(players)) {
                            players.teleport(plugin.gameManager.blueSpawn);
                        }
                        if (plugin.playersInTeamOrange.contains(players)) {
                            players.teleport(plugin.gameManager.orangeSpawn);
                        }
                    });


                }
            }
            if (ent.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK) {
                if (plugin.playersInTeamBlue.contains(player)) {
                    plugin.gameManager.setBlueScore();
                    Bukkit.broadcastMessage(color.Set("&9Blue &fteam got a GOAL!"));
                    event.setCancelled(true);
                    ent.teleport(plugin.gameManager.ballSpawn);
                    Bukkit.getOnlinePlayers().forEach(players -> {
                        players.playSound(players.getLocation(), Sound.BLOCK_BELL_USE, 1, 0);
                        if (plugin.playersInTeamOrange.contains(players)) {
                            players.teleport(plugin.gameManager.orangeSpawn);
                        }
                        if (plugin.playersInTeamBlue.contains(players)) {
                            players.teleport(plugin.gameManager.blueSpawn);
                        }
                    });
                }
            }
        }
    }

    public void onSpawnBall(Location location) {
        SlimeBall ball = new SlimeBall(plugin.gameManager.ballSpawn);
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        world.addEntity(ball);
        ball.setPosition(plugin.gameManager.ballSpawn.getX(), plugin.gameManager.ballSpawn.getY(), plugin.gameManager.ballSpawn.getZ());
    }

    @EventHandler
    public void setMaxPlayers(ServerListPingEvent event) {
        event.setMaxPlayers(plugin.gameManager.playerNeeded);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().setBedSpawnLocation(plugin.gameManager.lobbySpawn, true);
        event.setDeathMessage("");
    }
}

