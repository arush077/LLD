#include <iostream>
using namespace std;

// Why this is good : Each payment method has its own class.
// PaymentService never changes when a new method is added.
// Easy to add WalletPayment later.
// Easy to unit test each strategy.
// Behavior can be selected at runtime.

// Add new payment method, then just add 
// class WalletPayment : public PaymentStrategy {
//     public:
//         void pay(int amount) override {
//             cout << "Paid using Wallet" << endl;
//         }
//     };


// PaymentService --> PaymentStrategy* --> CardPayment



//---------------------------- Strategy abs class ----------------------------//
class PaymentStrategy {
public:
    virtual void pay(int amount) = 0;
    virtual ~PaymentStrategy() {}
};

//---------------------------- Concrete impl of Strategy abs class ----------------------------//

class CardPayment : public PaymentStrategy {
public:
    void pay(int amount) override {
        cout << "Paid " << amount << " using Credit Card" << endl;
    }
};

class UPIPayment : public PaymentStrategy {
public:
    void pay(int amount) override {
        cout << "Paid " << amount << " using UPI" << endl;
    }
};

//---------------------------- Context Class ----------------------------//
class PaymentService {
private:
    PaymentStrategy* strategy;

public:
    // VIMP :  Constructor in the context class for setting the strategy
    PaymentService(PaymentStrategy* s) : strategy(s) {}

    // Once strategy is set in the constructor then call the pay method of that strategy
    void processPayment(int amount) {
        strategy->pay(amount);
    }
};

//---------------------------- int main ----------------------------//
int main() {

    // 1. User chooses a strategy at runtime
    CardPayment card;
    // 2. Add this strategy in the PaymentService
    PaymentService p1(&card); //send this by reference, as you have a pointer in the constructor
   // 3. Ask PaymentService to process this strategy
    p1.processPayment(1000); 

    // Change strategy easily
    UPIPayment upi;
    PaymentService p2(&upi);
    p2.processPayment(500);


    // **VIMP**
    // 1. Make obj of concrete strategy
    // 2. Make obj of the context class, passing step1 object in this
    // 3. Call the method of the context class

    return 0;
}