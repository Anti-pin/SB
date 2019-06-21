package com.pin.silverbars;

import java.math.BigDecimal;
import java.util.Objects;

public final class Order {
    private final long id;
    private final String userId;
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final OrderType type;

    public Order(
            long orderId,
            String userId,
            BigDecimal orderQuantity,
            BigDecimal price,
            OrderType orderType)
    {
        this.id = orderId;
        this.userId = userId;
        this.quantity = orderQuantity;
        this.price = price;
        this.type = orderType;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                userId.equals(order.userId) &&
                quantity.equals(order.quantity) &&
                price.equals(order.price) &&
                type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, quantity, price, type);
    }
}
