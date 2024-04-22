package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveListener implements Listener {

    private final RPlace rPlace;

    public PlayerJoinLeaveListener(RPlace rPlace) {
        this.rPlace = rPlace;
    }

    @EventHandler
    public void onPlayerJoinListener(PlayerJoinEvent joinEvent) {
        rPlace.getScoreBoardManager().getScoreboard(joinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerLeaveListener(PlayerQuitEvent event) {
        rPlace.getScoreBoardManager().saveScoreBoardOfPlayer(event.getPlayer());
    }

}
