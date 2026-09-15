## Zomato : 
```
FoodDeliveryService  <<Singleton>> // FoodDeliveryService is singleton because it is the central service or central coordinator
|
├── HAS-A RestaurantService → List of Restraunt → List of Dish
|
├── HAS-A DeliveryService → get available drivers (D)
|
├── HAS-A MatchingStrategy → select driver (M)
|
├── HAS-A PricingStrategy → calculate price (P)
|
└── CREATES Order ==> createOrder(user, restraunt, List<orderItem>) function returns an order { Order has a list of orderItems}
```

```
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

    constructor
}
```


### Similarities(VIMP)
``` 
Zomato and Blinkit ka code 99% similar hoga, bas Restraunt ke badle DarkStore hoga lol

Zomato very similar to Uber ( difference : Uber = DMP , Zomato = restraunt svc + DMP )
```


<br>

## Uber : 
```
RideService <<Singleton>> // RideService is singleton because it is the central service or central coordinator
  │
  ├── DriverService → get available drivers (D)
  │
  ├── MatchingStrategy → select driver (M)
  │
  ├── PricingStrategy → calculate price (P)
  │
  └── CREATES Ride  ==> requestRide(rider, src, dest) function returns an ride
```

```
class Ride {
    Rider rider;
    Driver driver;
    Location source;
    Location destination;
    double amount;
    RideStatus status;

    constructor
}
```

