import java.util.ArrayList;

class ParkingLot{
    ArrayList<Floor> Floors;
}

class Floor{
    ArrayList<ParkingSpot> parkingSpots;
};

class ParkingSpot{
    Vehicle vehicle;
}


abstract class Vehicle{
    abstract int cost();
}

enum VehicleType{
    CAR,
    BIKE
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

}


class Main{
    public static void main(String[] args){
        Car merc = new Car(10);

    }
}