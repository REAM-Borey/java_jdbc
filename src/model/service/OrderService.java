package model.service;

import model.entity.Orders;

public interface OrderService {
    Orders AddOrder();
    Orders UpdateOrder();
    Orders DeleteOrder();
}
