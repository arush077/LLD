import java.util.Scanner;

interfa Vehicle{
    void createVehicle();
}

class Car extends Vehicle{
    void createVehicle(){
        System.out.println("Creating a car");
    }

};

class Bike extends Vehicle{
    void createVehicle(){
        System.out.println("Creating Bike");
    }    
};

class VehicleFactory{

    static Vehicle vehicle;
    static Vehicle getVehicle(String vehicleType){
        if(vehicleType == "Car"){vehicle = new Car();}
        else if(vehicleType == "Bike"){vehicle = new Bike();}

        return vehicle;
    }
};


public class Main{
    public static void main(String arg[]){
    // Taking user input lmao
    Scanner sc = new Scanner(System.in);
    String vehicleType = sc.next();

    
    
    }
}