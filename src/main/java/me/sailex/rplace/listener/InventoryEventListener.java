package me.sailex.rplace.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

import static me.sailex.rplace.RPlace.getInstance;
import static net.kyori.adventure.text.Component.text;

public class InventoryEventListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent dropItemEvent) {
        dropItemEvent.setCancelled(true);
        dropItemEvent.getPlayer().sendActionBar(
                text().content("You cannot drop items!")
                        .color(NamedTextColor.RED)
        );
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent clickEvent) {
        List<String> allowedMaterials = getInstance().getMaterialsManager().getAllowedMaterials();

        Material material = clickEvent.getCursor().getType();
        if (!allowedMaterials.contains(material.name())) {
            clickEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawnEntity(EntitySpawnEvent spawnEvent) {
        spawnEvent.setCancelled(true);
    }

}
