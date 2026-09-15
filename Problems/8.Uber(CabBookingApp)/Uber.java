import java.util.*;

class Main {

    public static void main(String[] args) {

        Rider rider = new Rider(1, "Arush");

        Driver driver1 = new Driver(101, "Rahul", new Location(19.13, 72.99),
                new Vehicle("MH43CS3112", VehicleType.CAB), DriverStatus.AVAILABLE);

        Driver driver2 = new Driver(102, "Amit", new Location(19.14, 72.98),
                new Vehicle("MH12AB1234", VehicleType.CAB), DriverStatus.AVAILABLE);

        DriverService driverService = new DriverService();
        driverService.addDriver(driver1);
        driverService.addDriver(driver2);

        MatchingStrategy matchingStrategy = new NearestDriverStrategy();
        PricingStrategy pricingStrategy = new CabPricingStrategy();

        // RideService is Singleton
        RideService rideService = RideService.getInstance(driverService, matchingStrategy, pricingStrategy);

        Location src = new Location(19.135, 72.992);
        Location dest = new Location(19.124, 72.917);

        // Driver 1 gets the request

        VehicleType choosenvehicleType = VehicleType.CAB;
        Ride ride = rideService.requestRide(rider, src, choosenvehicleType, dest);

        System.out.println("Driver selected: " + ride.driver.name);
        System.out.println("Status: " + ride.status);

        // Driver rejects
        rideService.rejectRide(ride);

        System.out.println("After rejection: " + ride.status);

        // RideService finds another driver
        rideService.assignNextDriver(ride);

        System.out.println("New driver: " + ride.driver.name);
        System.out.println("Status: " + ride.status);

        // New driver accepts
        rideService.acceptRide(ride);

        System.out.println("After acceptance: " + ride.status);
    }
}


// ---------------- Rider ----------------

class Rider {

    int id;
    String name;

    public Rider(int id, String name) {
        this.id = id;
        this.name = name;
    }
}


// ---------------- Location ----------------

class Location {

    double latitude;
    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}


// ---------------- Vehicle ----------------

class Vehicle {

    String numPlate;
    VehicleType vehicleType;

    public Vehicle(String numPlate, VehicleType vehicleType) {
        this.numPlate = numPlate;
        this.vehicleType = vehicleType;
    }
}

enum VehicleType {
    CAB,
    AUTO,
    BIKE
}


// ---------------- Driver ----------------

class Driver {

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

enum DriverStatus {
    AVAILABLE,
    NOT_AVAILABLE
}


// ---------------- DriverService ----------------
class DriverService {

    List<Driver> driverList = new ArrayList<>();

    public void addDriver(Driver driver) {
        driverList.add(driver);
    }

    public List<Driver> getAvailableDrivers(VehicleType vehicleType) {

        List<Driver> availableDrivers = new ArrayList<>();

        for (Driver driver : driverList) {

            if (driver.driverStatus == DriverStatus.AVAILABLE &&
                driver.vehicle.vehicleType == vehicleType) {

                availableDrivers.add(driver);
            }
        }

        return availableDrivers;
    }

    public void updateStatus(Driver driver, DriverStatus status) {
        driver.driverStatus = status;
    }
}


// ---------------- Matching Strategy ----------------

interface MatchingStrategy {

    Driver findDriver(List<Driver> drivers, Location source);
}


class NearestDriverStrategy implements MatchingStrategy {

    @Override
    public Driver findDriver(List<Driver> driverList, Location source) {

        double mini = Integer.MAX_VALUE;
        Driver nearestDriver = driverList.get(0);
        for(int i=0;i<driverList.size();i++){

            double difference = Math.abs(source.latitude - driverList.get(i).driverLocation.latitude) 
            + Math.abs(source.longitude - driverList.get(i).driverLocation.longitude);

            if(mini > difference){
                mini = difference;
                nearestDriver = driverList.get(i);
            }

        }

        return nearestDriver;

    }
}


// ---------------- Pricing Strategy ----------------

interface PricingStrategy {

    double calculatePrice(Location source, Location destination);
}


class CabPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(Location source, Location destination) {
        return 100;
    }
}


class AutoPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(Location source, Location destination) {
        return 70;
    }
}


class BikePricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(Location source, Location destination) {
        return 50;
    }
}


// ---------------- Ride ----------------

class Ride {

    Rider rider;
    Driver driver;
    Location source;
    Location destination;
    double amount;
    RideStatus status;

    public Ride(Rider rider, Driver driver, Location source, Location destination, double amount, RideStatus status) {
        this.rider = rider;
        this.driver = driver;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.status = status;
    }
}

enum RideStatus {

    REQUESTED,
    DRIVER_ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}


// ---------------- RideService ----------------

// RideService is singleton because it is the central service or central coordinator
class RideService { 

    private static RideService instance;

    private DriverService driverService;
    private MatchingStrategy matchingStrategy;
    private PricingStrategy pricingStrategy;

    private RideService(DriverService driverService, MatchingStrategy matchingStrategy, PricingStrategy pricingStrategy) {
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public static RideService getInstance(DriverService driverService, MatchingStrategy matchingStrategy, PricingStrategy pricingStrategy) {

        if (instance == null) {
            instance = new RideService(driverService, matchingStrategy, pricingStrategy);
        }

        return instance;
    }


    // Rider requests a ride
    public Ride requestRide(Rider rider, Location source, VehicleType vehicleType, Location destination) {

        List<Driver> drivers = driverService.getAvailableDrivers(vehicleType);

        Driver driver = matchingStrategy.findDriver(drivers, source);

        if (driver == null) {
            return null;
        }

        double amount = pricingStrategy.calculatePrice(source, destination);

        driverService.updateStatus(driver, DriverStatus.NOT_AVAILABLE);

        return new Ride(rider, driver, source, destination, amount, RideStatus.REQUESTED);
    }


    // Driver accepts
    public void acceptRide(Ride ride) {

        if (ride.status == RideStatus.REQUESTED) {
            ride.status = RideStatus.DRIVER_ASSIGNED;
        }
    }


    // Driver rejects
    public void rejectRide(Ride ride) {

        if (ride.status == RideStatus.REQUESTED) {

            driverService.updateStatus(ride.driver, DriverStatus.AVAILABLE);

            ride.status = RideStatus.CANCELLED;
        }
    }


    // Find another driver after rejection
    public void assignNextDriver(Ride ride) {

        List<Driver> drivers = driverService.getAvailableDrivers(ride.driver.vehicle.vehicleType);

        Driver nextDriver = matchingStrategy.findDriver(drivers, ride.source);

        if (nextDriver == null) {
            ride.status = RideStatus.CANCELLED;
            return;
        }

        ride.driver = nextDriver;

        driverService.updateStatus(nextDriver, DriverStatus.NOT_AVAILABLE);

        ride.status = RideStatus.REQUESTED;
    }
}