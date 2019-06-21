package com.pin.silverbars;

import java.math.BigDecimal;
import java.util.Collection;

public interface OrderService {
    Order registerOrder(String userId,
                        BigDecimal orderQuantity,
                        BigDecimal price,
                        OrderType orderType);

    Order getOrder(long orderId);

    Order removeOrder(long orderId);

    Collection<LiveOrderItem> getLiveOrders();
}
