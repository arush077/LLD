# Decorator Pattern (Pizza Example)

```

                         Pizza
                      <<interface>>
                      /           \
                 implements     implements + has a Pizza
                    /               \
                   ▼                 ▼
            PlainPizza       PizzaDecorator
                          <<abstract class>>
                                 ▲
                                 │
                              extends
                            ┌────┴────┐
                            │         │
                            ▼         ▼
                       Onion       Tomato

```
PlainPizza
    └── implements Pizza

PizzaDecorator
    ├── implements Pizza
    └── has-a Pizza

OnionTopping
    └── extends PizzaDecorator

TomatoTopping
    └── extends PizzaDecorator   

```

### Step-by-step

```text
1. base (100)
   
2. onion (+30)
   
3. tomato (+20)
```

- `&base` → address of `base`
- `OnionTopping` stores a pointer to the pizza it wraps.
- `TomatoTopping` stores a pointer to the onion pizza.

Calling TomatoOnionPizza`.cost()` becomes:

```text
base.cost() + 30 + 20 = 150
```

---



## How does this solve constructor/class explosion?

A new class is needed for every combination.(All possible combinations ke classes chahiye hote without Decorator Design Pattern)

| With Decorator(only toppings) | Without Decorator(every combn) |
|---|---|
| `PlainPizza` | `PlainPizza` |
| `OnionTopping` | `OnionPizza` |
| `TomatoTopping` | `TomatoPizza` |
| `CheeseTopping` | `OnionTomatoPizza` |
| | `CheesePizza` |
| | `CheeseOnionPizza` |
| | `...` |

To get Onion + Tomato + Cheese, simply wrap them:

```cpp
CheeseTopping(
    new TomatoTopping(
        new OnionTopping(
            new PlainPizza()
        )
    )
);
```

---


## IMPLEMENTATION
1. Product interface + concrete impl(for setting the base like plain pizza)
2. Decorator abs class + multiple conc impl
3. Decorator implements from Pizza interface + also 'has-a' Pizza


## 🌍 Real-World Examples
- Java I/O Streams (`BufferedInputStream`, `BufferedOutputStream`)
- Java `Reader` / `Writer` classes
- GUI components (borders, scrollbars, etc.)
- Middleware / HTTP request handlers
- Logging wrappers
- Authentication / Authorization wrappers
- Caching wrappers
- Compression / Encryption streams
