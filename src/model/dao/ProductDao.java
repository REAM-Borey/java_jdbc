package model.dao;

import model.entity.Product;

import java.util.List;

public interface ProductDao {
    int addNewProduct(Product product);
    int deleteProductById(Integer id);
    int updateProductById(Integer id);
    Product searchProductById(Integer id);
    List<Product> queryAllProduct();
}
