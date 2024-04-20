package me.sailex.rplace;

import me.sailex.rplace.listener.ListenerManager;

import me.sailex.rplace.materials.MaterialsManager;
import me.sailex.rplace.scoreboard.ScoreboardBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPlace extends JavaPlugin {

    private static RPlace instance;
    private MaterialsManager materialsManager;
    private ScoreboardBuilder scoreboardBuilder;

    @Override
    public void onEnable() {
        instance = this;

        materialsManager = new MaterialsManager();
        scoreboardBuilder = new ScoreboardBuilder();

        ListenerManager.manageListeners();
    }

    @Override
    public void onDisable() {

    }

    public static RPlace getInstance() {
        return instance;
    }

    public MaterialsManager getMaterialsManager() {
        return materialsManager;
    }

    public ScoreboardBuilder getScoreboardBuilder() {
        return scoreboardBuilder;
    }
}
