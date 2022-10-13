package jhkim105.tutorials.axon.controller;

import jhkim105.tutorials.axon.queries.OrderStatus;

public enum OrderStatusResponse {
    CREATED, CONFIRMED, SHIPPED, UNKNOWN;

    static OrderStatusResponse toResponse(OrderStatus status) {
        for (OrderStatusResponse response : values()) {
            if (response.toString().equals(status.toString())) {
                return response;
            }
        }
        return UNKNOWN;
    }
}
