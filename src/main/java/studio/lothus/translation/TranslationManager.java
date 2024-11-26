package studio.lothus.translation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import studio.lothus.BukkitPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TranslationManager {

    private final Map<String, FileConfiguration> translations = new HashMap<>();

    public TranslationManager(BukkitPlugin plugin) {
        loadTranslations(plugin);
    }

    private void loadTranslations(BukkitPlugin plugin) {
        File languagesFolder = new File(plugin.getDataFolder(), "languages");
        if (!languagesFolder.exists()) {
            languagesFolder.mkdirs();
        }

        File[] languageFiles = languagesFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (languageFiles != null) {
            for (File file : languageFiles) {
                String language = file.getName().replace(".txt", "");
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                translations.put(language, config);
            }
        }
    }

    public String getTranslation(String language, String key) {
        FileConfiguration config = translations.get(language);
        if (config != null) {
            return config.getString(key, "Translation not found: " + key);
        }
        return "Language not found: " + language;
    }
}