package me.sailex.rplace.time;

import me.sailex.rplace.RPlace;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static net.kyori.adventure.text.Component.text;

public class Countdown {

    private final RPlace rPlace;
    private final Player player;
    private int time;

    public Countdown(RPlace rPlace, Player player) {
        this.rPlace = rPlace;
        this.player = player;
        runCountDown(4);
    }

    public void runCountDown(int newTime) {
        this.time = newTime;
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    return;
                }
                sendTimerToActionBar();
                if (time >= 0) {
                    time--;
                }
            }
        };
        runnable.runTaskTimer(rPlace, 0, 20);
    }

    private void sendTimerToActionBar() {
        if (time > 0) {
            player.sendActionBar(
                    text().content(time + "s").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        } else {
            player.sendActionBar(
                    text().content("Place a new block now!").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
