import model.dao.CustomerDao;
import model.dao.CustomerDaoImpl;
import model.dao.OrderDaoImpl;
import model.entity.Customers;
import model.entity.Orders;
import model.entity.Product;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        new OrderDaoImpl()
                .addNewOrder(Orders.builder()
                        .id(1)
                        .orderName("java")
                        .orderDescription("James Gosling")
                        .orderDate(Date.valueOf(LocalDate.now()))
                        .customer(Customers.builder()
                                .id(1)
                                .build())
                        .productList(new ArrayList<>(List.of(
                                Product.builder()
                                        .id(2)
                                        .build()
                        )))
                        .build());
        }
    }