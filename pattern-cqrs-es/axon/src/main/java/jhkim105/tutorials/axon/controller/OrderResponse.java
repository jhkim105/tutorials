package jhkim105.tutorials.axon.controller;

import static jhkim105.tutorials.axon.controller.OrderStatusResponse.toResponse;

import java.util.Map;
import jhkim105.tutorials.axon.queries.Order;
import lombok.Getter;

@Getter
public class OrderResponse {

    private String orderId;
    private Map<String, Integer> products;
    private OrderStatusResponse orderStatus;

    OrderResponse(Order order) {
        this.orderId = order.getId();
        this.products = order.getProducts();
        this.orderStatus = toResponse(order.getOrderStatus());
    }


}
