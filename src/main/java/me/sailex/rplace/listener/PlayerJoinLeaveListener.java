package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;
import me.sailex.rplace.player.RPlacePlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.kyori.adventure.text.Component.text;

public class PlayerJoinLeaveListener implements Listener {

    private final RPlace rPlace;

    public PlayerJoinLeaveListener(RPlace rPlace) {
        this.rPlace = rPlace;
    }

    @EventHandler
    public void onPlayerJoinListener(PlayerJoinEvent joinEvent) {
        joinEvent.getPlayer().sendMessage(text().content(
                "Welcome to our r/place-inspired Minecraft server! ").color(NamedTextColor.GOLD)
        );
        rPlace.getrPlacePlayerBuilder().buildPlayer(joinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerLeaveListener(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (RPlacePlayer placePlayer : rPlace.getrPlacePlayerBuilder().getRPlacePlayers()) {
            if (player.equals(placePlayer.getPlayer())) {
                rPlace.getrPlacePlayerBuilder().savePlayerData(placePlayer);
                rPlace.getrPlacePlayerBuilder().getRPlacePlayers().remove(placePlayer);
                return;
            }
        }
    }

}
