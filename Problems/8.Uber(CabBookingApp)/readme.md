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

                         ┌────────────────────┐
                         │     RideService    │  // RideService is singleton because it is the central service or central coordinator
                         │    <<Singleton>>   │
                         └─────────┬──────────┘
                                   │
                    ┌──────────────┼──────────────┐
                    │              │              │
                  HAS-A          HAS-A          HAS-A
                    │              │              │
                    ▼              ▼              ▼
             DriverService   MatchingStrategy  PricingStrategy
                    │              │              │
                 manages        implements      implements
                    │              │          ┌────┼────┐
                    ▼              ▼          ▼    ▼    ▼
                 Driver      NearestDriver   Cab  Auto  Bike
                    │
                  HAS-A
                    │
                    ▼
                 Vehicle


Rider
  │
  │ requestRide()
  ▼
RideService
  │
  ├── DriverService → get available drivers
  │
  ├── MatchingStrategy → select driver
  │
  ├── PricingStrategy → calculate fare
  │
  └── CREATES
        │
        ▼
      Ride
        │
        ▼
    REQUESTED
        │
        ▼
     Driver
      /   \
 ACCEPT   REJECT
   │        │
   ▼        ▼
DRIVER_   RideService
ASSIGNED     │
             ▼
      MatchingStrategy
             │
             ▼
         Next Driver
             │
          REQUESTED
             │
          ACCEPT
             │
             ▼
      DRIVER_ASSIGNED





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