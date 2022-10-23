package carsharing.dao;

import carsharing.entity.Car;

import java.util.List;

public interface CarDao {
    List<Car> getCars(int companyId);
    void createCar(String name, int companyId);
}