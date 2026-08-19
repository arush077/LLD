# Decorator Pattern (Pizza Example)

## Why this code looks weird

```cpp
PlainPizza base;

OnionTopping onion(&base);
TomatoTopping TomatoOnionPizza(&onion);
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

## Pointer intuition

```cpp
Pizza* pizza;
```

The pointer can point to:

- `PlainPizza`
- `OnionTopping`
- `TomatoTopping`

The decorator only knows **“I have some Pizza.”**

---



## How does this solve constructor/class explosion?



### Without Decorator

```text
PlainPizza
OnionPizza
TomatoPizza
OnionTomatoPizza
CheesePizza
CheeseOnionPizza
...
```

A new class is needed for every combination.(All possible combinations ke classes chahiye hote without Decorator Design Pattern)

### With Decorator

```text
PlainPizza
OnionTopping
TomatoTopping
CheeseTopping
```

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

Only **one class per topping** is needed.

---



## Interview takeaway

- **Base object** = original pizza.
- **Decorator** = wraps another pizza.
- **Pointer** = points to the wrapped pizza.
- **Benefit** = add toppings dynamically and avoid subclass explosion.


## IMPLEMENTATION
1. Product abs class + concrete impl(for setting the base like plain pizza)
2. Decorator abs class + multiple conc impl
3. The step 2 wale, (decorator + conc impl) ke constructors both take the Product abs class as input parameter as a ptr
