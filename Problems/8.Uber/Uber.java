import java.util.List;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        
        Driver Arush = new Driver(1, "Arushnull", new Location(100, 10), new Vehicle("MH43CS3112", VehicleType.CAB),DriverStatus.AVAILABLE);
        Driver Nidhish = new Driver(1, "nidhish", new Location(20, 73), new Vehicle("MH43CS3112", VehicleType.AUTO),DriverStatus.AVAILABLE);
        
        DriverService driverService = new DriverService();
        driverService.addDriver(Arush);
        driverService.addDriver(Nidhish);
        
        RideService rideService = new RideService(new NearestDriverStrategy(),driverService);

        rideService.requestRide(new Rider(10,"Random boi"),Arush,new Location(19.1354908, 72.992191),new Location(19.1245692,72.9170748 ));
    }
}

class Location{
    double latitude;
    double longitude;
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
}

class Ride{
    Rider rider;
    Driver driver;
    Location src;
    Location destination;
    double amount;
    RideStatus RideStatus;
    public Ride(Rider rider, Driver driver, Location src, Location destination, double amount, RideStatus rideStatus) {
        this.rider = rider;
        this.driver = driver;
        this.src = src;
        this.destination = destination;
        this.amount = amount;
        RideStatus = rideStatus;
    }
}

enum RideStatus{
    REQUESTED,
    DRIVER_ASSIGNED,
    IN_PROGRESS,
    COMPLETED
}


class Rider{
    int id;
    String name;

    public Rider(int id, String name) {
        this.id = id;
        this.name = name;
    }
}


class DriverService{
    List<Driver> driverList;

    void addDriver(Driver driver){
        driverList = new ArrayList<>();
        driverList.add(driver);
    }

    List<Driver> getAvailableDriver(){
        List<Driver> availableDriver = new ArrayList<>();
        for(int i=0;i<driverList.size();i++){
            if(driverList.get(i).driverStatus == DriverStatus.AVAILABLE){
                availableDriver.add(driverList.get(i));
            }
        }
        return availableDriver;
    }


    List<Driver> getAvailableDriverbyVehicleType(VehicleType vehicleType){
        List<Driver> availableDriverbyVehicleType = new ArrayList<>();
        for(int i=0;i<driverList.size();i++){
            if(driverList.get(i).driverStatus == DriverStatus.AVAILABLE && driverList.get(i).vehicle.vehicleType == vehicleType){
                availableDriverbyVehicleType.add(driverList.get(i));
            }
        }
        return availableDriverbyVehicleType;
    }


    void updateStatus(Driver driver, DriverStatus status){
        driver.driverStatus = status;
    }

}

class Driver{
    int id;
    String name;
    Location driverLocation;
    Vehicle vehicle;
    DriverStatus driverStatus;
    public Driver(int id, String name, Location driverLocation, Vehicle vehicle, DriverStatus driverStatus) {
        this.id = id;
        this.name = name;
        this.driverLocation = driverLocation;
        this.vehicle = vehicle;
        this.driverStatus = driverStatus;
    }
}

enum DriverStatus{
    AVAILABLE,
    NOT_AVAILABLE
}

class Vehicle{
    String numPlate;
    VehicleType vehicleType;
    public Vehicle(String numPlate, VehicleType vehicleType) {
        this.numPlate = numPlate;
        this.vehicleType = vehicleType;
    }

    
}

enum VehicleType{
    CAB, 
    AUTO,
    BIKE
}


interface PricingStrategy{
    double calculatePrice(Location src, Location dest);
}

class CabPricingStrategy implements PricingStrategy{
    public double calculatePrice(Location src, Location dest){
        return ((src.latitude - dest.latitude) + (src.longitude - dest.longitude))*2000 + 100;
    }
}

class AutoPricingStrategy implements PricingStrategy{
    public double calculatePrice(Location src, Location dest){
        return ((src.latitude - dest.latitude) + (src.longitude - dest.longitude))*1;
    }
}

class BikePricingStrategy implements PricingStrategy{
    public double calculatePrice(Location src, Location dest){
        return ((src.latitude - dest.latitude) + (src.longitude - dest.longitude))*0.5;
    }
}


interface MatchingStrategy{
    Driver findDriver(List<Driver> availableDriverList, Location src);
}


class NearestDriverStrategy implements MatchingStrategy{
    public Driver findDriver(List<Driver> availableDriverList, Location src){

        double mini = Integer.MAX_VALUE;
        int mini_index = 0;
        for(int i=0;i<availableDriverList.size();i++){
            Driver driver = availableDriverList.get(i);
            Location driverLocation = driver.driverLocation;
            double difference =  (driverLocation.latitude - src.latitude) + (driverLocation.longitude - src.longitude);
            if(mini> difference){
                mini = difference;
                mini_index = i;
            }
        }

        return availableDriverList.get(mini_index);
    }
}


class RideService{
    PricingStrategy pricingStrategy;
    MatchingStrategy matchingStrategy;

    DriverService driverService;

    public RideService(MatchingStrategy matchingStrategy, DriverService driverService){
        // this.pricingStrategy = pricingStrategy;
        this.matchingStrategy = matchingStrategy;
        this.driverService = driverService;
    }


    Ride requestRide(Rider rider,Driver driver, Location src, Location dest){
        
        // matching ka logic 
        // Ride.driver = driver 
        // return Ride
        List<Driver> availableDriverListbyVehicleType = driverService.getAvailableDriverbyVehicleType(driver.vehicle.vehicleType);
        
        
        //Matching Strategy 
        NearestDriverStrategy nearestDriver = new NearestDriverStrategy();
        Driver matchedDriver = nearestDriver.findDriver(availableDriverListbyVehicleType,src);

        double amount = 0;
        //Pricing Strategy
        if(driver.vehicle.vehicleType == VehicleType.CAB ){
            CabPricingStrategy cabPricingStrategy = new CabPricingStrategy();
            amount = cabPricingStrategy.calculatePrice(src, dest);
        }

        else if(driver.vehicle.vehicleType == VehicleType.AUTO ){
            AutoPricingStrategy autoPricingStrategy = new AutoPricingStrategy();
            amount = autoPricingStrategy.calculatePrice(src, dest);
        }

        else if(driver.vehicle.vehicleType == VehicleType.BIKE ){
            BikePricingStrategy bikePricingStrategy = new BikePricingStrategy();
            amount = bikePricingStrategy.calculatePrice(src, dest);
        }
        
        Ride ride = new Ride(rider,matchedDriver,src,dest,amount,RideStatus.REQUESTED);
        
        System.out.println(
            "\n===== RIDE CONFIRMED =====\n" +
            "Rider           : " + rider.name + "\n" +
            "Driver          : " + matchedDriver.name + "\n" +
            "Vehicle         : " + matchedDriver.vehicle.vehicleType +
            " (" + matchedDriver.vehicle.numPlate + ")\n" +
            "Pickup          : (" + src.latitude + ", " + src.longitude + ")\n" +
            "Destination     : (" + dest.latitude + ", " + dest.longitude + ")\n" +
            "Estimated Fare  : ₹" + amount + "\n" +
            "Ride Status     : " + RideStatus.REQUESTED + "\n" +
            "==========================\n"
        );

        return ride;
    }
}