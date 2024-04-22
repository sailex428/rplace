package me.sailex.rplace.config;

import me.sailex.rplace.RPlace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigLoader {

    private FileConfiguration fileConfiguration;
    private File dataFile;
    private final RPlace rPlace;
    private final String filename;

    public ConfigLoader(String filename, RPlace rPlace) {
        this.filename = filename;
        this.rPlace = rPlace;
        createConfig();
    }

    private void createConfig() {

        this.dataFile = new File(rPlace.getDataFolder(), this.filename);

        if (!dataFile.exists()) {

            dataFile.getParentFile().mkdirs();
            rPlace.saveResource(this.filename, false);

        }
        this.fileConfiguration = new YamlConfiguration();

        fileConfiguration = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public File getDataFile() {
        return dataFile;
    }


}
