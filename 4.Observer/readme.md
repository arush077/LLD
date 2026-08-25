## Remember

```
interface Observable + concrete observable === notify() method
interface Observer + concrete observer === update() method


ALSO remember always a ConcreteObservable "has-a" interface Observer !!!


┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
              │
┌───────────────────────────--┐              ┌────────────────────────────┐
│ (SomethingWhoseStateChanges)│              │          Observer          │
│    <<ConcreteObservable>>   │              │        <<interface>>       │
├─────────────────────────────┤              ├────────────────────────────┤
│ List<Observer> observerList │──── HAS-A ──►│       + update()           │
│ + addObserver()             │              └─────────────▲──────────────┘
│ + removeObserver()          │                            │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                            │
                                                           │
                                      ┌────────────────────┼────────────────────┐
                                      │                    │                    │
                                      ▼                    ▼                    ▼
                           ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────────┐
                           │       User       │  │   EmailService   │  │   AnalyticsService   │
                           │<<ConcObserver>>  │  │ <<ConcObserver>> │  │  <<ConcObserver>>    │
                           └────────┬─────────┘  └──────────────────┘  └──────────────────────┘
                                    │
                         ┌──────────┼──────────┐
                         │          │          │
                         ▼          ▼          ▼
                        u1         u2         u3

```
```
VVBIG CONFUSION : where does notfn service belong to?
it depends 

notfn service = producing notifications = on the concrete observable 
notfn service = listening to notifications = on the concrete observer 


Examples of Observables (something whose state changes):

Stock       → StockPrice (StockPrice changes)
E-commerce  → OrderService (Orders keep changing)
Weather     → WeatherStation (Weather keeps changing)
Smart Home  → MotionSensor (motion state keeps changing)
Ride Share  → RideService (rides keep happening and state/status of it keeps chaning)
Banking     → TransactionService (txns happens its state/status changes)
YouTube     → YouTubeChannel (channel uploads video its state/status changes)

```



# What changed?

| Without Observer                                  | With Observer            |
| ------------------------------------------------- | ------------------------ |
| Hardcoded users in observable                     | Dynamic subscribers      |
| Tight coupling                                    | Loose coupling           |
| Edit channel for new user                         | Just subscribe user      |
| Duplicate notification code                       | Centralized notification |
| Hard to scale                                     | Easy to scale            |



##WITHOUT OBSERVER

```
SomethingWhoseStateChanges (Observable)
       │
       ├──── knows ────> ConcreteObserver 1
       ├──── knows ────> ConcreteObserver 2
       └──── knows ────> ConcreteObserver 3
```

#WITH OBSERVER

```
SomethingWhoseStateChanges (Observable)
       │
       │ knows only
       ▼
    Observer
       ▲
       │
     User
 ┌─────┼─────┐
 ▼     ▼     ▼
Alice  Bob  Rahul
```

---



## Interview one-liner

**Observer Pattern defines a one-to-many dependency so that when one object changes state, all its dependent objects are notified automatically.**

---



## Famous real-world examples

- YouTube channel subscriptions
- Instagram / Twitter followers
- Stock price alerts
- Weather app notifications
- Email newsletter subscribers
- GUI button click listeners

1. 📈 Stock Market

```
┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
┌─────────────────────────────┐               ┌────────────────────────────┐
│        StockPrice           │               │          Observer          │
│    <<ConcreteObservable>>   │               │        <<interface>>       │
├─────────────────────────────┤               ├────────────────────────────┤
│ List<Observer> observers    │──── HAS-A ──► │ + update()                 │
│ + addObserver()             │               └─────────────▲──────────────┘
│ + removeObserver()          │                             │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                             │
                                                            │
                                             ┌──────────────--─────────────┐
                                             │       TradingDashboard      │
                                             │    <<ConcreteObserver>>     │
                                             └─────────────────────────────┘

Other Concrete Observables: CryptoPrice, CurrencyRate, MarketIndex
Other observers: PriceAlertService, PortfolioService, MobileApp.
```

1. 🛒 E-commerce

