package studio.lothus.delivery.scheduler;

import org.bukkit.Bukkit;
import studio.lothus.delivery.LothusDelivery;

public class PendingOrdersSchedule implements Runnable {

    @Override
    public void run() {
        Bukkit.getLogger().info("[Lothus Delivery] Buscando dados...");

        LothusDelivery.getService().pendingOrders();
        LothusDelivery.getService().topBuyers();
        LothusDelivery.getService().coupons();
        LothusDelivery.getService().products();
        LothusDelivery.getService().stats();

        Bukkit.getLogger().info("[Lothus Delivery] Dados atualizados!");
    }
}
