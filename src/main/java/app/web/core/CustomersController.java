package app.web.core;

import app.persistance.model.dao.ICustomerDAO;
import app.persistance.model.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class CustomersController {

  private ICustomerDAO service;

  @Autowired(required=true)
  @Qualifier(value="customerDAO")
  public void setCustomerService(ICustomerDAO srv){
    this.service = srv;
  }

  @RequestMapping(path= "/customers",method= RequestMethod.GET)
  public ModelAndView getCustomers(Model model){
    ModelAndView mav = new ModelAndView("customers") ;
    List<Customer> items = service.getAll();
    mav.addObject("customerList", items);
    return mav;
  }
}

