import java.util.Scanner;

interface Vehicle {
    public void createVehicle();
}

class Car implements Vehicle {
    public void createVehicle() {
        System.out.println("Creating a car");
    }
}

class Bike implements Vehicle {
    public void createVehicle() {
        System.out.println("Creating Bike");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String vehicleType = sc.next();

        Vehicle vehicle;

        // Ye logic client code ko nahi likhna padega without Factory Pattern
        if (vehicleType.equals("Car")) {
            vehicle = new Car();
        } else if (vehicleType.equals("Bike")) {
            vehicle = new Bike();
        } else {
            System.out.println("Invalid vehicle type");
            return;
        }

        System.out.println("user chose a " + vehicle.getClass());
        vehicle.createVehicle();
    }
}