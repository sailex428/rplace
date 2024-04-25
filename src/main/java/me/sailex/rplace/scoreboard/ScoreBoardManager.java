package me.sailex.rplace.scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoardManager {

    private final Map<String, ScoreBoard> scoreBoardMap;

    public ScoreBoardManager() {
        scoreBoardMap = new HashMap<>();
    }

    public ScoreBoard getScoreboard(Player player, String playedTime, int placedBlocks) {
        String uuid = player.getUniqueId().toString();

        if (scoreBoardMap.containsKey(uuid)) {
            return scoreBoardMap.get(uuid);
        }
        ScoreBoard scoreBoard = new ScoreBoard(player, playedTime, placedBlocks);
        scoreBoardMap.put(player.getUniqueId().toString(), scoreBoard);
        return scoreBoard;
    }

    public Map<String, ScoreBoard> getScoreBoardMap() {
        return scoreBoardMap;
    }

}