```
Here multiple things can be Observables, so this is a nice example:

┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
┌─────────────────────────────┐              ┌────────────────────────────┐
│        OrderService         │              │          Observer          │
│    <<ConcreteObservable>>   │              │        <<interface>>       │
├─────────────────────────────┤              ├────────────────────────────┤
│ List<Observer> observers    │──── HAS-A ──►│ + update()                 │
│ + addObserver()             │              └─────────────▲──────────────┘
│ + removeObserver()          │                            │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                            │
                                                           │
                                            ┌──────────────┴──────────────┐
                                            │       EmailService          │
                                            │    <<ConcreteObserver>>     │
                                            └─────────────────────────────┘

Other Concrete Observables: PaymentService, InventoryService, DeliveryService
Other observers: SMSService, InventoryService, AnalyticsService.
```

1. 🌡️ Weather System

```
┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
┌─────────────────────────────┐              ┌────────────────────────────┐
│      WeatherStation         │              │          Observer          │
│    <<ConcreteObservable>>   │              │        <<interface>>       │
├─────────────────────────────┤              ├────────────────────────────┤
│ List<Observer> observers    │──── HAS-A ──►│ + update()                 │
│ + addObserver()             │              └─────────────▲──────────────┘
│ + removeObserver()          │                            │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                            │
                                                           │
                                            ┌──────────────┴──────────────┐
                                            │       WeatherDisplay        │
                                            │    <<ConcreteObserver>>     │
                                            └─────────────────────────────┘

Other Concrete Observables: TemperatureSensor, HumiditySensor, PressureSensor
Other observers: MobileWeatherApp, WeatherAlertService, Dashboard.
```

1. 🏠 Smart Home

```
┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
┌─────────────────────────────┐              ┌────────────────────────────┐
│       MotionSensor          │              │          Observer          │
│    <<ConcreteObservable>>   │              │        <<interface>>       │
├─────────────────────────────┤              ├────────────────────────────┤
│ List<Observer> observers    │──── HAS-A ──►│ + update()                 │
│ + addObserver()             │              └─────────────▲──────────────┘
│ + removeObserver()          │                            │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                            │
                                                           │
                                            ┌──────────────┴──────────────┐
                                            │      SecuritySystem         │
                                            │    <<ConcreteObserver>>     │
                                            └─────────────────────────────┘

Other Concrete Observables: DoorSensor, TemperatureSensor, SmokeSensor
Other observers: MobileApp, AlarmSystem, SmartLights.
```

1. 🚗 Ride Sharing

```
┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
┌─────────────────────────────┐              ┌────────────────────────────┐
│       RideService           │              │          Observer          │
│    <<ConcreteObservable>>   │              │        <<interface>>       │
├─────────────────────────────┤              ├────────────────────────────┤
│ List<Observer> observers    │──── HAS-A ──►│ + update()                 │
│ + addObserver()             │              └─────────────▲──────────────┘
│ + removeObserver()          │                            │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                            │
                                                           │
                                            ┌──────────────┴──────────────┐
                                            │        RiderApp             │
                                            │    <<ConcreteObserver>>     │
                                            └─────────────────────────────┘

Other Concrete Observables: DriverLocation, PaymentStatus, RideStatus
Other observers: DriverApp, NotificationService, PricingService.
```

1. 🏦 Banking

```
┌────────────────────────────┐
│         Observable         │
│        <<interface>>       │
├────────────────────────────┤
│ + notifyObservers()        │
└─────────────▲──────────────┘
              │
         implements
              │
┌─────────────────────────────┐              ┌────────────────────────────┐
│    TransactionService       │              │          Observer          │
│    <<ConcreteObservable>>   │              │        <<interface>>       │
├─────────────────────────────┤              ├────────────────────────────┤
│ List<Observer> observers    │──── HAS-A ──►│ + update()                 │
│ + addObserver()             │              └─────────────▲──────────────┘
│ + removeObserver()          │                            │
│ + notifyObservers()         │                        implements
└─────────────────────────────┘                            │
                                                           │
                                            ┌──────────────┴──────────────┐
                                            │    NotificationService      │
                                            │    <<ConcreteObserver>>     │
                                            └─────────────────────────────┘

Other Concrete Observables: AccountBalance, LoanStatus, PaymentStatus
Other observers: FraudDetectionService, AnalyticsService, BankingApp.                                            
```

