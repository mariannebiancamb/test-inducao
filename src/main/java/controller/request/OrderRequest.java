package controller.request;

import entities.Order;

import java.util.List;

public class OrderRequest {

    private final List<Order> orderList;

    public OrderRequest(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

}
