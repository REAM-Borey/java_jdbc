package model.dao;

import model.entity.Customers;

import java.util.List;
public interface CustomerDao {
    List<Customers> queryAllCustomers();
    int updateCustomersById(Integer id);
    int deleteCustomersById(Integer id);
    int addNewCustomers(Customers customers);
    Customers searchCustomersById(Integer id);
}
