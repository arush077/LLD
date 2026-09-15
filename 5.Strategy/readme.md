## What changed?


| Without Strategy                  | With Strategy         |
| --------------------------------- | --------------------- |
| One big class                     | Small focused classes |
| `if-else` chain                   | Polymorphism          |
| Edit existing code for new method | Add new class only    |
| Hard to test                      | Easy to test          |
| Compile-time branching            | Runtime selection     |


---

## Interview one-liner

**Strategy Pattern defines a family of algorithms, puts each algorithm in a separate class, and makes them interchangeable at runtime.**

---



## PaymentStrategy

```
┌──────────┐       ┌──────────────────────────┐            ┌────────────────────────┐
│   User   │ ────► │     PaymentService       │   has-a    │   PaymentStrategy      │
└──────────┘       │     (Context Class)      │ ────────►  │     <<interface>>      │
                   ├──────────────────────────┤            ├────────────────────────┤
                   │ + processPayment()       │            │ + pay()                │
                   └──────────────────────────┘            └───────────┬────────────┘
                                                                       │
                                                                  implements
                                                                       │
                                                    ┌──────────────────┼──────────────────┐
                                                    ▼                  ▼                  ▼
                                             ┌────────────┐     ┌────────────┐     ┌──────────┐
                                             │    UPI     │     │ CreditCard │     │   Cash   │
                                             │            │     │            │     │          │
                                             │   pay()    │     │   pay()    │     │  pay()   │
                                             └────────────┘     └────────────┘     └──────────┘
```



## PricingStrategy

```
┌──────────┐      ┌────────────────┐      ┌──────────────────────┐       ┌──────────────────────┐
│   User   │─────►│Context/Service │─────►│PricingStrategyFactory│─────► │   PricingStrategy    │
└──────────┘      ├────────────────┤      ├──────────────────────┤ has-a ├    <<interface>>     ┤
                  │ - factory      │      │ - pricingStrategy    │       │──────────────────────│
                  ├────────────────┤      ├──────────────────────┤       ┤                      │
                  │ +calcPrice()   │      │ + getStrategy(type)  │       │ + calculatePrice()   │
                  └────────────────┘      └──────────────────────┘       └──┬───────────────────┘
                                                                            │
                                                                        implements
                                                                            │        
                                                         ┌──────────────────┼──────────────────┐
                                                         ▼                  ▼                  ▼
                                                  ┌────────────┐     ┌────────────┐     ┌───────────┐  
                                                  │    UPI     │     │ CreditCard │     │   Cash    │
                                                  │            │     │            │     │           │
                                                  │calcPrice() │     │calcPrice() │     │calcPrice()│
                                                  └────────────┘     └────────────┘     └───────────┘
```



### Zomato

```
PricingStrategy                    PaymentStrategy
├── NormalPricing                  ├── UPIPayment
├── PeakHourPricing                ├── CardPayment
└── SurgePricing                   └── CashPayment
```



### Uber

```
PricingStrategy                    PaymentStrategy
├── CabPricing                     ├── UPIPayment
├── AutoPricing                    ├── CardPayment
└── BikePricing                    └── CashPayment
```



### Hotel Booking

```
PricingStrategy                    PaymentStrategy
├── StandardPricing                ├── CardPayment
├── SeasonalPricing                ├── UPIPayment
└── WeekendPricing                 └── CashPayment
```



## Famous real-world examples

- Payment methods
- Sorting algorithms
- Compression algorithms
- Route finding / navigation
- Discount calculation

