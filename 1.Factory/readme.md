
1. User makes an object of the Factory
2. User does FactoryObject.createProduct()

Product class is an interface
Factory has a product 

```
┌─────────────────────────┐        ┌─────────────────────────┐
│    VehicleFactory       │        │        Vehicle          │
├─────────────────────────┤        │      <<interface>>      │
│ - vehicle : Vehicle     │ ──────>├─────────────────────────┤
│                         │ has-a  │       + drive()         │
│ + createVehicle(type)   │        └────────────▲────────────┘
└─────────────────────────┘                     │
                                      implements│
                                                │
                                                │
                              ┌─────────────────┴─────────────────┐
                              │                                   │
                     ┌─────────────────┐                 ┌─────────────────┐
                     │       Car       │                 │      Bike       │
                     ├─────────────────┤                 ├─────────────────┤
                     │    + drive()    │                 │   + drive()     │
                     └─────────────────┘                 └─────────────────┘
```