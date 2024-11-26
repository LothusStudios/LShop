package studio.lothus;

import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.api.ConfigManager;
import studio.lothus.api.scheduler.Scheduler;

public class BukkitPlugin extends JavaPlugin {

    private LothusAPI lothusAPI;
    private Scheduler scheduler;
    private App app;

    @Override
    public void onEnable() {
        super.onEnable();

        ConfigManager.setup(this);

        scheduler = new Scheduler(this);

        String token = ConfigManager.getSecret();
        if (token == null || token.isEmpty()) {
            getLogger().severe("Token não encontrado ou inválido no arquivo de configuração.");
            return;
        }

        lothusAPI = new LothusAPI(token);
        app = new App(lothusAPI);
        scheduler.runTaskTimer(app::checkAndProcessOrders, 0L, 20L * 60);
    }
}