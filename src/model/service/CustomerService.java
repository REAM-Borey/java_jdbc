package model.service;

import model.dao.CustomerDao;
import model.entity.Customers;

import java.util.List;

public interface CustomerService {
    public void addNewCustomer();
    List<CustomerDao> queryAllCustomers();
    CustomerDao updateCustomerById(Integer id);
    CustomerDao deleteCustomerById(Integer id);

}
