package studio.lothus.delivery.commands.register;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import studio.lothus.delivery.commands.BukkitCommand;
import studio.lothus.delivery.packet.hologram.list.TopCompradores;

import java.util.ArrayList;

public class HologramCommand extends BukkitCommand {

    private final TopCompradores topCompradores;

    public HologramCommand(TopCompradores topCompradores) {
        super(
                "hologram",
                "lothusdelivery.delivery",
                new ArrayList<>(),
                true
        );
        this.topCompradores = topCompradores;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lERRO! §cEste comando só pode ser executado por um jogador.");
            return;
        }

        Player player = (Player) sender;

        topCompradores.setLocation(player.getLocation());

        topCompradores.showTopBuyers(player);

        player.sendMessage("§a§lSUCESSO! §aLocalização do holograma definida e atualizada com sucesso.");
    }
}