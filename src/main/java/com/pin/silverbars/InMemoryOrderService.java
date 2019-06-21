package com.pin.silverbars;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class InMemoryOrderService implements OrderService {

    private long sequence = 0;

    private Map<Long, Order> orders = new HashMap<>();
    private Map<LiveOrderKey, BigDecimal> liveOrders = new TreeMap<>();


    @Override
    public Order registerOrder(
            // @NotNull
            String userId,
            // @NotNull
            BigDecimal quantity,
            // @NotNull
            BigDecimal price,
            // @NotNull
            OrderType orderType) {
        Order newOrder = new Order(sequence++, userId, quantity, price, orderType);
        orders.put(newOrder.getId(), newOrder);

        LiveOrderKey liveOrderKey = new LiveOrderKey(orderType, price);
        BigDecimal knownQuantity = liveOrders.get(liveOrderKey);

        BigDecimal newQuantity;
        if (knownQuantity != null)
            newQuantity = knownQuantity.add(quantity);
        else
            newQuantity = quantity;

        liveOrders.put(liveOrderKey, newQuantity);


        return newOrder;
    }

    @Override
    public Order getOrder(long orderId) {
        return orders.get(orderId);
    }

    @Override
    public Order removeOrder(long orderId) {
        Order removed = orders.remove(orderId);

        if (removed != null) {
            LiveOrderKey liveOrderKey = new LiveOrderKey(removed.getType(), removed.getPrice());
            BigDecimal knownQuantity = liveOrders.get(liveOrderKey);
            BigDecimal newQuantity = knownQuantity.subtract(removed.getQuantity());
            liveOrders.put(liveOrderKey, newQuantity);
        }

        return removed;
    }

    @Override
    public Collection<LiveOrderItem> getLiveOrders() {
        return liveOrders.entrySet().stream().map(
                e -> new LiveOrderItem(e.getValue(), e.getKey().getPrice(), e.getKey().getType())).collect(Collectors.toList());
    }
}
