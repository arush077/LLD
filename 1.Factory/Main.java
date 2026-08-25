import java.util.Scanner;

interface Vehicle{
    public void createVehicle();
}

class Car implements Vehicle{
    public void createVehicle(){
        System.out.println("Creating a car");
    }
}

class Bike implements Vehicle{
    public void createVehicle(){
        System.out.println("Creating Bike");
    }    
}

class VehicleFactory {
     Vehicle vehicle;
     Vehicle getVehicle(String vehicleType) {
        if (vehicleType.equals("Car")) {
            vehicle = new Car();
        }
        else if (vehicleType.equals("Bike")) {
            vehicle = new Bike();
        }
        return vehicle;
    }
}

// Acts as main class 
public class Main{
    public static void main(String arg[]){
    // Taking user input lmao
    Scanner sc = new Scanner(System.in);
    String vehicleType = sc.next();
    // Without factory, there will be multiple if else statements in the client code
    // if (vehicleType.equals("Car")) { vehicle = new Car(); }
    // else if (vehicleType.equals("Bike")){ vehicle = new Bike(); }
    
    VehicleFactory vehicleFactory = new VehicleFactory(); //Making a factory object 
    Vehicle vehicle =  vehicleFactory.getVehicle(vehicleType);
    System.out.println("user chose a " + vehicle.getClass()); 
    }
}