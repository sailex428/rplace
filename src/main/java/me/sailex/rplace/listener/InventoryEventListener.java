package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import net.kyori.adventure.text.format.NamedTextColor;
import static net.kyori.adventure.text.Component.text;

import java.util.List;

public class InventoryEventListener implements Listener {

    private final RPlace rPlace;

    public InventoryEventListener(RPlace rPlace) {
        this.rPlace = rPlace;
    }

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
        List<String> allowedMaterials = rPlace.getMaterialsManager().getAllowedMaterials();

        Material material = clickEvent.getCursor().getType();
        if (!allowedMaterials.contains(material.name())) {
            clickEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent openEvent) {
        if (!openEvent.getInventory().equals(openEvent.getPlayer().getInventory())) {
            openEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawnEntity(EntitySpawnEvent spawnEvent) {
        spawnEvent.setCancelled(true);
    }

}
