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

```
1. User can only interact with PaymentService(contextClass).
2. PaymentService processPayment ke andar PaymentStrategy ka pay call karta he 
3. So, for that PaymentService must maintain a pointer of PaymentStrategy (PaymentService ke Constructor me assign kardena PaymentStrategy ko)


## Famous real-world examples

* Payment methods
* Sorting algorithms
* Compression algorithms
* Route finding / navigation
* Discount calculation
```

