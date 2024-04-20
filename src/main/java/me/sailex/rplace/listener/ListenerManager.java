package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;

public class ListenerManager {

    private static final RPlace rPlace = RPlace.getInstance();

    public static void manageListeners() {
        rPlace.getServer().getPluginManager().registerEvents(new BlockEventListener(), rPlace);
        rPlace.getServer().getPluginManager().registerEvents(new InventoryEventListener(), rPlace);
        rPlace.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), rPlace);
    }

}
