package studio.lothus.delivery.inventories.register.coupon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.inventories.register.StoreInventory;
import studio.lothus.delivery.utils.ItemCreator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CouponsInventory extends View {

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Cupons - Lothus Shop");
        config.size(9 * 3);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        JsonArray coupons = LothusDelivery.getService().getCoupons();
        int slot = 0;

        for (JsonElement coupon : coupons) {
            String key = coupon.getAsJsonObject().get("key").getAsString();
            double discount = coupon.getAsJsonObject().get("discount").getAsDouble();
            int maxUses = coupon.getAsJsonObject().has("maxUses") ? coupon.getAsJsonObject().get("maxUses").getAsInt() : -1;
            long expires = coupon.getAsJsonObject().has("expires") ? coupon.getAsJsonObject().get("expires").getAsLong() : -1;

            String expirationDate = expires != -1 ? "§fExpira em: §e" + formatTimestamp(expires) : "§fExpira em: §cIndefinido";

            render.slot(slot, new ItemCreator(Material.PAPER, "§aCupom: " + key)
                    .setLore(
                            "§fDesconto: §e" + discount + "%",
                            maxUses != -1 ? "§fMáximo de usos: §e" + maxUses : "§fMáximo de usos: §cIlimitado",
                            expirationDate
                    ).build());

            slot++;
        }

        render.slot(26, new ItemCreator(Material.ARROW, "§aVoltar")
                        .setLore("§eClique para voltar ao menu principal.")
                        .build())
                .onClick((event) -> {
                    LothusDelivery.getInventories().open((Player) event.getPlayer(), StoreInventory.class);
                });
    }
}
