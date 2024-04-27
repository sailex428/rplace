package me.sailex.rplace.scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardManager {

    private final Map<UUID, ScoreBoard> scoreBoardMap;

    public ScoreBoardManager() {
        scoreBoardMap = new HashMap<>();
    }

    public ScoreBoard getScoreboard(Player player, String playedTime, int placedBlocks) {
        UUID uuid = player.getUniqueId();

        if (scoreBoardMap.containsKey(uuid)) {
            ScoreBoard scoreBoard = scoreBoardMap.get(uuid);
            scoreBoard.setup(uuid);
            return scoreBoard;
        }
        ScoreBoard scoreBoard = new ScoreBoard(player, playedTime, placedBlocks);
        scoreBoardMap.put(player.getUniqueId(), scoreBoard);
        return scoreBoard;
    }

    public Map<UUID, ScoreBoard> getScoreBoardMap() {
        return scoreBoardMap;
    }

}
