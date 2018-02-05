package app.config;

import app.persistance.model.dao.CustomerDAOImpl;
import app.persistance.model.entities.Customer;
import app.persistance.model.repository.CustomerRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {CustomerRepo.class, Customer.class, CustomerDAOImpl.class})
@PropertySource({ "classpath:persistence-mysql.properties" })
@ComponentScan({ "app.persistence", "app.persistance.model.dao", "app.persistance.model.entities", "app.persistance.model.repository" })
public class PersistenceConfig {

  @Autowired
  private Environment env;

  @Bean
  public DataSource restDataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:schema.sql")
        .addScript("classpath:test-data.sql")
        .build();
    /* BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.pass"));*/

    //return dataSource;
  }

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setDataSource(dataSource);
    lef.setJpaVendorAdapter(jpaVendorAdapter);
    //lef.setJpaProperties(hibernateProperties());
    lef.setPackagesToScan("app.persistance.model.dao", "app.persistance.model.entities", "app.persistance.model.repository");
    lef.setPersistenceUnitName("MyPU");
    return lef;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(true);
    hibernateJpaVendorAdapter.setGenerateDdl(true);
    hibernateJpaVendorAdapter.setDatabase(Database.H2);
    return hibernateJpaVendorAdapter;
  }

  @Bean(name = "transactionManager")
  public JpaTransactionManager jpaTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManager) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManager);
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  //   Hibernate

  /*@Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(restDataSource());
    sessionFactory.setPackagesToScan(
        new String[] { "app.persistence.model.entities" });
    sessionFactory.setHibernateProperties(hibernateProperties());

    return sessionFactory;
  }*/

 /* @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(
      SessionFactory sessionFactory) {

    HibernateTransactionManager txManager
        = new HibernateTransactionManager();
    txManager.setSessionFactory(sessionFactory);

    return txManager;
  }*/

 Properties hibernateProperties() {
    return new Properties() {
      {
        setProperty("hibernate.hbm2ddl.auto",
            env.getProperty("hibernate.hbm2ddl.auto"));
        setProperty("hibernate.dialect",
            env.getProperty("hibernate.dialect"));
        setProperty("hibernate.globally_quoted_identifiers",
            "true");
      }
    };
  }

}
