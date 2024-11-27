package studio.lothus.api;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import studio.lothus.BukkitPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigManager {

    private static Configuration fileConfiguration;
    private static File propertiesFile;
    private static final String fileName = "properties.yml";

    public static void setup(BukkitPlugin plugin) {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

        propertiesFile = new File(plugin.getDataFolder(), fileName);

        if (!propertiesFile.exists()) {
            try {
                Files.createFile(propertiesFile.toPath());
                fileConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(propertiesFile);
                fileConfiguration.set("secret", "SUA-TOKEN-AQUI!");
                fileConfiguration.set("hologram.world", "world");
                fileConfiguration.set("hologram.x", 0);
                fileConfiguration.set("hologram.y", 100);
                fileConfiguration.set("hologram.z", 0);
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                fileConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(propertiesFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Configuration get() {
        return fileConfiguration;
    }

    public static void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(get(), propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setValue(String key, String value) {
        get().set(key, value);
        save();
    }

    public static void setSecret(String value) {
        setValue("secret", value);
    }

    public static String getSecret() {
        return get().getString("secret");
    }

    public static void setHologramLocation(Location location) {
        get().set("hologram.world", location.getWorld().getName());
        get().set("hologram.x", location.getX());
        get().set("hologram.y", location.getY());
        get().set("hologram.z", location.getZ());
        save();
    }

    public static Location getHologramLocation() {
        String worldName = get().getString("hologram.world");
        double x = get().getDouble("hologram.x");
        double y = get().getDouble("hologram.y");
        double z = get().getDouble("hologram.z");
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}