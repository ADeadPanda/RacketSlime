package racketslime.deadpanda.playerdata;

import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerManager implements Listener {

    private UUID uuid;
    private boolean ingame;
    private int scoreEarned;

    public PlayerManager(UUID uuid, boolean ingame, int scoreEarned) {
        this.uuid = uuid;
        this.ingame = ingame;
        this.scoreEarned = scoreEarned;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isIngame() {
        return ingame;
    }

    public void setIngame(boolean ingame) {
        this.ingame = ingame;
    }

    public int getScoreEarned() {
        return scoreEarned;
    }

    public void setScoreEarned(int scoreEarned) {
        this.scoreEarned = scoreEarned;
    }
}
