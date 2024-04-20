package me.sailex.rplace.materials;

import me.sailex.rplace.RPlace;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MaterialsManager {

    private List<String> allowedMaterials;

    public MaterialsManager() {
        Logger LOGGER = RPlace.getInstance().getLogger();
        try {
            allowedMaterials = RPlace.getInstance().getConfig().getStringList("allowedBlocks");
            LOGGER.info("Loaded allowedBlocks List from config.yml successfully!");
        } catch (ClassCastException e) {
            LOGGER.warning("Could not load allowed Block List from config.yml!");
            allowedMaterials = new ArrayList<>();
        }
    }

    public List<String> getAllowedMaterials() {
        return allowedMaterials;
    }

}
