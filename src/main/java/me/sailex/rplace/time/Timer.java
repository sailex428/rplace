package me.sailex.rplace.time;

import org.bukkit.entity.Player;

public class Timer {

    private final Player player;
    private int time;

    public Timer(Player player, int time) {
        this.player = player;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

}
