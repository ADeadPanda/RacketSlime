package racketslime.deadpanda;

public class ArenaMethods {
    private Main plugin = Main.getInstance();

    public void onDisableSetupArenas() {
        for (ArenaManager arenas : plugin.arenaManagerHashMap.values()) {
            plugin.getConfig().set(arenas.getName() + ".lobbyspawn", arenas.getLobbySpawn());
            plugin.getConfig().set(arenas.getName() + ".id", arenas.getId());
            plugin.saveConfig();
        }
    }
}
