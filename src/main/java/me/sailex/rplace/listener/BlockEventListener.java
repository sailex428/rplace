package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;

import me.sailex.rplace.scoreboard.ScoreBoard;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class BlockEventListener implements Listener {

    private final RPlace rPlace;

    public BlockEventListener(RPlace rPlace) {
        this.rPlace = rPlace;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(true);
        blockBreakEvent.getPlayer().sendActionBar(
                text().content("You cannot break blocks here!")
                        .color(NamedTextColor.RED)
        );
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        blockPlaceEvent.setCancelled(true);

        Block blockAgainst = blockPlaceEvent.getBlockAgainst();
        Material newBlockMaterial =  blockPlaceEvent.getBlock().getType();

        List<String> allowedMaterials = rPlace.getMaterialsManager().getAllowedMaterials();

        if (allowedMaterials.isEmpty()) {
            rPlace.getLogger().warning("Allowed Blocks List is empty!");
            return;
        }
        if (allowedMaterials.contains(newBlockMaterial.name())) {
            blockAgainst.setType(newBlockMaterial);

            Player player = blockPlaceEvent.getPlayer();
            ScoreBoard scoreBoard = rPlace.getScoreBoardManager().getScoreBoardMap().get(player.getUniqueId().toString());
            scoreBoard.setPlacedBlocks(scoreBoard.getPlacedBlocks() + 1);
            return;
        }
        blockPlaceEvent.getPlayer().sendActionBar(
                text().content("You can only place Blocks!")
                        .color(NamedTextColor.RED)
        );

    }

}
