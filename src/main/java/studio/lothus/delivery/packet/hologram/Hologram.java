package studio.lothus.delivery.packet.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import studio.lothus.delivery.packet.PacketUtils;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private final JavaPlugin plugin;
    private final Location location;
    private final List<String> lines;
    private final List<Integer> entityIds;

    public Hologram(JavaPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
        this.lines = new ArrayList<>();
        this.entityIds = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void clearLines() {
        lines.clear();
        entityIds.clear();
    }

    public void show(Player player) {
        hide(player);

        double yOffset = 0;
        for (String line : lines) {
            int entityId = PacketUtils.nextEntityId();
            entityIds.add(entityId);

            PacketUtils.sendSpawnEntityPacket(player, entityId, location.clone().add(0, yOffset, 0));
            PacketUtils.sendEntityMetadataPacket(player, entityId, line);

            yOffset -= 0.25;
        }
    }

    public void hide(Player player) {
        for (int entityId : entityIds) {
            PacketUtils.sendDestroyEntityPacket(player, entityId);
        }
        entityIds.clear();
    }
}