package me.sailex.rplace.player;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.config.ConfigLoader;
import me.sailex.rplace.scoreboard.ScoreBoardManager;
import me.sailex.rplace.time.Countdown;
import me.sailex.rplace.time.Timer;
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
    private final List<RPlacePlayer> rPlacePlayers;
    private final ConfigLoader configLoader;
    private final FileConfiguration config;

    public RPlacePlayerBuilder(RPlace rPlace, ScoreBoardManager scoreBoardManager) {
        this.rPlace = rPlace;
        this.scoreBoardManager = scoreBoardManager;
        rPlacePlayers = new ArrayList<>();
        configLoader = new ConfigLoader("player_data.yml", rPlace);
        config = configLoader.getFileConfiguration();
    }

    public void buildPlayer(Player player) {
        Map<String, Integer> playerData = getPlayerData(player);

        RPlacePlayer rPlacePlayer = new RPlacePlayer(
                player,
                scoreBoardManager.getScoreboard(player, playerData.get("playedTime"), playerData.get("placedBlocks")),
                new Countdown(rPlace, player, 10),
                new Timer(player, playerData.get("playedTime"))
        );
        rPlacePlayers.add(rPlacePlayer);
    }

    private Map<String, Integer> getPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        Map<String, Integer> playerDataMap = new HashMap<>();
        int playedTime = 0;
        int placedBlocks = 0;

        if (config.getString(uuid) != null) {
            playedTime = config.getInt(uuid + ".playedTime");
            placedBlocks = config.getInt(uuid + ".placedBlocks");
        }
        playerDataMap.put("playedTime", playedTime);
        playerDataMap.put("placedBlocks", placedBlocks);
        return playerDataMap;
    }

    public void savePlayerData(RPlacePlayer placePlayer) {
        String uuid = placePlayer.getPlayer().getUniqueId().toString();
        Map<String, Integer> playerDataMap = new HashMap<>();

        playerDataMap.put("playedTime", placePlayer.getTimer().getTime());
        playerDataMap.put("placedBlocks", placePlayer.getScoreBoard().getPlacedBlocks());

        config.set(uuid, playerDataMap);
        try {
            config.save(configLoader.getDataFile());
        } catch (IOException e) {
            rPlace.getLogger().warning("Could not save playerData of player: " + placePlayer.getPlayer().getName() +  " : " + e);
        }
    }

    public List<RPlacePlayer> getrPlacePlayers() {
        return rPlacePlayers;
    }
}
