package carsharing.dao;

import carsharing.entity.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    void createCustomer(String name);
    int myRentedCarId(int customerId);
    void rentCar(int carId, int customerId);
    void returnCar(int carId, int customerId);
    Customer getCustomer(int customerId);
    List<Integer> getAllRentedCars();
}
