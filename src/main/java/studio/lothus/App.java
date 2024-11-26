package studio.lothus;

import studio.lothus.model.Order;

import java.util.List;

public class App {

    private final LothusAPI lothusAPI;

    public App(LothusAPI lothusAPI) {
        this.lothusAPI = lothusAPI;
    }

    public void checkAndProcessOrders() {
        List<Order> pendingOrders = lothusAPI.getPendingOrders();
        if (pendingOrders == null) return;

        for (Order order : pendingOrders) {
            processOrder(order);
        }
    }
    private void processOrder(Order order) {

    }
}