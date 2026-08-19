#include <iostream>
using namespace std;


//-------------------------------------------------Product and its concrete impl (for setting the base)--------------------------------------//
// Product
class Pizza {
public:
    virtual int cost() = 0;
    virtual ~Pizza() = default;
};

// Concrete Product (for setting the base on top of which we need to decorate)
class PlainPizza : public Pizza {
public:
    int cost() override { return 100; }
};


//-------------------------------------------------Decorator and its concrete impl ------------------------------------------------------//

//Decorator
class PizzaDecorator : public Pizza {
protected:
    Pizza* pizza;

public:
    PizzaDecorator(Pizza* p) : pizza(p) {}
};

// Onion topping
class OnionTopping : public PizzaDecorator {
public:
    OnionTopping(Pizza* p) : PizzaDecorator(p) {}

    int cost() override {
        return pizza->cost() + 30;
    }
};

// Tomato topping
class TomatoTopping : public PizzaDecorator {
public:
    TomatoTopping(Pizza* p) : PizzaDecorator(p) {}

    int cost() override {
        return pizza->cost() + 20;
    }
};

int main() {

    // 1. Stack-based Manual decoration:
    // First make the base in DDP
    PlainPizza base;

    // After making the base only then decorate 
    OnionTopping onion(&base);
    TomatoTopping TomatoOnionPizza(&onion);
    cout << "Cost1: " << TomatoOnionPizza.cost() << endl;


    // 2. Heap-based Nested decoration:
    Pizza* pizza = new TomatoTopping( new OnionTopping( new PlainPizza()) );

    cout << "Cost2: " << pizza->cost() << endl;

}