package model.service;

import model.dao.CustomerDao;

import java.util.List;

public class CustomerServiceImpl implements CustomerService{
    @Override
    public void addNewCustomer() {

    }

    @Override
    public List<CustomerDao> queryAllCustomers() {
        return List.of();
    }

    @Override
    public CustomerDao updateCustomerById(Integer id) {
        return null;
    }

    @Override
    public CustomerDao deleteCustomerById(Integer id) {
        return null;
    }
}
