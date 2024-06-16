package model.dao;

import model.entity.Orders;

import java.util.List;

public interface OrderDao {
    int addNewOrder(Orders orders);
    int deletedOrderById(Integer id);
    int updateOrderById(Integer id);
    Orders searchOrderById(Integer id);
    List<Orders> queryAllOrders();
}
