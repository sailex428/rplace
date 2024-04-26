package me.sailex.rplace.scoreboard;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;

public class ScoreBoard {

    private Scoreboard scoreboard;
    private final Player player;
    private Objective objective;
    private String playedTime;
    private int placedBlocks;

    public ScoreBoard(Player player, String playedTime, int placedBlocks) {
        this.player = player;
        this.placedBlocks = placedBlocks;
        this.playedTime = playedTime;
        setup();
    }

    public void setup() {
        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        this.scoreboard = player.getScoreboard();

        if (this.scoreboard.getObjective("display") != null) {
            Objects.requireNonNull(this.scoreboard.getObjective("display")).unregister();
        }

        String title = "r/place";
        objective = this.scoreboard.registerNewObjective("display", Criteria.DUMMY, text().content(title).build());

        objective.displayName(text().content("§6§l" + title)
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.BOLD).build());

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        setScore("§6" + "-------------", 6);

        setScore("Time played:", 5);

        setPlayedTime(playedTime);

        setScore("§6" + "-------------", 3);

        setScore("Blocks placed:", 2);

        setPlacedBlocks(placedBlocks);

        setScore("§6" + "-------------", 0);

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
        setScore("§6" + playedTime, 4);
        this.playedTime = playedTime;
    }

    public void setPlacedBlocks(int placedBlocks) {
        setScore("§6" + placedBlocks, 1);
        this.placedBlocks = placedBlocks;
    }

    public int getPlacedBlocks() {
        return placedBlocks;
    }

}
