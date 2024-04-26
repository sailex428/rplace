package me.sailex.rplace.time;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.scoreboard.ScoreBoard;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class Timer {

    private final RPlace rPlace;
    private final Player player;
    private int time;

    public Timer(RPlace rPlace, Player player, int time) {
        this.rPlace = rPlace;
        this.player = player;
        this.time = time;
        runTimer();
    }

    public void runTimer() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                }
                sendTimerToScoreBoard();
                time++;
            }
        };
        runnable.runTaskTimer(rPlace, 0, 20);
    }

    private void sendTimerToScoreBoard() {
        ScoreBoard scoreBoard = rPlace.getScoreBoardManager().getScoreBoardMap().get(player.getUniqueId());
        scoreBoard.setPlayedTime(formatTime(time));
    }

    private String formatTime(int time) {
        long days = TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(time) % 60;
        long seconds = TimeUnit.SECONDS.toSeconds(time) % 60;

        StringBuilder timeString = new StringBuilder();

        if (days > 0) timeString.append(days).append("d ");

        if (hours > 0) timeString.append(hours).append("h ");

        if (minutes > 0) timeString.append(minutes).append("m ");

        if (seconds > 0) timeString.append(seconds).append("s ");

        return timeString.toString().trim();
    }

    public int getTime() {
        return time;
    }

}
