package com.yoo.controller;

import com.yoo.model.Employee;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.util.List;

public class EmployeeController {
    public static void main(String[] args) {
        //Using .env version:
        String dbPassword = System.getenv("hibernate_password");
        String dbUsername = System.getenv("hibernate_username");
        String dbURL = System.getenv("dbURL");

        Configuration configuration = new Configuration();
        //setting password using .env file:
        configuration.setProperty("hibernate.connection.password", dbPassword);
        configuration.setProperty("hibernate.connection.username", dbUsername);
        configuration.setProperty("hibernate.connection.url", dbURL);

        //adding mapping class:
        configuration.addAnnotatedClass(Employee.class);
        configuration.configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();


//          findEmployeeByName();
        //  findEmployeeById();
        //  showOfficeCodesAsDepartment();

        factory.close();
        session.close();
    }

    public static void findEmployeeByName() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

//------------  Hibernate Named Query   -------------

        TypedQuery<Employee>query = session.createNamedQuery("findEmployeeByName", Employee.class);
        query.setParameter("name","Tom Thele");
        List<Employee> employees=query.getResultList();
        System.out.printf("%-10s%-22s%-14s%-14s%-16s%-10s%-10s%s%n", "ID","Name","Salary", "Job","AddressLine","Zipcode","city","startDate");

        for (Employee e : employees) {
            System.out.printf("%-11d%-22s%-14d%-14s%-16s%-10s%-10s%s%n",e.getId(),e.getName(),e.getSalary(),e.getJob(),e.getAddressLine(),e.getZipcode(),e.getCity(),e.getStartDate());
        }

        factory.close();
        session.close();
    }
    public static void findEmployeeById() {   SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
//------------  Hibernate Named Query   -------------
        TypedQuery<Object[]> query = session.createNamedQuery("getEmployeeById", Object[].class);
        query.setParameter("id",3);
        List<Object[]> employee=  query.getResultList();
        System.out.printf("%-10s%-12s%s%n", "Name","Salary", "Job");

        for (Object[] e : employee) {
            System.out.printf("%-11s%-12s%s%n",e[0],e[1],e[2]);
        }
        factory.close();
        session.close();
    }
    public static void showOfficeCodesAsDepartment() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
//------------  Hibernate Named Query   -------------
        TypedQuery<Object[]> query = session.createNamedQuery("employeeDeptAlias", Object[].class);
        List<Object[]> employees  =  query.getResultList();

        System.out.printf("%-15s%-17s%s%n", "OfficeCode","Dep Name","Employee Name");

        for (Object[] e : employees) {
            System.out.printf(" %-15s%-17s%s%n",e[1],e[3],e[2]);
        }

        factory.close();
        session.close();
    }


}
