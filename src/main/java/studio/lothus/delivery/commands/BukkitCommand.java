package studio.lothus.delivery.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@Getter @Setter
public abstract class BukkitCommand implements CommandExecutor {

    private String command;
    private String permission;
    private List<String> aliases;
    private boolean allowConsole;

    private final String NO_PERMISSION = "§cVocê não tem permissão para executar este comando.";

    public BukkitCommand(String command, String permission, List<String> aliases, boolean allowConsole) {
        this.command = command;
        this.permission = permission;
        this.aliases = aliases;
        this.allowConsole = allowConsole;
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            if (!allowConsole) {
                sender.sendMessage("§cEste comando não pode ser executado pelo console.");
                return false;
            }
        } else {
            Player player = (Player) sender;
            if (!player.hasPermission(permission)) {
                player.sendMessage(NO_PERMISSION);
                return false;
            }
        }

        execute(sender, args);
        return false;
    }
}
