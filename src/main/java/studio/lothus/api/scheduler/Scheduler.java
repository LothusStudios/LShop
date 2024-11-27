package studio.lothus.api.scheduler;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Scheduler {

    private final JavaPlugin plugin;
    private final Map<UUID, BukkitTask> tasks;

    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.tasks = new HashMap<>();
    }

    public UUID runTaskTimer(Runnable task, long delay, long period) {
        BukkitTask bukkitTask = plugin.getServer().getScheduler().runTaskTimer(plugin, task, delay, period);
        UUID taskId = UUID.randomUUID();
        tasks.put(taskId, bukkitTask);
        return taskId;
    }

    public void cancelTask(UUID taskId) {
        BukkitTask task = tasks.get(taskId);
        if (task != null) {
            task.cancel();
            tasks.remove(taskId);
        }
    }

    public void Run() {

    }

    public void cancelAllTasks() {
        for (BukkitTask task : tasks.values()) {
            task.cancel();
        }
        tasks.clear();
    }
}