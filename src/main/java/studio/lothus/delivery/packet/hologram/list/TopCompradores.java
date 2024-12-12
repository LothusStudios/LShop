package studio.lothus.delivery.packet.hologram.list;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.service.LothusService;

public class TopCompradores {

    private final JavaPlugin plugin;
    private Location location;
    private ArmorStand[] hologramLines;

    public TopCompradores(JavaPlugin plugin) {
        this.plugin = plugin;
        loadLocationFromConfig();
    }

    public void setLocation(Location location) {
        this.location = location;
        saveLocationToConfig();
    }

    public void showTopBuyers(Player player) {
        if (location == null) {
            player.sendMessage("§c§lERRO! §cLocalização do holograma não definida. Use /hologram primeiro.");
            return;
        }

        if (hologramLines != null) {
            for (ArmorStand armorStand : hologramLines) {
                if (armorStand != null && !armorStand.isDead()) {
                    armorStand.remove();
                }
            }
        }

        hologramLines = new ArmorStand[13];

        LothusService lothusService = LothusDelivery.getService();
        addHologramLine(0, "§6§lTOP COMPRADORES");
        JsonArray topBuyers = lothusService.getTopBuyers();
        for (int i = 0; i < 10; i++) {
            String line = getPositionColor(i + 1) + (i + 1) + " §7- ";
            if (i < topBuyers.size()) {
                JsonElement buyer = topBuyers.get(i);
                String name = buyer.getAsJsonObject().get("name").getAsString();
                line += "§f" + name;
            } else {
                line += "§7Ninguém";
            }
            addHologramLine(i + 1, line);
        }

    }

    private void addHologramLine(int index, String text) {
        Location lineLocation = location.clone().add(0, -0.25 * index, 0);
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(lineLocation, EntityType.ARMOR_STAND);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        hologramLines[index] = armorStand;
    }

    private String getPositionColor(int position) {
        switch (position) {
            case 1:
                return "§a";
            case 2:
                return "§6";
            case 3:
                return "§c";
            default:
                return "§e";
        }
    }

    private void saveLocationToConfig() {
        plugin.getConfig().set("hologram.world", location.getWorld().getName());
        plugin.getConfig().set("hologram.x", location.getX());
        plugin.getConfig().set("hologram.y", location.getY());
        plugin.getConfig().set("hologram.z", location.getZ());
        plugin.saveConfig();
    }

    private void loadLocationFromConfig() {
        if (plugin.getConfig().contains("hologram")) {
            String worldName = plugin.getConfig().getString("hologram.world");
            double x = plugin.getConfig().getDouble("hologram.x");
            double y = plugin.getConfig().getDouble("hologram.y");
            double z = plugin.getConfig().getDouble("hologram.z");

            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                location = new Location(world, x, y, z);
            } else {
                plugin.getLogger().warning("Mundo '" + worldName + "' não encontrado ao carregar a localização do holograma.");
            }
        }
    }
}