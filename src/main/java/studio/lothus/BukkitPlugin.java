package studio.lothus;

import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.api.ConfigManager;

import studio.lothus.api.scheduler.Scheduler;
import studio.lothus.command.LeadCommand;
import studio.lothus.core.leaderboard.HttpUtil;
import studio.lothus.core.leaderboard.hologram.HologramUtil;
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
            String ApiNotFound = translationManager.getTranslation("pt-br", "api-not-found");
            getLogger().info(ApiNotFound);
            return;
        }

        lothusAPI = new LothusAPI(token);
        app = new App(lothusAPI);
        scheduler.runTaskTimer(app::checkAndProcessOrders, 0L, 20L * 60);

        String apiUrl = "https://api.lothus.shop/web/id_da_loja/topBuyers?limit=100";
        String response = HttpUtil.sendGetRequest(apiUrl);

        this.getCommand("sethologram").setExecutor(new LeadCommand());

        HologramUtil.createHologram(response);
    }
}