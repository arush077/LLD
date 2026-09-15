import java.util.ArrayList;
import java.util.List;


class ParkingLot{
    ArrayList<Floor> floorList;

    PaymentStrategy paymentStrategy;
    PricingStrategy pricingStrategy;

    ParkingLot(PaymentStrategy paymentStrategy, PricingStrategy pricingStrategy){
        this.pricingStrategy = pricingStrategy;
       this.paymentStrategy = paymentStrategy;
    }


    public ParkingSpot findParkingSpotAcrossAllFloors(){
        //Go to every floor and check if a parking spot is available

        for(int i=0;i<floorList.size();i++){
            ParkingSpot possibleAvailableParkingSpot = floorList.get(i).getAvailableParkingSpot();
                if(possibleAvailableParkingSpot != null){
                    //parkingSpot found 
                    System.out.println("Parking spot found");
                    return possibleAvailableParkingSpot;
                }
        }

        System.out.println("Parking spot not found");
        return null;
    }

    
    public void parkVehicle(Vehicle vehicle){
        ParkingSpot parkingSpot = findParkingSpotAcrossAllFloors();
        if(parkingSpot == null){
            System.out.println("Cant park, no parking spot found");
        }

        parkingSpot.isAvailable = false;
        parkingSpot.vehicle = vehicle;
    }

    void exitParkingSpot(ParkingSpot parkingSpot){
        parkingSpot.isAvailable = true;
        parkingSpot.vehicle = null;
        
    }

    double pay(VehicleType vehicleType, int duration){
        //Calulating the amnt using pricing strat
        double amount = pricingStrategy.calculatePrice(vehicleType, duration);

        //Processing the payment using payment strat
        paymentStrategy.processPayment();

        return amount;
    }
}

class Floor{
    List<ParkingSpot> parkingSpotList;

    public ParkingSpot getAvailableParkingSpot(){
        for(int i=0;i<parkingSpotList.size();i++){
            if(parkingSpotList.get(i).isAvailable == true ){ // C++ me NULL tha, Java me null he
                return parkingSpotList.get(i);
            }
        }
        return null;
    }
};

class ParkingSpot{
    Vehicle vehicle;
    boolean isAvailable;
}

abstract class Vehicle{
    Ticket tikcet;
    abstract int cost();
}

enum VehicleType{
    CAR,
    BIKE,
    TRUCK
}

class Car extends Vehicle{
    int vehicleNo;
    VehicleType vehicleType;

    private int duration;

    public Car(int vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public int cost(){
        return duration*10;
    }
}

class Bike extends Vehicle{
    private int duration;

    Bike(int duration){
        this.duration = duration;
    }

    public int cost(){
        return duration*5;
    }
}


class Ticket{
    Vehicle vehicle;
    int duration;
    int amount;
}


interface PaymentStrategy{
    public void processPayment();
}

class UPIPayment implements PaymentStrategy{
    public void processPayment(){
        System.out.println("Processing using UPI");
    }
}

class CardPayment implements PaymentStrategy{
    public void processPayment(){
        System.out.println("Processing using Card");
    }
}

class CashPayment implements PaymentStrategy{
    public void processPayment(){
        System.out.println("Processing using Cash");
    }
}



interface PricingStrategy{
    public double calculatePrice(VehicleType vehicleType,int duration);
}

class BikeCost implements PricingStrategy{
    public double calculatePrice(VehicleType vehicleType,int duration){
       return duration*1;
    }
}

class CarCost implements PricingStrategy{
    public double calculatePrice(VehicleType vehicleType,int duration){
        return duration*2;
    }
}

class TruckCost implements PricingStrategy{
    public double calculatePrice(VehicleType vehicleType,int duration){
        return duration*3;
    }
}



class Main{
    public static void main(String[] args){
        Car merc = new Car(10);

    }
}