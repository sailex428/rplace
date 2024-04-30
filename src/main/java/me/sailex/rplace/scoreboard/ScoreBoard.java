package me.sailex.rplace.scoreboard;

import me.sailex.rplace.leaderboard.LeaderboardManager;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;

public class ScoreBoard {

    private Scoreboard scoreboard;
    private Map<String, Integer> leaderboard;
    private Objective objective;
    private String playedTime;
    private int placedBlocks;

    public ScoreBoard(Player player, String playedTime, int placedBlocks, LeaderboardManager leaderboardManager) {
        this.placedBlocks = placedBlocks;
        this.playedTime = playedTime;
        this.leaderboard = leaderboardManager.getLeaderboard();
        initializeScoreBoard(player.getUniqueId());
    }

    public void initializeScoreBoard(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return;
        }
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        if (this.scoreboard.getObjective("display") != null) {
            Objects.requireNonNull(this.scoreboard.getObjective("display")).unregister();
        }

        String title = "r/place";
        objective = this.scoreboard.registerNewObjective("display", Criteria.DUMMY, text().content(title).build());

        objective.displayName(text().content("§6§l" + title)
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.BOLD).build());

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        setLine(10);
        setScore("Time played:", 9);
        setupPlayedTime(playedTime);
        setLine(7);
        setScore("Blocks placed:", 6);
        setupPlacedBlocks(placedBlocks);
        setLine(4);
        setupLeaderBoard(leaderboard);

        player.setScoreboard(this.scoreboard);
    }

    private void setScore(String content, int score) {
        Team team = getTeamByScore(score);

        if (team == null) {
            return;
        }

        team.prefix(text().content(content).build());
        showScore(score);
    }

    private Team getTeamByScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if (name == null) {
            return null;
        }

        Team team = scoreboard.getEntryTeam(name.getEntryName());

        if (team != null) {
            return team;
        }

        team = scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());
        return team;
    }

    private EntryName getEntryNameByScore(int score) {
        for (EntryName name : EntryName.values()) {
            if (score == name.getEntry()) {
                return name;
            }
        }
        return null;
    }

    private void showScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if (name == null) {
            return;
        }

        if (objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        objective.getScore(name.getEntryName()).setScore(score);
    }

    public void setupPlayedTime(String playedTime) {
        setScore("§6" + playedTime, 8);
        this.playedTime = playedTime;
    }

    public void setupPlacedBlocks(int placedBlocks) {
        setScore("§6" + placedBlocks, 5);
        this.placedBlocks = placedBlocks;
    }

    public void setupLeaderBoard(Map<String, Integer> leaderboard) {
        setScore("Leaderboard:", 3);
        int index = 2;
        int place = 1;
        for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
            setScore("§6" + place  + ". " + "§f" + entry.getKey() + ", " + "§6" + entry.getValue(), index);
            index--;
            place++;
        }
        this.leaderboard = leaderboard;
    }

    private void setLine(int score) {
        setScore("§6" + "---------------", score);
    }

    public int getPlacedBlocks() {
        return placedBlocks;
    }

}
