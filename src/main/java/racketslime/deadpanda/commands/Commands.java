package racketslime.deadpanda.commands;

import racketslime.deadpanda.Main;

public class Commands {
    public String cmd1 = "lobby";
    public String cmd2 = "set";
    public String cmd3 = "rematch";
    public String cmd4 = "ball";
    public String cmd6 = "blue";
    public String cmd7 = "orange";
    private Main plugin = Main.getInstance();

    public void onEnable() {
        registerCommands();
    }

    private void registerCommands() {
        this.plugin.getCommand(cmd1).setExecutor(new LobbyCommands());
        this.plugin.getCommand(cmd2).setExecutor(new SetCommands());
        this.plugin.getCommand(cmd4).setExecutor(new SpawnBall());
        this.plugin.getCommand(cmd6).setExecutor(new BlueSpawn());
        this.plugin.getCommand(cmd7).setExecutor(new OrangeSpawn());
        this.plugin.getCommand(cmd3).setExecutor(new rematchCommand());

    }
}
