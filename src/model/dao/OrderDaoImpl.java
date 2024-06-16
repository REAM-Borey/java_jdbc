package model.dao;

import model.entity.Customers;
import model.entity.Orders;
import model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderDaoImpl implements OrderDao {

    @Override
    public int addNewOrder(Orders orders) {
        String sql = """
                INSERT INTO "order" (order_name,order_description,cus_id,ordered_at)
                VALUE (?,?,?,?)
                """;
        String sql1 = """
                INSERT INTO "product_order"
                VALUE (?,?)
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres","postgres","123"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                ){
            preparedStatement.setString(1, orders.getOrderName());
            preparedStatement.setString(2,orders.getOrderDescription());
            preparedStatement.setInt(3,orders.getCustomer().getId());
            preparedStatement.setDate(4,orders.getOrderDate());
            int rowAffected = preparedStatement.executeUpdate();
            String message = rowAffected > 0 ? "insert Order Successfully":"Insert Order fails";
            for(Product product: orders.getProductList()){
                preparedStatement1.setInt(1,product.getId());
                preparedStatement1.setInt(2,orders.getId());
            }
            int rowAffect1 = preparedStatement1.executeUpdate();
            if(rowAffect1 >0){
                System.out.println("Product has been ordered");
            }else{
                System.out.println("Product out of rand");
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int deletedOrderById(Integer id) {
        String sql = """
                DELETE FROM "order"
                WHERE id = ? 
                """;
        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres","postgres","123"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            Orders orders = searchOrderById(id);
            if(orders !=null){
                preparedStatement.setInt(1,id);
                int rowAffected = preparedStatement.executeUpdate();
                String  message = rowAffected >0 ?"Deleted Successfully":"Deleted failed";
                return rowAffected;
            }else{
                System.out.println("Not found ");
            }



        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int updateOrderById(Integer id) {
        String sql = """
                UPDATE "order"
                SET order_name =?,order_description=?
                WHERE id =?
                """;
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres","postgres","123"
            );
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            Orders orders = searchOrderById(id);
            if(orders !=null){
                System.out.println("[+] Insert order Name=");
                preparedStatement.setString(1,new Scanner(System.in).nextLine());
                System.out.println("[+] Insert order_description:");
                preparedStatement.setString(2,new Scanner(System.in).nextLine());
                preparedStatement.setInt(3,id);
                int rowAffect = preparedStatement.executeUpdate();
                if (rowAffect > 0) {
                    System.out.println("Update Successfully");
                }else{
                    System.out.println("Update fail");
                }
            }else{
                System.out.println("Not found");
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Orders searchOrderById(Integer id) {
        String sql = """
                SELECT * FROM "order"
                WHERE id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres","postgres","123"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Orders orders = null;
            while (resultSet.next()){
                orders = Orders.builder()
                        .id(resultSet.getInt("id"))
                        .orderName(resultSet.getString("order_name"))
                        .orderDescription(resultSet.getString("order_description"))
                        .customer(
                                Customers.builder()
                                        .id(resultSet.getInt("cus_id"))
                                        .build())
                        .orderDate(resultSet.getDate("order_at"))
                        .build();
            }
            return orders;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Orders> queryAllOrders() {
        String sql = """
                SELECT * FROM "order"
                INNER JOIN customer c ON "orders".cus_id = c.id
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres","postgres","123"
                );
                Statement statement = connection.createStatement();
                ){
            ResultSet resultSet = statement.executeQuery(sql);
            List<Orders> ordersList = new ArrayList<>();
            while (resultSet.next()){
                ordersList.add(Orders.builder()
                        .id(resultSet.getInt("id"))
                                .orderName(resultSet.getString("order_name"))
                                .orderDescription(resultSet.getString("order_description"))
                                .orderDate(resultSet.getDate("order_at"))
                                .customer(Customers.builder()
                                        .id(resultSet.getInt("cus_id"))
                                        .name(resultSet.getString("name"))
                                        .email(resultSet.getString("email"))
                                        .password(resultSet.getString("password"))
                                        .isDeleted(resultSet.getBoolean("is_deleted"))
                                        .createdDate(resultSet.getDate("created_date"))
                                        .build())
                        .build());
            }
            return ordersList;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }
}
