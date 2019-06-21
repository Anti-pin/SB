package com.pin.silverbars;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderServiceTest {


    private OrderService orderService = new InMemoryOrderService();

    @Test
    public void testOrderRegistration() {
        String userId = "User1";
        BigDecimal quantity = BigDecimal.valueOf(42);
        BigDecimal price = BigDecimal.valueOf(100.1);

        Order o1 = orderService.registerOrder(userId, quantity, price, OrderType.buy);
        Order o2 = orderService.registerOrder(userId, quantity, price, OrderType.buy);

        Assert.assertNotEquals(o1, o2);

        Assert.assertEquals(userId, o1.getUserId());
        Assert.assertEquals(quantity, o1.getQuantity());
        Assert.assertEquals(price, o1.getPrice());
        Assert.assertEquals(OrderType.buy, o1.getType());

        Assert.assertEquals(o1, orderService.getOrder(o1.getId()));
        Assert.assertEquals(o2, orderService.getOrder(o2.getId()));

        Order removed = orderService.removeOrder(o2.getId());
        Assert.assertEquals(o2, removed);
        Assert.assertNull(orderService.getOrder(o2.getId()));
    }


    @Test
    public void testLiveOrderSellSide() {

        orderService.registerOrder("user1", BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), OrderType.sell);
        orderService.registerOrder("user2", BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), OrderType.sell);
        orderService.registerOrder("user3", BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), OrderType.sell);
        orderService.registerOrder("user4", BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), OrderType.sell);

        Assert.assertArrayEquals(
                new LiveOrderItem[]{
                        new LiveOrderItem(BigDecimal.valueOf(5.5), BigDecimal.valueOf(306), OrderType.sell),
                        new LiveOrderItem(BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), OrderType.sell),
                        new LiveOrderItem(BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), OrderType.sell)
                },
                orderService.getLiveOrders().toArray()
        );
    }

    @Test
    public void testLiveOrderBuySide() {
        orderService.registerOrder("user1", BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), OrderType.buy);
        orderService.registerOrder("user2", BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), OrderType.buy);
        orderService.registerOrder("user3", BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), OrderType.buy);
        orderService.registerOrder("user4", BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), OrderType.buy);

        Assert.assertArrayEquals(
                new LiveOrderItem[]{
                        new LiveOrderItem(BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), OrderType.buy),
                        new LiveOrderItem(BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), OrderType.buy),
                        new LiveOrderItem(BigDecimal.valueOf(5.5), BigDecimal.valueOf(306), OrderType.buy)

                },
                orderService.getLiveOrders().toArray()
        );
    }

    @Test
    public void testLiveOrderMixed() {
        orderService.registerOrder("user1", BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), OrderType.sell);
        orderService.registerOrder("user2", BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), OrderType.sell);
        orderService.registerOrder("user3", BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), OrderType.buy);
        orderService.registerOrder("user4", BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), OrderType.buy);

        Assert.assertArrayEquals(
                new LiveOrderItem[]{
                        new LiveOrderItem(BigDecimal.valueOf(1.5), BigDecimal.valueOf(307), OrderType.buy),
                        new LiveOrderItem(BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), OrderType.buy),
                        new LiveOrderItem(BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), OrderType.sell),
                        new LiveOrderItem(BigDecimal.valueOf(1.2), BigDecimal.valueOf(310), OrderType.sell),
                },
                orderService.getLiveOrders().toArray()
        );
    }

    @Test
    public void testOrderRemovalAffectingLiveOrders() {
        Order o1 = orderService.registerOrder("user1", BigDecimal.valueOf(3.5), BigDecimal.valueOf(306), OrderType.sell);
        orderService.registerOrder("user4", BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), OrderType.sell);
        orderService.removeOrder(o1.getId());

        Assert.assertArrayEquals(
                new LiveOrderItem[]{
                        new LiveOrderItem(BigDecimal.valueOf(2.0), BigDecimal.valueOf(306), OrderType.sell),
                },
                orderService.getLiveOrders().toArray()
        );
    }

    @Test
    public void testRetrievalAndDeletionOfMissingIds() {

        Assert.assertEquals(0, orderService.getLiveOrders().size());

        long rubbishId = 12345L;
        Assert.assertNull(orderService.getOrder(rubbishId));
        Assert.assertNull(orderService.removeOrder(rubbishId));
    }
}
