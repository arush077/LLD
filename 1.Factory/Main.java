import java.util.Scanner;

interface Vehicle {
    void drive();
}

class Car implements Vehicle {
    public void drive() {
        System.out.println("Driving a car");
    }
}

class Bike implements Vehicle {
    public void drive() {
        System.out.println("Riding a bike");
    }
}

class VehicleFactory {

    Vehicle createVehicle(String vehicleType) {
        if (vehicleType.equals("Car")) {
            return new Car();
        } else if (vehicleType.equals("Bike")) {
            return new Bike();
        }
        throw new IllegalArgumentException("Invalid vehicle type");
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String vehicleType = sc.next();

        //Pehle factory ka object bana and then factory.create() karna he
        VehicleFactory vehicleFactory = new VehicleFactory();
        Vehicle vehicle = vehicleFactory.createVehicle(vehicleType);

        System.out.println("User chose a " + vehicle.getClass().getSimpleName());
        vehicle.drive();

        sc.close();
    }
}