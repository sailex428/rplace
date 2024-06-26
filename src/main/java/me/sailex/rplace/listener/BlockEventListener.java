package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;

import me.sailex.rplace.player.RPlacePlayer;
import me.sailex.rplace.scoreboard.ScoreBoard;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class BlockEventListener implements Listener {

    private final RPlace rPlace;

    public BlockEventListener(RPlace rPlace) {
        this.rPlace = rPlace;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        if (blockBreakEvent.getPlayer().isOp()) {
            return;
        }
        blockBreakEvent.setCancelled(true);
        blockBreakEvent.getPlayer().sendActionBar(
                text().content("You cannot break blocks here!")
                        .color(NamedTextColor.RED)
        );
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if (blockPlaceEvent.getPlayer().isOp()) {
            return;
        }
        blockPlaceEvent.setCancelled(true);
        Player player = blockPlaceEvent.getPlayer();
        RPlacePlayer currentPlacePlayer = null;
        for (RPlacePlayer placePlayer : rPlace.getrPlacePlayerBuilder().getRPlacePlayers()) {
            if (player.equals(placePlayer.getPlayer())) {
                currentPlacePlayer = placePlayer;
                if (placePlayer.getCountdown().getTime() >= 0) {
                    return;
                }
            }
        }

        Block blockAgainst = blockPlaceEvent.getBlockAgainst();
        Material newBlockMaterial =  blockPlaceEvent.getBlock().getType();

        if (newBlockMaterial.equals(blockAgainst.getType())) {
                blockPlaceEvent.getPlayer().sendActionBar(
                        text().content("This type of block is already placed here!")
                                .color(NamedTextColor.RED)
                );
                return;
        }

        List<String> allowedMaterials = rPlace.getMaterialsManager().getAllowedMaterials();

        if (allowedMaterials.isEmpty()) {
            rPlace.getLogger().warning("Allowed Blocks List is empty!");
            return;
        }
        if (allowedMaterials.contains(newBlockMaterial.name())) {
            blockAgainst.setType(newBlockMaterial);

            if (currentPlacePlayer != null) {
                ScoreBoard scoreBoard = currentPlacePlayer.getScoreBoard();
                scoreBoard.setupPlacedBlocks(scoreBoard.getPlacedBlocks() + 1);
                currentPlacePlayer.getCountdown().setTime(4);
            }
            return;
        }
        blockPlaceEvent.getPlayer().sendActionBar(
                text().content("You can only place Blocks!")
                        .color(NamedTextColor.RED)
        );

    }

    @EventHandler
    public void onExplodeEvent(EntityExplodeEvent explodeEvent) {
        explodeEvent.setCancelled(true);
    }

}
