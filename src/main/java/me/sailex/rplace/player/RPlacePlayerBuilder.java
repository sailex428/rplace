package me.sailex.rplace.player;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.config.ConfigLoader;
import me.sailex.rplace.scoreboard.ScoreBoardManager;
import me.sailex.rplace.time.Countdown;

import me.sailex.rplace.time.TimerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPlacePlayerBuilder {

    private final RPlace rPlace;
    private final ScoreBoardManager scoreBoardManager;
    private final TimerManager timerManager;
    private final List<RPlacePlayer> rPlacePlayers;
    private final ConfigLoader configLoader;
    private final FileConfiguration config;
    private final String PLACED_BLOCKS = "placedBlocks";
    private final String PLAYED_TIME = "playedTime";

    public RPlacePlayerBuilder(RPlace rPlace, ScoreBoardManager scoreBoardManager, TimerManager timerManager) {
        this.rPlace = rPlace;
        this.scoreBoardManager = scoreBoardManager;
        this.timerManager = timerManager;
        rPlacePlayers = new ArrayList<>();
        configLoader = new ConfigLoader("player_data.yml", rPlace);
        config = configLoader.getFileConfiguration();
    }

    public void buildPlayer(Player player) {
        Map<String, Integer> playerData = getPlayerData(player);

        RPlacePlayer rPlacePlayer = new RPlacePlayer(
                player,
                scoreBoardManager.getScoreboard(player, "0s", playerData.get(PLACED_BLOCKS)),
                new Countdown(rPlace, player),
                timerManager.getTimer(player, playerData.get(PLAYED_TIME))
        );
        rPlacePlayers.add(rPlacePlayer);
    }

    private Map<String, Integer> getPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        Map<String, Integer> playerDataMap = new HashMap<>();
        int playedTime = 0;
        int placedBlocks = 0;

        if (config.getString(uuid) != null) {
            playedTime = config.getInt(uuid + "." + PLAYED_TIME);
            placedBlocks = config.getInt(uuid + "." + PLACED_BLOCKS);
        }
        playerDataMap.put(PLAYED_TIME, playedTime);
        playerDataMap.put(PLACED_BLOCKS, placedBlocks);
        return playerDataMap;
    }

    public void savePlayerData(RPlacePlayer placePlayer) {
        String uuid = placePlayer.getPlayer().getUniqueId().toString();
        Map<String, Integer> playerDataMap = new HashMap<>();

        playerDataMap.put(PLAYED_TIME, placePlayer.getTimer().getTime());
        playerDataMap.put(PLACED_BLOCKS, placePlayer.getScoreBoard().getPlacedBlocks());

        config.set(uuid, playerDataMap);
        try {
            config.save(configLoader.getDataFile());
        } catch (IOException e) {
            rPlace.getLogger().warning("Could not save playerData of player: " + placePlayer.getPlayer().getName() +  " : " + e);
        }
    }

    public List<RPlacePlayer> getRPlacePlayers() {
        return rPlacePlayers;
    }
}
