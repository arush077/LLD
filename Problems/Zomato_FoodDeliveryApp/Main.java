import java.util.*;

// FoodDeliveryService is singleton because it is the central service or central coordinator
class FoodDeliveryService {

    private static FoodDeliveryService instance;

    RestaurantService restaurantService;
    DeliveryService deliveryService;
    MatchingStrategy matchingStrategy;
    PricingStrategy pricingStrategy;

    private FoodDeliveryService(RestaurantService restaurantService,
                               DeliveryService deliveryService,
                               MatchingStrategy matchingStrategy,
                               PricingStrategy pricingStrategy) {

        this.restaurantService = restaurantService;
        this.deliveryService = deliveryService;
        this.matchingStrategy = matchingStrategy;
        this.pricingStrategy = pricingStrategy;
    }


    public static FoodDeliveryService getInstance(RestaurantService restaurantService,DeliveryService deliveryService,MatchingStrategy matchingStrategy,PricingStrategy pricingStrategy){
        if(instance == null){
            instance = new FoodDeliveryService(restaurantService, deliveryService, matchingStrategy, pricingStrategy);
        }
        return instance;
    }


    // MOST IMP FN input (user, restraunt, orderItem)
    Order placeOrder(User user, Restaurant restaurant, List<OrderItem> items) {

        // 1. Restaurant handles the order
        restaurantService.cookOrder(restaurant, items);

        // 2. Get available drivers
        List<Driver> driverList = deliveryService.getAvailableDrivers();

        // 3. Match driver
        Driver driver = matchingStrategy.matchDriver(user.location, driverList);

        if (driver == null) {
            return null;
        }

        // 4. Calculate delivery price
        double deliveryCharge = pricingStrategy.calculatePrice(
                restaurant.location, user.location
        );

        // 5. Mark driver unavailable
        deliveryService.updateStatusToUnavailable(driver);

        // 6. Create Order
        Order order = new Order(user, restaurant, items, driver, deliveryCharge);

        return order;
    }
}


// ---------------- RestaurantService ----------------

class RestaurantService {

    List<Restaurant> restaurantList = new ArrayList<>();

    void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    void cookOrder(Restaurant restaurant, List<OrderItem> items) {
        System.out.println("Restaurant is preparing the order...");
    }
}


// ---------------- DeliveryService ----------------

class DeliveryService {

    List<Driver> driverList = new ArrayList<>();

    void addDriver(Driver driver) {
        driverList.add(driver);
    }

