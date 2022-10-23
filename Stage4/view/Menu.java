package carsharing.view;

import carsharing.database.CarDaoDatabaseImpl;
import carsharing.database.CompanyDaoDatabaseImpl;
import carsharing.database.CustomerDaoDatabaseImpl;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.util.List;
import java.util.Scanner;

public class Menu {
        
        public static final Scanner scanner = new Scanner(System.in);

        public static void managementCar(CarDaoDatabaseImpl carDaoH2, CompanyDaoDatabaseImpl companyDaoH2, int companyId) {
            Scanner scanner = new Scanner(System.in);
            if (companyId > 0) {
                String companyName = companyDaoH2.getAllCompanies().get(companyId - 1).getName();
                System.out.println("'" + companyName + "' company");
            } else {
                return;
            }

            for(;;) {
                if (companyDaoH2.getAllCompanies().size() >= companyId) {
                    PrintMenu.companyMenu();
                    switch (userChoice()) {
                        case 0:
                            return;
                        case 1:
                            if(carDaoH2.getCars(companyId).size() == 0) {
                                System.out.println("The car list is empty!\n");
                            } else {
                                System.out.println("Car list:");
                                int id = 1;
                                for (Car car : carDaoH2.getCars(companyId)) {
                                    System.out.println(id + ". " + car.getName());
                                    id++;
                                }
                                System.out.println();
                            }
                            break;
                        case 2:
                            System.out.println("Enter the car name:");
                            carDaoH2.createCar(scanner.nextLine(), companyId);
                            System.out.println("The car was added!\n");
                            break;
                    }
                } else {
                    return;
                }
            }
        }


        public static void managementCompany(CompanyDaoDatabaseImpl companyDaoH2, CarDaoDatabaseImpl carDaoH2) {
            Scanner scanner = new Scanner(System.in);
            for (;;) {
                PrintMenu.managerMenu();
                switch (userChoice()) {
                    case 0:
                        return;
                    case 1:
                        if(companyDaoH2.getAllCompanies().size() == 0) {
                            System.out.println("The company list is empty!\n");
                        } else {
                            System.out.println("Choose the company:");
                            companyDaoH2.getAllCompanies()
                                    .forEach(System.out::println);
                            System.out.println("0. Back");
                            managementCar(carDaoH2, companyDaoH2, userChoice());
                        }
                        break;
                    case 2:
                        System.out.println("Enter the company name:");
                        companyDaoH2.createCompany(scanner.nextLine());
                        System.out.println("The company was created!\n");
                        break;
                }
            }
        }

        public static void managementCreateCustomer(CustomerDaoDatabaseImpl customerDaoH2) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the customer name:");
            customerDaoH2.createCustomer(scanner.nextLine());
            System.out.println("The customer was added!\n");
        }

        public static void managementCustomerMenu(CustomerDaoDatabaseImpl customerDaoH2,
                                                  CompanyDaoDatabaseImpl companyDaoH2, CarDaoDatabaseImpl carDaoH2) {
            if(customerDaoH2.getAllCustomers().size() == 0) {
                System.out.println("The customer list is empty!\n");
            } else {
                System.out.println("Choose a customer:");
                customerDaoH2.getAllCustomers()
                        .forEach(System.out::println);
                System.out.println("0. Back\n");
                Customer customer = customerDaoH2.getCustomer(userChoice());
                managementCustomer(customerDaoH2, companyDaoH2, carDaoH2, customer);
            }
        }

        public static void managementCustomer(CustomerDaoDatabaseImpl customerDaoH2,
                                              CompanyDaoDatabaseImpl companyDaoH2, CarDaoDatabaseImpl carDaoH2, Customer customer) {
            for(;;) {
                PrintMenu.customerMenu();
                switch (userChoice()) {
                    case 0:
                        return;
                    case 1:
                        if(companyDaoH2.getAllCompanies().size() == 0) {
                            System.out.println("The company list is empty!\n");
                        } else {
                            if (customer.getCarId() > 0) {
                                System.out.println("You've already rented a car!");
                                break;
                            } else {
                                System.out.println("Choose the company:");
                                companyDaoH2.getAllCompanies()
                                        .forEach(System.out::println);
                                System.out.println("0. Back");
                                int companyId = userChoice();
                                if (companyId == 0) {
                                    System.out.println();
                                    break;
                                }
                                Company company = companyDaoH2.getCompany(companyId);
                                managementRentCar(carDaoH2, customerDaoH2, company, customer);
                            }
                        }
                        break;
                    case 2:
                        customer = customerDaoH2.getCustomer(customer.getId());
                        if (customer.getCarId() == 0) {
                            System.out.println("You didn't rent a car!\n");
                        } else {
                            returnCar(customerDaoH2, customer);
                        }
                        break;
                    case 3:
                        customer = customerDaoH2.getCustomer(customer.getId());
                        if (customer.getCarId() == 0) {
                            System.out.println("You didn't rent a car!\n");
                        } else {
                            Car car = carDaoH2.getCar(customer.getCarId());
                            Company company = companyDaoH2.getCompany(car.getCompanyId());
                            System.out.println("Your rented car:");
                            System.out.println(car.getName());
                            System.out.println("Company:");
                            System.out.println(company.getName());
                            System.out.println();
                        }
                }
            }

        }

        private static void managementRentCar(CarDaoDatabaseImpl carDaoH2,
                                              CustomerDaoDatabaseImpl customerDaoH2,
                                              Company company, Customer customer) {
            List<Integer> rentedCarsId = customerDaoH2.getAllRentedCars();
            List<Car> carList = carDaoH2.getCars(company.getId())
                    .stream()
                    .filter((c) -> !rentedCarsId.contains(c.getId())).toList();
            if (carList.size() == 0) {
                System.out.println("The car list is empty!\n");
            } else {

                System.out.println("Choose a car:");
                int id = 1;
                for (Car car : carList) {
                    System.out.println(id + ". " + car.getName());
                    id++;
                }
                System.out.println("0. Back");
                int carId = userChoice();
                if (carId == 0) {
                    return;
                } else {

                    if (carId > carList.size() + 1 || carId < 0) {
                        return;
                    } else {
                        Car car = carList.get(carId - 1);
                        rentCar(customerDaoH2, car, customer);
                    }

                }

            }
        }

        private static void rentCar(CustomerDaoDatabaseImpl customerDaoH2, Car car, Customer customer) {
            customerDaoH2.rentCar(car.getId(), customer.getId());
            System.out.println("You rented '" + car.getName() + "'");
            System.out.println();
        }

        private static void returnCar(CustomerDaoDatabaseImpl customerDaoH2, Customer customer) {
            customerDaoH2.returnCar(customer.getCarId(), customer.getId());
            System.out.println("You've returned a rented car!\n");
        }

        public static int userChoice() {
            return scanner.nextInt();
        }

    }