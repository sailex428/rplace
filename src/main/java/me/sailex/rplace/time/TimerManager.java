package me.sailex.rplace.time;

import me.sailex.rplace.RPlace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager {

    private final RPlace rPlace;
    private final Map<UUID, Timer> timerMap;

    public TimerManager(RPlace rPlace) {
        this.rPlace = rPlace;
        this.timerMap = new HashMap<>();
    }

    public Timer getTimer(Player player, int playedTime) {
        UUID uuid = player.getUniqueId();

        if (timerMap.containsKey(uuid)) {
            Timer timer = timerMap.get(uuid);
            timer.runTimer();
            return timer;
        }
        Timer timer = new Timer(rPlace, player, playedTime);
        timerMap.put(uuid, timer);
        return timer;
    }

}