    List<Driver> getAvailableDrivers() {

        List<Driver> availableDrivers = new ArrayList<>();

        for (Driver driver : driverList) {
            if (driver.driverStatus == DriverStatus.AVAILABLE) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

    void updateStatusToAvailable(Driver driver) {
        driver.driverStatus = DriverStatus.AVAILABLE;
    }

    void updateStatusToUnavailable(Driver driver) {
        driver.driverStatus = DriverStatus.NOT_AVAILABLE;
    }
}


// ---------------- Matching Strategy ----------------

interface MatchingStrategy {
    Driver matchDriver(Location userLocation, List<Driver> driverList);
}


class NearestDriver implements MatchingStrategy {

    @Override
    public Driver matchDriver(Location userLocation, List<Driver> driverList) {

        if (driverList.isEmpty()) {
            return null;
        }

        double mini = Double.MAX_VALUE;
        Driver nearestDriver = null;

        for (Driver driver : driverList) {

            double difference =
                    Math.abs(userLocation.latitude - driver.location.latitude)
                    + Math.abs(userLocation.longitude - driver.location.longitude);

            if (mini > difference) {
                mini = difference;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }
}


// ---------------- Restaurant ----------------

class Restaurant {

    String name;
    List<Dish> menu = new ArrayList<>();
    Location location;

    public Restaurant(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    void addDish(Dish dish) {
        menu.add(dish);
    }
}


// ---------------- Dish ----------------

class Dish {

    String name;
    double amount;

    public Dish(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}


// ---------------- OrderItem ----------------

class OrderItem {

    Dish dish;
    int quantity;

    public OrderItem(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    double getPrice() {
        return dish.amount * quantity;
    }
}

// ---------------- Order ----------------

class Order {
    static int counter = 1;

    int orderId;
    User user;
    Restaurant restaurant;
    List<OrderItem> items;
    Driver driver;
    double deliveryCharge;
    double totalAmount;
    OrderStatus status;

    public Order(User user, Restaurant restaurant, List<OrderItem> items,
                 Driver driver, double deliveryCharge) {

        this.orderId = counter++;
        this.user = user;
        this.restaurant = restaurant;
        this.items = items;
        this.driver = driver;
        this.deliveryCharge = deliveryCharge;
        this.status = OrderStatus.PLACED;

        for (OrderItem item : items) {
            totalAmount += item.getPrice();
        }
        totalAmount += deliveryCharge;
    }
}

// ---------------- OrderStatus ----------------

enum OrderStatus {

    PLACED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

// ---------------- Driver ----------------

class Driver {

    int id;
    String name;
    Location location;
    DriverStatus driverStatus;

    public Driver(int id, String name, Location location, DriverStatus driverStatus) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.driverStatus = driverStatus;
    }
}

// ---------------- DriverStatus ----------------

enum DriverStatus {

    AVAILABLE,
    NOT_AVAILABLE
}

// ---------------- User ----------------

class User {

    int id;
    String name;
    Location location;

    public User(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

// ---------------- Pricing Strategy ----------------

interface PricingStrategy {
    double calculatePrice(Location restaurantLocation, Location userLocation);
}

class NormalPrice implements PricingStrategy {
    @Override
    public double calculatePrice(Location restaurantLocation, Location userLocation) {
        double difference =  Math.abs(restaurantLocation.longitude - userLocation.longitude) + Math.abs(restaurantLocation.latitude - userLocation.latitude);
        return difference * 1;
    }
}

class LateNightPrice implements PricingStrategy {
    @Override
    public double calculatePrice(Location restaurantLocation, Location userLocation) {
        double difference = Math.abs(restaurantLocation.longitude - userLocation.longitude) + Math.abs(restaurantLocation.latitude - userLocation.latitude);
        return difference * 2;
    }
}

class MonsoonPrice implements PricingStrategy {
    @Override
    public double calculatePrice(Location restaurantLocation, Location userLocation) {
        double difference = Math.abs(restaurantLocation.longitude - userLocation.longitude) + Math.abs(restaurantLocation.latitude - userLocation.latitude);
        return difference * 3;
    }
}

// ---------------- Main ----------------

public class Main {

    public static void main(String[] args) {

        // Making individual user
        User user = new User( 1, "Arush",  new Location(19.12, 72.91));

        // Making individual restaurants
        Restaurant restaurant = new Restaurant( "Dominos", new Location(19.13, 72.99));
        restaurant.addDish(new Dish("Pizza", 300));
        restaurant.addDish(new Dish("Burger", 200));
        
         // Making individual drivers
        Driver driver1 = new Driver( 101, "Rahul", new Location(19.11, 72.98), DriverStatus.AVAILABLE);
        Driver driver2 = new Driver(  102, "Amit",  new Location(19.20, 72.90), DriverStatus.AVAILABLE);


        // Restraunt svc me adding individual restraunts
        RestaurantService restaurantService = new RestaurantService();
        restaurantService.addRestaurant(restaurant);

        // Delivery svc me adding individual restraunts
        DeliveryService deliveryService = new DeliveryService();
        deliveryService.addDriver(driver1);
        deliveryService.addDriver(driver2);

        MatchingStrategy matchingStrategy = new NearestDriver();
        PricingStrategy pricingStrategy = new NormalPrice();

        // Making the central coordinator at the end
        FoodDeliveryService foodDeliveryService = FoodDeliveryService.getInstance(restaurantService, deliveryService, matchingStrategy, pricingStrategy);

        List<OrderItem> items = new ArrayList<>();

        items.add(new OrderItem(restaurant.menu.get(0), 2));
        items.add(new OrderItem(restaurant.menu.get(1), 1));


        //This is the actual call from client/user to place the order
        Order order = foodDeliveryService.placeOrder(user, restaurant, items);

        System.out.println("Order ID : " + order.orderId);
        System.out.println("Driver   : " + order.driver.name);
        System.out.println("Total    : " + order.totalAmount);
        System.out.println("Status   : " + order.status);
    }
}