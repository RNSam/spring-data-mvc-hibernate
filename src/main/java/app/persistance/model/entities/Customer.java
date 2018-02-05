package app.persistance.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer")
public class Customer {

  @Id
  @Column(name = "CustomerId")
  private Long id;

  @Column(name = "CustomerName")
  private String name;

  @Column(name = "Created")
  private String created;

  @Column(name = "Description")
  private String desc;

  public Customer() {
  }

  public Customer(Long id, String name, String created, String desc) {
    this.id = id;
    this.name = name;
    this.created = created;
    this.desc = desc;
  }

  public String getName() {
    return name;
  }

}
