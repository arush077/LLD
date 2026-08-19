## Think of it like this

### Remember 
Product + ProductBuilder 
```
ProductBuilder = 
1. copy same variables of product class
2. have a constructor(req fields) + make setters(for optional fields) 
3. build() method create a new product ka obj and return it
```
```
How to Use :
Product p1 = new ProductBuilder().set().set().set().build();
```

### Without Builder

The **user of the class** must remember the order of all parameters and too much reliance of constructor for obj creation.

```
User u = new User("Arush", 22, "Software Engineer", "Mumbai");
```

#### Problems

- Hard to read
- Easy to swap parameters accidentally
- Leads to multiple overloaded constructors

---

### With Builder

Now, we use setter methods like setJob and setLocation rather than blindly putting loads of parameters into constructor 
**We rely on setters than on constructors**

```cpp
User u = UserBuilder("Arush", 22)
            .setJob("Software Engineer")
            .setLocation("Mumbai")
            .build();
```

#### Benefits

- Much more readable
- Each field is clearly named (Thanks to the setters)
- Optional fields can be set only when needed or else they have default values
- Avoids constructor overload explosion

---



### What actually happens?
```
Builder = temporary object that collects data
                         ↓
                      .build() at the very end 
                         ↓
                 final Product object
```
