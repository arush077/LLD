# State Design Pattern (Vending Machine)

## Problem

Without State Pattern:
If you have a common state variable which keeps changing 
```cpp
if(state == "IDLE") ...
else if(state == "HAS_MONEY") ...
```

As states grow, the class becomes full of if-else checks.

---

## Solution

Move behavior into separate state classes.

```text
VendingMachine
      |
      v
   State*
   /     \
Idle   HasMoney
```

The VendingMachine delegates work to the current state.

---

## Pointer Intuition

```cpp
State* currentState;
```

The machine does not know which concrete state it has.

It may point to:

- `IdleState`
- `HasMoneyState`

Example:

```cpp
vm->setState(&hasMoneyState);
```

Meaning:

> Change behavior by pointing to a different state object.

Same pattern as:

- `PaymentStrategy* strategy`
- `Observer* observer`
- `Pizza* pizza`

---

## When to use

Use State Pattern when behavior changes based on the current state.

Examples:

- Vending Machine
- Order Lifecycle (Created → Paid → Shipped)
- Traffic Light
- Media Player (Playing / Paused / Stopped)
- ATM Machine
- Elevator

---

## Interview Takeaway

Instead of:

```cpp
if(state == ...)
```

use:

```cpp
currentState->action();
```

Each state class contains its own behavior, making the code easier to extend.