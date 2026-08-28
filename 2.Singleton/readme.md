### Remember

1. private static Singleton instance;
2. private Singleton(){}
3. public static Singleton getInstance()

```
┌─────────────────────────────────────┐
│             Singleton               │
├─────────────────────────────────────┤
│   instance : Singleton              │  ← static
│                                     │
│ - Singleton()                       │  ← private
├─────────────────────────────────────┤
│   getInstance() : Singleton         │  ← static
└─────────────────────────────────────┘
```

## Why static? in both the instance and getInstance() ?

Reason1 : because now we want only one instance of the class, not instance of every object
Reason2 : `getInstance()` **is static so we can access the Singleton without first creating an object.**

