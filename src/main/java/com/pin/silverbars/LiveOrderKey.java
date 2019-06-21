package com.pin.silverbars;


import java.math.BigDecimal;
import java.util.Objects;

final class LiveOrderKey implements Comparable {
    private final OrderType type;
    private final BigDecimal price;

    LiveOrderKey(
            // @NotNull
            OrderType type,
            // @NotNull
            BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    OrderType getType() {
        return type;
    }

    BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveOrderKey that = (LiveOrderKey) o;
        return type == that.type &&
                price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price);
    }

    @Override
    public int compareTo(Object other) {
        LiveOrderKey otherLiveOrder = (LiveOrderKey) other;

        if (this.type != otherLiveOrder.type)
            return this.type.ordinal() - otherLiveOrder.type.ordinal();

        if (this.type == OrderType.buy)
            return otherLiveOrder.price.compareTo(this.price);
        else
            return this.price.compareTo(otherLiveOrder.price);

    }

    static LiveOrderKey valueOf(
            // @NotNull
            OrderType type,
            // @NotNull
            BigDecimal price) {
        return new LiveOrderKey(type, price);
    }
}
