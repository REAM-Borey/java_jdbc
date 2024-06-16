package model.dao;

import model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDao {
    @Override
    public int addNewProduct(Product product) {
        String sql = """
                INSERT INTO "product"(product_name,product_code,is_deleted,imported_at,expired_at,product_description)
                VALUES (?,?,?,?,?,?)
                """;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "1410"
        );
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getProductCode());
            preparedStatement.setBoolean(3, product.getIsDeleted());
            preparedStatement.setDate(4, product.getImportDated());
            preparedStatement.setDate(5,product.getExpiredDate());
            preparedStatement.setString(6, product.getDescription());
            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected>0){
                System.out.println("product has been added succesfully");
            }


        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;

    }

    @Override
    public int deleteProductById(Integer id) {
        String sql = """
                DELETE FROM"product"
                WHERE id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "1410"
                );
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql);
        ) {
            Product product = searchProductById(id);
            if (product==null){
                System.out.println("cannot delete");
            }else {
                preparedStatement.setInt(1,id);
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println("delete succesfully");
            }


        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int updateProductById(Integer id) {
        String sql = """
                UPDATE "product"
                SET product_name = ?, product_description = ?
                WHERE id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "1410"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            Product product = searchProductById(id);
            if (product!=null){
                System.out.println("Insert name: ");
                preparedStatement.setString(1, new Scanner(System.in).next());
                System.out.println("Insert des: ");
                preparedStatement.setString(2, new Scanner(System.in).next());
                preparedStatement.setInt(3,id);
            }else {
                System.out.println("product not found");
            }
            int rowAffected = preparedStatement.executeUpdate();
            String message = rowAffected > 0 ? "update success":"update fail";
            System.out.println(message);

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Product searchProductById(Integer id) {
        String sql = """
                SELECT *FROM "product"
                WHERE id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "1410"
                );
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            while (resultSet.next()){
                product = Product
                        .builder()
                        .id(resultSet.getInt("id"))
                        .productName(resultSet.getString("product_name"))
                        .productCode(resultSet.getString("product_code"))
                        .isDeleted(resultSet.getBoolean("is_deleted"))
                        .importDated(resultSet.getDate("imported_at"))
                        .expiredDate(resultSet.getDate("expired_at"))
                        .description(resultSet.getString("product_description"))
                        .build();
            }
            return product;

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> queryAllProduct() {
        String sql = """
               SELECT * FROM "product"
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "1410"
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()){
                productList.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("product_name"),
                                resultSet.getString("product_code"),
                                resultSet.getBoolean("is_deleted"),
                                resultSet.getDate("imported_at"),
                                resultSet.getDate("expired_at"),
                                resultSet.getString("product_description")
                        )
                );
            }
            return productList;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }

}
