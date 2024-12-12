package studio.lothus.delivery;

import com.google.gson.Gson;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.delivery.commands.register.CreateCouponCommand;
import studio.lothus.delivery.commands.register.DeliveryCommand;
import studio.lothus.delivery.commands.register.HologramCommand;
import studio.lothus.delivery.inventories.Inventories;
import studio.lothus.delivery.packet.hologram.list.TopCompradores;
import studio.lothus.delivery.scheduler.PendingOrdersSchedule;
import studio.lothus.delivery.service.LothusService;

public class LothusDelivery extends JavaPlugin {

    @Getter
    private static Gson gson;

    @Getter
    private static LothusService service;

    @Getter private TopCompradores topCompradores;

    @Getter
    private static LothusDelivery instance;

    @Getter
    private static Inventories inventories;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        instance = this;

        gson = new Gson();
        service = new LothusService(getConfig().getString("store-token"));
    }

    @Override
    public void onEnable() {
        if (!service.checkToken()) {
            getLogger().info("Token inválido, desabilitando plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new PendingOrdersSchedule(), 0, (20 * 60));

        inventories = new Inventories();
        topCompradores = new TopCompradores(this);

        getCommand("create").setExecutor(new CreateCouponCommand(getService()));
        getCommand("hologram").setExecutor(new HologramCommand(topCompradores));
        getCommand("delivery").setExecutor(new DeliveryCommand());
    }

    @Override
    public void onDisable() {

    }
}
