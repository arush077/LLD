# C++ Design Patterns: The `*` and `&` Intuition

---
### Composition vs Association (C++)

#### Composition (owns the object)

```cpp
class Car {
    Engine engine;
};
```

* `Engine` is stored **inside** `Car`.
* Destroying `Car` automatically destroys `Engine`. (when Car destructor will begin, it will destroy all members of the Car class)

#### Association (does not own the object)

```cpp
class Car {
    Engine* engine;
};
```

* `Car` stores only a pointer (address) to an existing `Engine`.
* Destroying `Car` destroys only the pointer, **not** the `Engine` object.(lol bas address destroy karne se ghar thodi destroy ho jaega)
* `Engine` can exist independently.

**Key takeaway:**

* **Composition** → object is part of the owner, so its lifetime is tied to the owner.
* **Association** → owner just stores the address of the object, so the object's lifetime is managed elsewhere.
------

------

---

## Without ptr → store the object itself


```cpp
class Engine {};

class Car {
    Engine engine;   // always Engine
};
```

Use this when the object type is fixed and owned by the class.

Think: **“this specific object.”**

---

## With base pointer/reference
The easiest rule to remember is:

> **When a class should work with multiple possible implementations chosen at runtime, store a base-class pointer or reference.**

```cpp
class PaymentStrategy {
public:
    virtual void pay(int amount) = 0;
};

class PaymentProcessor {
    PaymentStrategy* strategy;
};
```

`strategy` may point to:

* `CardPayment`
* `UPIPayment`
* `WalletPayment`

Think: **“anything that behaves like PaymentStrategy.”**

---

## Visual

```text
PaymentProcessor
        |
        v
PaymentStrategy*
        |
   +----+----+
   |         |
CardPayment  UPIPayment
```

The processor does not care which concrete class is attached.

---

## Why do we pass `&card`?

```cpp
CardPayment card;
PaymentProcessor p(&card);
```

* `card` → real object
* `&card` → address of that object
* `PaymentStrategy*` stores that address

---

## The three symbols

| Syntax        | Meaning                           |
| ------------- | --------------------------------- |
| `&obj`        | address of object                 |
| `Base* p`     | pointer storing an address        |
| `p->method()` | go to that object and call method |

Read them in English:

```cpp
strategy->pay(100);
```

→ “Go to the strategy object and call `pay(100)`.”

---



## The interview shortcut

Ask one question:

> **Will this member always be one fixed concrete type?**

* **Yes** → store by value (`Engine engine;`)
* **No / may vary** → store `Base*` or `Base&`

This is why Strategy, Decorator, Observer, State, Command, and many other patterns use pointers or references.

---

## One-line takeaway

**Object = “this exact thing.”**
**Base pointer/reference = “any object that follows this contract.”**
