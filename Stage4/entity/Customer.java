package carsharing.entity;

public class Customer {
    private int id;
    private String name;
    private int carId;


    public Customer(int id, String name, int carId) {
        this.id = id;
        this.name = name;
        this.carId = carId;

    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.carId = 0;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCarId() {
        return carId;
    }



    @Override
    public String toString(){
        return this.getId() + ". " + this.getName();
    }
}
