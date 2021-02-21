package racketslime.deadpanda.playerdata;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.utils.color;

public class playerScoreboard {
    private static ScoreboardManager m;
    private static Scoreboard b;
    private static Objective o;
    private static Score gameMode;
    private static Score time;
    private static Score score;

    private static Main plugin = Main.getInstance();

    public void scoreGame(Player player) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();
        o = b.registerNewObjective("Racket", "Slime");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(color.Set("&a&l&oRacket Slime"));

        if (plugin.gameManager.orangeScore == plugin.gameManager.maxScore || plugin.gameManager.blueScore == plugin.gameManager.maxScore) {
            time = o.getScore(color.Set("&cGame Over"));
            time.setScore(4);
            return;
        } else {
            time = o.getScore(color.Set("&fTime: &a" + plugin.gameManager.gameTime));
            time.setScore(4);
        }
        gameMode = o.getScore(color.Set("&fGame: &aRacketSlime"));
        gameMode.setScore(3);

        score = o.getScore(color.Set("&6Orange Score: &f" + plugin.gameManager.orangeScore));
        score.setScore(2);

        score = o.getScore(color.Set("&9Blue Score: &f" + plugin.gameManager.blueScore));
        score.setScore(1);

        score = o.getScore(color.Set("&7Max Score: &f" + plugin.gameManager.maxScore));
        score.setScore(0);

        player.setScoreboard(b);
    }

    public void scoreLobby(Player player) {
        m = Bukkit.getScoreboardManager();
        b = m.getNewScoreboard();
        o = b.registerNewObjective("Racket ", "Slime");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(color.Set("&a&l&oRacket Slime"));

        time = o.getScore(color.Set("&fTime: &cNot Started"));
        time.setScore(4);

        gameMode = o.getScore(color.Set("&fGame: &aRacketSlime"));
        gameMode.setScore(3);

        gameMode = o.getScore(color.Set("&fMade By: &7ADeadPanda"));
        gameMode.setScore(2);


        player.setScoreboard(b);
    }
}
