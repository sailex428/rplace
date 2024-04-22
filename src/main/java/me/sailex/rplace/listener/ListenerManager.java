package me.sailex.rplace.listener;

import me.sailex.rplace.RPlace;

public class ListenerManager {

    private final RPlace rPlace;

    public ListenerManager(RPlace rPlace) {
        this.rPlace = rPlace;
    }

    public void manageListeners() {
        rPlace.getServer().getPluginManager().registerEvents(new BlockEventListener(rPlace), rPlace);
        rPlace.getServer().getPluginManager().registerEvents(new InventoryEventListener(rPlace), rPlace);
        rPlace.getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(rPlace), rPlace);
    }

}
