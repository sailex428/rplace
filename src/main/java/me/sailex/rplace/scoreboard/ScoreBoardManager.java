package me.sailex.rplace.scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoardManager {

    private final Map<String, ScoreBoard> scoreBoardMap;

    public ScoreBoardManager() {
        scoreBoardMap = new HashMap<>();
    }

    public ScoreBoard getScoreboard(Player player, int playedTime, int placedBlocks) {
        String uuid = player.getUniqueId().toString();

        if (scoreBoardMap.containsKey(player.getUniqueId().toString())) {
            return scoreBoardMap.get(uuid);
        }
        ScoreBoard scoreBoard = new ScoreBoard(player, playedTime, placedBlocks);
        scoreBoardMap.put(player.getUniqueId().toString(), scoreBoard);
        return scoreBoard;
    }

}
