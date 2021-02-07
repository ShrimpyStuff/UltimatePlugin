package ca.sajid.ultimateplugin.util;

import ca.sajid.ultimateplugin.UltimatePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CustomConfig {

    private final UltimatePlugin plugin = UltimatePlugin.getPlugin();
    private final String path;
    private final File file;
    private FileConfiguration config;

    public CustomConfig(String path) {
        this(path, true);
    }

    public CustomConfig(String path, boolean autoLoad) {
        this.path = path;

        file = new File(plugin.getDataFolder(), path);

        if (autoLoad) {
            saveDefault();
            reload();
        }
    }

    public void saveDefault() {
        if (file.exists()) return;

        File folder = plugin.getDataFolder();
        folder.mkdir();

        try (InputStream in = plugin.getResource(path)) {
            if (in != null) {
                Files.copy(in, file.toPath());
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration get() {
        return config;
    }

    public UltimatePlugin getPlugin() {
        return plugin;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }
}
