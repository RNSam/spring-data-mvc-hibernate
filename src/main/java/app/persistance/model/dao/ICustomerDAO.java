package app.persistance.model.dao;

import app.persistance.model.entities.Customer;

import java.util.List;

public interface ICustomerDAO {

  List<Customer> getAll();

}
