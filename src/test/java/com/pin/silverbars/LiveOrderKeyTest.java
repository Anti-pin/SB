package com.pin.silverbars;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LiveOrderKeyTest {

    @Test
    public void testOrdering() {

        LiveOrderKey buyOrder42 = LiveOrderKey.valueOf(OrderType.buy, BigDecimal.valueOf(42));
        LiveOrderKey buyOrder84 = LiveOrderKey.valueOf(OrderType.buy, BigDecimal.valueOf(84));

        LiveOrderKey sellOrder42 = LiveOrderKey.valueOf(OrderType.sell, BigDecimal.valueOf(42));
        LiveOrderKey sellOrder84 = LiveOrderKey.valueOf(OrderType.sell, BigDecimal.valueOf(84));


        Assert.assertEquals(0, buyOrder42.compareTo(LiveOrderKey.valueOf(OrderType.buy, BigDecimal.valueOf(42))));
        Assert.assertEquals(0, sellOrder42.compareTo(LiveOrderKey.valueOf(OrderType.sell, BigDecimal.valueOf(42))));

        Assert.assertTrue(buyOrder42.compareTo(buyOrder84) > 0);
        Assert.assertTrue(sellOrder42.compareTo(sellOrder84) < 0);

        Assert.assertTrue(sellOrder42.compareTo(buyOrder42) > 0);
    }
}
