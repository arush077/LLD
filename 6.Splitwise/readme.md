# Splitwise: Expense vs Split

## Mental Model

- **Expense** = total bill paid by someone.
- **Split** = one person’s share of that bill.

Example:

- A paid **₹100** for A, B, and C.
- Each person’s share = **₹33**.

---

## Expense stores its own splits

```text
Expense (Dinner ₹100 paid by A)
├── amount = 100
├── paidBy = A
└── splitList
    ├── Split(A, 33)
    ├── Split(B, 33)
    └── Split(C, 33)
```

```text
One Expense
    └── Many Splits 


(After splitting the expense, these splits are again stored in the "Expense only"!!!)
```

---

**Impl Tree Diagram**

```
SplitwiseService
├── Group
│   └── User
│
├── Expense
│   ├── paidBy (User)
│   └── splitList
│       ├── Split
│       ├── Split
│       └── Split
│
└── SplitStrategy
    └── ExactSplit

```

```
Main
│
├── User 
│   
├── Group
│   └── userList (User<>)
│
├── Expense
│   ├── amount      //Total amount
│   ├── paidBy      //Kisne pay kiya
│   └── splitList   //Store the splits in the Expense only lol after splitting
│
├── Split
│   ├── userId
│   └── amount
│
├── SplitwiseService (U G E (user, group, expense ka list rakho))
│   ├── groupList<>
│   ├── userList<>
│   ├── expenseList<>
│   └── splitStrategy.calculateSplit(Expense, User<>)
|   
│
└── SplitStrategy  { calculateSplit(Expense, User<>) }
    |
    └── ExactSplit (Simple implement calculateSplit)
```

