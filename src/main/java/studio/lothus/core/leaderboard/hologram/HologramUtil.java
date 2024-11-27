package studio.lothus.core.leaderboard.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import studio.lothus.api.ConfigManager;

public class HologramUtil {

    public static void createHologram(String response) {
        Location location = ConfigManager.getHologramLocation();

        if (response == null || response.isEmpty()) {
            response = "§6§LTOP 100 COMPRADORES\n§7 - NONE";
        }

        String[] lines = response.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            createHologramLine(location.clone().add(0, -i, 0), line);
        }
    }

    private static void createHologramLine(Location location, String line) {
        try {
            Object world = location.getWorld().getClass().getMethod("getHandle").invoke(location.getWorld());
            Class<?> entityClass = Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".Entity");
            Class<?> armorStandClass = Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".EntityArmorStand");
            Object armorStand = armorStandClass.getConstructor(world.getClass()).newInstance(world);

            armorStandClass.getMethod("setLocation", double.class, double.class, double.class, float.class, float.class).invoke(armorStand, location.getX(), location.getY(), location.getZ(), 0, 0);
            armorStandClass.getMethod("setCustomName", String.class).invoke(armorStand, line);
            armorStandClass.getMethod("setCustomNameVisible", boolean.class).invoke(armorStand, true);
            armorStandClass.getMethod("setInvisible", boolean.class).invoke(armorStand, true);
            armorStandClass.getMethod("setGravity", boolean.class).invoke(armorStand, false);

            entityClass.getMethod("addEntity", armorStand.getClass()).invoke(world, armorStand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}