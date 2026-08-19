# Observer Design Pattern (Notification Service)

* **Observable (Subject):** `NotificationService`
* **Observers:** `Email`, `SMS`, `WA`

---

## Implementation Flow

1. Create an abstract **Observer** with `update()`. + Create concrete observers (`Email`, `SMS`, `WA`) implementing `update()`.
2. Create an abstract **Observable** with `notify() + Create concrete observers (`NotificationService`) implementing `notify()`
3. `NotificationService` stores a list of `Observer*`.
4. `notify()` loops through all observers and calls `update()`.

---

## Why `vector<Observer*>`?

`Email`, `SMS`, and `WA` are different classes, but all inherit from `Observer`.

Storing `Observer*` enables **runtime polymorphism**:

```cpp
vector<Observer*> observerList;
```

Each pointer can point to any concrete observer.

---

## Notification Flow

```text
Client
   |
   v
NotificationService
   |
   +--> Email.update()
   +--> SMS.update()
   +--> WA.update()
```

---

## Key Interview Points

* **Open for extension:** add `PushNotification`, `SlackNotification`, etc. without changing `NotificationService`.
* **Runtime subscription:** observers can be added dynamically.
* **Polymorphism:** achieved through `Observer*`.

---

## Client Usage

```cpp
//First make all the observer objects
Email email;
SMS sms;
WA wa;

//First one object for notificationService, then add observers and notify()
NotificationService ns;
ns.add(&email);
ns.add(&sms);
ns.add(&wa);

ns.notify();
```

The client only registers observers; the service handles all notifications automatically.
