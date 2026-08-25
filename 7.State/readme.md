# State Design Pattern (Vending Machine)

## RATTNA 
VendingMachine HAS-A VendingMachineState interface


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
┌──────────────────────┐          ┌────────────────────────────┐
│    VendingMachine    │          │    VendingMachineState     │
│       Context        │          │       <<interface>>        │
├──────────────────────┤   HAS-A  ├────────────────────────────┤
│ balance              │ ───────► │ + insertCoin()             │
│ currentState         │          │ + dispense()               │
└──────────────────────┘          └─────────────┬──────────────┘
                                                │
                                           implements
                                         ┌────────┴────────┐
                                         ▼                 ▼
                                   ┌───────────┐     ┌───────────┐
                                   │  NoCoin   │     │  HasCoin  │
                                   └───────────┘     └───────────┘
```

The VendingMachine delegates work to the current state.


---

## When to use

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