package studio.lothus;

import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.api.ConfigManager;
import studio.lothus.api.scheduler.Scheduler;
import studio.lothus.translation.TranslationManager;

public class BukkitPlugin extends JavaPlugin {

    private LothusAPI lothusAPI;
    private Scheduler scheduler;
    private TranslationManager translationManager;
    private App app;

    @Override
    public void onEnable() {
        super.onEnable();

        translationManager = new TranslationManager(this);

        ConfigManager.setup(this);

        scheduler = new Scheduler(this);

        String token = ConfigManager.getSecret();
        if (token == null || token.isEmpty()) {
            String ApiNotFound = translationManager.getTranslation("pt-br", "apinotfound");
            getLogger().info(ApiNotFound);
            return;
        }

        lothusAPI = new LothusAPI(token);
        app = new App(lothusAPI);
        scheduler.runTaskTimer(app::checkAndProcessOrders, 0L, 20L * 60);
    }
}