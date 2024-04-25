package me.sailex.rplace.time;

import me.sailex.rplace.RPlace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {

    private final RPlace rPlace;
    private final Map<String, Timer> timerMap;

    public TimerManager(RPlace rPlace) {
        this.rPlace = rPlace;
        this.timerMap = new HashMap<>();
    }

    public Timer getTimer(Player player, int playedTime) {
        String uuid = player.getUniqueId().toString();

        if (timerMap.containsKey(uuid)) {
            return timerMap.get(uuid);
        }
        Timer timer = new Timer(rPlace, player, playedTime);
        timerMap.put(uuid, timer);
        return timer;
    }

}
