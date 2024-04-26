package me.sailex.rplace.player;

import me.sailex.rplace.time.Countdown;
import me.sailex.rplace.scoreboard.ScoreBoard;
import me.sailex.rplace.time.Timer;
import org.bukkit.entity.Player;

public class RPlacePlayer {

    private Player player;
    private final ScoreBoard scoreBoard;
    private final Countdown countdown;
    private final Timer timer;

    public RPlacePlayer(Player player, ScoreBoard scoreBoard, Countdown countdown, Timer timer) {
        this.player = player;
        this.scoreBoard = scoreBoard;
        this.countdown = countdown;
        this.timer = timer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public Timer getTimer() {
        return timer;
    }

}
