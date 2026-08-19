#include <iostream>
#include <string>
using namespace std;

// All payment logic is inside one PaymentProcessor class.
// Why this is bad : Adding a new method (Wallet) means editing PaymentProcessor.
// One class keeps growing. Violates Open/Closed Principle.
// Harder to test each payment method separately. Many if-else blocks: This is why Strategy Pattern is needed.

class PaymentStrategy{
    virtual void pay(int amount) = 0;
}

//Concrete Impl of PaymentStrategy
class UPI{
    void pay(){
        cout<<"Paid using UPI";
    }
}

//Concrete Impl of PaymentStrategy
class CreditCard{
    void pay(){
        cout<<"Paid using CreditCard";
    }
}


// Context Class
class PaymentProcessor {
public:
    PaymentStrategy *ps;

    public:
    PaymentProcessor(PaymentStrategy* s) : strategy(s) {}

    processPayment(int amount){
        ps->pay(amount);
    }
   
};

int main() {
    PaymentProcessor p;

    UPI upi;
    PaymentProcessor p(&upi);
    p.processPayment(100);
    

    return 0;
}