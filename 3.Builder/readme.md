## Think of it like this

### Without Builder

The **user of the class** must remember the order of all parameters.

```cpp
User u("Arush", 22, "Software Engineer", "Mumbai");
```

#### Problems

- Hard to read
- Easy to swap parameters accidentally
- Leads to multiple overloaded constructors

---

### With Builder

The caller writes:

```cpp
User u = UserBuilder("Arush", 22)
            .setJob("Software Engineer")
            .setLocation("Mumbai")
            .build();
```

#### Benefits

- Much more readable
- Each field is clearly named
- Optional fields can be set only when needed
- The API becomes self-documenting
- Avoids constructor overload explosion

---



### What actually happens?

The `UserBuilder` collects values step by step and **creates the final** `User` **object only once** when `build()` is called.
#
---
---
---
---
# WHY IS THE SETTER CODE SO WEIRD MAN : Why does a Builder setter return `UserBuilder&`?

You’re staring at this code:

```cpp
UserBuilder& setJob(string j) {
    job = j;
    return *this;
}
```

and thinking:

> **“Why the hell is a setter in Builder class so WEIRD? Setters should return nothing!”**

That instinct is correct for **normal setters**. Builder setters are special because they are designed for **method chaining**.

---



## Concept 1: What happens if it returns `void`?

```cpp
void setJob(string j) {
    job = j;
}
```

Then this works:

```cpp
UserBuilder b("Arush",22);
b.setJob("SDE");
```

But this fails:

```cpp
b.setJob("SDE").setLocation("Mumbai");
```



### Why?

Because `b.setJob(...)` returns **nothing**. You cannot call `.setLocation()` on “nothing”.

---



## Concept 2: Return the same object

We want this to work:

```cpp
b.setJob("SDE")
 .setLocation("Mumbai");
```

For that, `setJob()` must return an object on which `setLocation()` can be called.

So we return the **current builder object**.

---



## Concept 3: What is `this`?

Inside any member function, C++ secretly gives you a pointer called `this`.

```cpp
UserBuilder b("Arush",22);
```

Inside `setJob()`, it is roughly:

```cpp
this = &b;
```

So:

```cpp
return this;
```

would return a `UserBuilder*`.

Then chaining would look like:

```cpp
b.setJob("SDE")->setLocation("Mumbai");
```

Works, but it is ugly and inconsistent with normal object syntax.

---



## Concept 4: What is `*this`?

`this` is a pointer.

`*this` means **“the object pointed to by** `this`**.”**

If:

```cpp
this == &b
```

then:

```cpp
*this == b
```

So:

```cpp
return *this;
```

returns the actual builder object.

---



## Concept 5: Why `UserBuilder&` specifically?

Suppose we returned by value:

```cpp
UserBuilder setJob(string j) {
    job = j;
    return *this; // copy created
}
```

A **copy** of the builder would be returned every time.

Chaining would still work, but unnecessary copies are made.

### Visual



#### Return by value

```text
b (original)
   ↓
copy1
   ↓
copy2
```

Wasteful.

---

Returning by reference:

```cpp
UserBuilder& setJob(string j)
```

means:

> **“Return an alias to the same object.”**



### Visual

```text
b
↓
same object reused
```

No copies are created.

---



## Watch the chain step by step

```cpp
UserBuilder("Arush",22)
    .setJob("SDE")
    .setLocation("Mumbai")
    .build();
```

Compiler roughly sees:

```cpp
temp.setJob("SDE");        // returns reference to temp
temp.setLocation("Mumbai"); // same temp
temp.build();                // same temp
```

All calls operate on **one builder object**.

---



# The 10-second takeaway

```cpp
UserBuilder& setJob(...) {
    job = ...;
    return *this;
}
```

means:

> **“After setting the field, give me back the same builder object so I can continue calling methods on it.”**

This is called a **fluent interface** or **method chaining**. The `&` avoids creating copies, and `return *this` returns the current object itself.