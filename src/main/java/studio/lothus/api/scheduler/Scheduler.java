package studio.lothus.scheduler;

import org.bukkit.plugin.java.JavaPlugin;

public class Scheduler {

    private final JavaPlugin plugin;

    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void runTaskTimer(Runnable task, long delay, long period) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, task, delay, period);
    }
}