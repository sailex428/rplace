package me.sailex.rplace.time;

import me.sailex.rplace.RPlace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {

    private RPlace rPlace;
    private Map<String, Timer> timerMap;

    public TimerManager(RPlace rPlace) {
        this.rPlace = rPlace;
        this.timerMap = new HashMap<>();
    }

    public Timer getTimer(Player player) {


        return new Timer(player, 0);
    }

    public Map<String, Timer> getTimerMap() {
        return timerMap;
    }

}
