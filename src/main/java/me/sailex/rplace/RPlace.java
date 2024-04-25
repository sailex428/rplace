package me.sailex.rplace;

import me.sailex.rplace.listener.ListenerManager;

import me.sailex.rplace.materials.MaterialsManager;
import me.sailex.rplace.player.RPlacePlayerBuilder;
import me.sailex.rplace.scoreboard.ScoreBoardManager;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPlace extends JavaPlugin {

    private MaterialsManager materialsManager;
    private RPlacePlayerBuilder rPlacePlayerBuilder;

    @Override
    public void onEnable() {
        RPlace instance = this;
        setupWorld();

        materialsManager = new MaterialsManager(instance);
        rPlacePlayerBuilder = new RPlacePlayerBuilder(instance, new ScoreBoardManager());

        new ListenerManager(instance).manageListeners();
    }

    @Override
    public void onDisable() {

    }

    public void setupWorld() {
        World world = Bukkit.getWorld("world");
        if (world != null) {
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            WorldBorder border = world.getWorldBorder();
            border.setSize(200);
            border.setCenter(0.0, 0.0);
        }
    }

    public MaterialsManager getMaterialsManager() {
        return materialsManager;
    }

    public RPlacePlayerBuilder getrPlacePlayerBuilder() {
        return rPlacePlayerBuilder;
    }
}
