package me.sailex.rplace.scoreboard;

import me.sailex.rplace.config.LeaderBoard;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;

public class ScoreBoard {

    private Scoreboard scoreboard;
    private LeaderBoard leaderBoard;
    private Objective objective;
    private String playedTime;
    private int placedBlocks;

    public ScoreBoard(Player player, String playedTime, int placedBlocks, LeaderBoard leaderBoard) {
        this.placedBlocks = placedBlocks;
        this.playedTime = playedTime;
        this.leaderBoard = leaderBoard;
        setup(player.getUniqueId());
    }

    public void setup(UUID uuid) {
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

        setPlayedTime(playedTime);

        setLine(7);

        setScore("Blocks placed:", 6);

        setPlacedBlocks(placedBlocks);

        setLine(4);

        setScore("Leaderboard:", 3);

        setScore("1. ", 2);

        setScore("2. ", 1);

        setScore("3. ", 0);

        player.setScoreboard(scoreboard);
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

    public void setPlayedTime(String playedTime) {
        setScore("§6" + playedTime, 8);
        this.playedTime = playedTime;
    }

    public void setPlacedBlocks(int placedBlocks) {
        setScore("§6" + placedBlocks, 5);
        this.placedBlocks = placedBlocks;
    }

    private void setLine(int score) {
        setScore("§6" + "-------------", score);
    }

    public int getPlacedBlocks() {
        return placedBlocks;
    }

}
