#include <iostream>
using namespace std;

// Too many combinations needed
class Pizza {
public:
    int cost() { return 100; }
};

class OnionPizza {
public:
    int cost() { return 130; } // 100 + 30
};

class TomatoPizza {
public:
    int cost() { return 120; } // 100 + 20
};

class OnionTomatoPizza {
public:
    int cost() { return 150; } // 100 + 30 + 20
};


int main() {
    OnionTomatoPizza pizza;
    cout << "Cost: " << pizza.cost() << endl;
}