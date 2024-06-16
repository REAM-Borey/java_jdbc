package model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.entity.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public List<Customers> queryAllCustomers() {
        String sql = """
                select * from "customer"
                """;
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
            List<Customers> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customers( resultSet.getInt("id"),resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"),resultSet.getBoolean("is_deleted"),resultSet.getDate("creat_at")));
            }
            return new ArrayList<>();
        }catch (SQLException sqlException){
            System.out.printf(sqlException.getMessage());
        }
        return List.of();
    }

    @Override
    public int updateCustomersById(Integer id) {
        String sql = """
                UPDATE customer SET
                name =?,email = ?,WHERE id =?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres","postgres","123"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
        Customers customers = searchCustomersById(id);
        if(customers != null){
            System.out.print("[+] Insert Name=");
            preparedStatement.setString(1,new Scanner(System.in).next());
            System.out.println("[+] Insert Email=");
            preparedStatement.setString(2,new Scanner(System.in).next());
            preparedStatement.setInt(3,id);
        }
        int rowsAffected = preparedStatement.executeUpdate();
        String message = rowsAffected >0 ? "Update successful" : "Update failed";
            System.out.println(message);
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteCustomersById(Integer id) {
        String sql = """
                delete from customers where id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres","postgres","123"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            Customers customers = searchCustomersById(id);
            if (customers == null){
                System.out.println("Can't delete");
            }else{
                preparedStatement.setInt(1,id);
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Deleted successfully");
            }
            preparedStatement.setInt(1, id);

        }catch (SQLException sqlException){
            System.out.printf(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int addNewCustomers(Customers customers) {
        String sql = """
                INSERT INTO "customer" (name,email,password,is_deleted,created_date)
                VALUES (?,?,?,?,?)
                """;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "123"
        );
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, customers.getName());
            preparedStatement.setString(2, customers.getEmail());
            preparedStatement.setString(3, customers.getPassword());
            preparedStatement.setBoolean(4,customers.getIsDeleted());
            preparedStatement.setDate(5,customers.getCreatedDate());
            int RowAffected =preparedStatement.executeUpdate();
            if(RowAffected > 0){
                System.out.println("Customer has been updated successfully");
            }
        }catch (SQLException sqlException){
            System.out.printf(sqlException.getMessage());

        }
        return 0;
    }

    @Override
    public Customers searchCustomersById(Integer id) {
        String sql = """
                select * from "customer"
                where id=?
                """;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres","postgres","123");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customers customers = null;
        while (resultSet.next()) {
            customers = Customers
                    .builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .isDeleted(resultSet.getBoolean("is_deleted"))
                    .createdDate(resultSet.getDate("created_date"))
                    .build();
        }
        }catch (SQLException sqlException){
            System.out.printf(sqlException.getMessage());
        }
        return null;
    }


}
