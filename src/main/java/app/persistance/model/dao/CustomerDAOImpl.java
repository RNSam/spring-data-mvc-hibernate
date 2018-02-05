package app.persistance.model.dao;

import app.persistance.model.entities.Customer;
import app.persistance.model.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerDAO")
@Repository
public class CustomerDAOImpl implements ICustomerDAO {

  public CustomerDAOImpl() {
  }

  @Autowired
  CustomerRepo repository;

  @Override public List<Customer> getAll() {
    return repository.findAll();
  }

}
