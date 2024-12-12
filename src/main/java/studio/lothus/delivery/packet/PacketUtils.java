package studio.lothus.delivery.packet;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

public class PacketUtils {

    private static final Random RANDOM = new Random();

    public static int nextEntityId() {
        return RANDOM.nextInt(Integer.MAX_VALUE);
    }

    public static void sendSpawnEntityPacket(Player player, int entityId, Location location) {
        EntityArmorStand armorStand = new EntityArmorStand(
                ((CraftPlayer) player).getHandle().world,
                location.getX(),
                location.getY(),
                location.getZ()
        );

        armorStand.setInvisible(true);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setArms(false);
        armorStand.setBasePlate(false);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armorStand);
        sendPacket(player, packet);
    }

    public static void sendEntityMetadataPacket(Player player, int entityId, String line) {
        DataWatcher dataWatcher = new DataWatcher(null);
        dataWatcher.a(0, (byte) 0x20);
        dataWatcher.a(2, line);
        dataWatcher.a(3, (byte) 1);
        dataWatcher.a(5, (byte) 1);

        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(entityId, dataWatcher, true);
        sendPacket(player, packet);
    }

    public static void sendDestroyEntityPacket(Player player, int entityId) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityId);
        sendPacket(player, packet);
    }

    private static void sendPacket(Player player, Object packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
    }
}