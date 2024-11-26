package studio.lothus.api;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
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
}