package me.sailex.rplace.leaderboard;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.config.ConfigLoader;
import me.sailex.rplace.player.RPlacePlayer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardManager {

    private final RPlace rPlace;
    private Map<String, Integer> leaderboard;
    private final Map<String, Integer> leaderboardData;
    private final FileConfiguration config;

    public LeaderboardManager(RPlace rPlace, ConfigLoader configLoader) {
        this.rPlace = rPlace;
        this.config = configLoader.getFileConfiguration();
        this.leaderboard = new HashMap<>();
        this.leaderboardData = new HashMap<>();
        initializeLeaderboard();
    }

    private void initializeLeaderboard() {
        for (String uuid : config.getKeys(false)) {
            String playerName = config.getString(uuid + ".playerName");
            int placedBlocks = config.getInt(uuid + ".placedBlocks");
            if (playerName != null) {
                leaderboardData.put(playerName, placedBlocks);
            }
        }
        createLeaderboardFromData();
    }

    private void createLeaderboardFromData() {
        this.leaderboard = leaderboardData.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void updateLeaderboard() {
        BukkitRunnable bukkitRunnable  = new BukkitRunnable() {
            public void run() {
                for (RPlacePlayer placePlayer : rPlace.getrPlacePlayerBuilder().getRPlacePlayers()) {
                    int placedBlocks = placePlayer.getScoreBoard().getPlacedBlocks();
                    leaderboardData.put(placePlayer.getPlayer().getName(), placedBlocks);
                    createLeaderboardFromData();
                }
                for (RPlacePlayer placePlayer : rPlace.getrPlacePlayerBuilder().getRPlacePlayers()) {
                    placePlayer.getScoreBoard().setupLeaderBoard(leaderboard);
                }
            }
        };
        bukkitRunnable.runTaskTimer(rPlace, 0, 400);
    }

    public Map<String, Integer> getLeaderboard() {
        return leaderboard;
    }

}
