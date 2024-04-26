package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.player.RPlacePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerJoinLeaveListener implements Listener {

    private final RPlace rPlace;

    public PlayerJoinLeaveListener(RPlace rPlace) {
        this.rPlace = rPlace;
    }

    @EventHandler
    public void onPlayerJoinListener(PlayerJoinEvent joinEvent) {
        rPlace.getrPlacePlayerBuilder().buildPlayer(joinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerLeaveListener(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        List<RPlacePlayer> placePlayers = rPlace.getrPlacePlayerBuilder().getRPlacePlayers();
        for (RPlacePlayer placePlayer : placePlayers) {
            if (player.equals(placePlayer.getPlayer())) {
                rPlace.getrPlacePlayerBuilder().savePlayerData(placePlayer);
                placePlayers.remove(placePlayer);
                return;
            }
        }
    }

}
