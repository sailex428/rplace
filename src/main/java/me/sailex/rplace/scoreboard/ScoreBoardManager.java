package me.sailex.rplace.scoreboard;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.config.ConfigLoader;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScoreBoardManager {

    private final RPlace rPlace;
    private final FileConfiguration config;
    private final ConfigLoader configLoader;
    private final Map<String, ScoreBoard> scoreBoardMap;
    public ScoreBoardManager(RPlace rPlace) {
        this.rPlace = rPlace;
        configLoader = new ConfigLoader("player_data.yml", rPlace);
        config = configLoader.getFileConfiguration();
        scoreBoardMap = new HashMap<>();
    }

    public void getScoreboard(Player player) {
        ScoreBoard scoreBoard;
        Map<String, String> playerValues = new HashMap<>();

        ConfigurationSection playerSection = config.getConfigurationSection(player.getUniqueId().toString());
        if (playerSection != null) {
            for (String key : playerSection.getKeys(false)) {
                String value = playerSection.getString(key);
                playerValues.put(key, value);
            }
            scoreBoard = new ScoreBoard(
                    player,
                    playerValues.get("playedTime"),
                    Integer.parseInt(playerValues.get("placedBlocks"))
            );
        } else {
            scoreBoard = new ScoreBoard(player, "0", 0);
        }
        scoreBoardMap.put(player.getUniqueId().toString(), scoreBoard);
    }

    public void saveScoreBoardOfPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        ScoreBoard scoreBoard = scoreBoardMap.get(uuid);

        Map<String, String> scoreBoardMap = new HashMap<>();
        scoreBoardMap.put("placedBlocks", String.valueOf(scoreBoard.getPlacedBlocks()));
        scoreBoardMap.put("playedTime", scoreBoard.getPlayedTime());

        config.set(uuid, scoreBoardMap);
        try {
            config.save(configLoader.getDataFile());
        } catch (IOException e) {
            rPlace.getLogger().warning("Could not save playerData of player: " + player.getName() +  " : " + e);
        }
    }

    public Map<String, ScoreBoard> getScoreBoardMap() {
        return scoreBoardMap;
    }

}
