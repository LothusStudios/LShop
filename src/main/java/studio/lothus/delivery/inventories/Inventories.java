package studio.lothus.delivery.inventories;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.entity.Player;
import studio.lothus.delivery.LothusDelivery;
import studio.lothus.delivery.inventories.register.StoreInventory;
import studio.lothus.delivery.inventories.register.buyers.BuyersInventory;
import studio.lothus.delivery.inventories.register.coupon.CouponsInventory;

import studio.lothus.delivery.inventories.register.products.ProductsInventory;
import studio.lothus.delivery.inventories.register.products.manager.ProductEditInventory;
import studio.lothus.delivery.model.Product;

public class Inventories {

    private ViewFrame viewFrame;

    public Inventories() {
        viewFrame = ViewFrame.create(LothusDelivery.getInstance())
                .with(
                        new StoreInventory(),
                        new BuyersInventory(),
                        new ProductsInventory(),
                        new CouponsInventory(),
                        new ProductEditInventory(new Product()))
                .register();
    }

    public void open(Player player, Class<? extends View> c) {
        viewFrame.open(c, player);
    }
}
