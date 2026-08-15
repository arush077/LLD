## Why are pointers used in `bookTicket()`?

```cpp
Booking bookTicket(User* user,
                   Show* show,
                   vector<Seat*> seatList);
```

Pointers are used because a booking should **refer to existing objects**, not create copies of them.

```text
Booking
 ├─ user  ----> Arush
 ├─ show  ----> Interstellar (7 PM)
 └─ seats ----> A1, A2
```

If we pass objects by value:

```cpp
Booking bookTicket(User user,
                   Show show,
                   vector<Seat> seats);
```

the function creates copies. Any changes (e.g., `seat.markBooked()`) affect only the copies, not the original objects in the system.

Using pointers ensures:

* `Booking` refers to the actual `User`, `Show`, and `Seat` objects.
* Seat booking updates the original seat state.
* Large objects are not unnecessarily copied.

**Rule of thumb:** Use pointers/references when multiple classes need to work with the same object instance and share its state.

```
**Dependency Injection**
```
In your BookMyShow example:

BookingService uses PaymentService and NotificationService.

Yahan:

main() ne PaymentService ka concrete object banaya.

main() ne BookingService ko de diya. BookingService sirf use kar raha hai.

Bas jo “de diya” wala step hai wahi Dependency Injection hai.