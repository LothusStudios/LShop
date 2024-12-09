package studio.lothus.delivery.commands.register;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.commands.BukkitCommand;
import studio.lothus.delivery.inventories.register.StoreInventory;

import java.util.ArrayList;

public class DeliveryCommand extends BukkitCommand {

    public DeliveryCommand() {
        super(
                "delivery",
                "lothusdelivery.delivery",
                new ArrayList<>(),
                true
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            LothusDelivery.getInventories().open(player, StoreInventory.class);
        }
    }
}
