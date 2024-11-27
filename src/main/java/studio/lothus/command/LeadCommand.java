package studio.lothus.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import studio.lothus.api.ConfigManager;

public class LeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ConfigManager.setHologramLocation(player.getLocation());
            player.sendMessage("§aHolograma definida para a sua posição atual.");
            return true;
        } else {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return false;
        }
    }
}