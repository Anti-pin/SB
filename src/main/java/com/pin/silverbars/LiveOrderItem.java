package com.pin.silverbars;

import java.math.BigDecimal;
import java.util.Objects;

public final class LiveOrderItem {
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final OrderType type;

    public LiveOrderItem(BigDecimal quantity, BigDecimal price, OrderType type) {
        this.quantity = quantity;
        this.price = price;
        this.type = type;
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
        LiveOrderItem that = (LiveOrderItem) o;
        return quantity.equals(that.quantity) &&
                price.equals(that.price) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, price, type);
    }

    @Override
    public String toString() {
        return "LiveOrderItem{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", type=" + type +
                '}';
    }



}
