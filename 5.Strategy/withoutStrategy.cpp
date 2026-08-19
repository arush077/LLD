#include <iostream>
#include <string>
using namespace std;

// All payment logic is inside one PaymentService class.
// Why this is bad
// Adding a new method (Wallet) means editing PaymentService.
// One class keeps growing.
// Violates Open/Closed Principle.
// Harder to test each payment method separately.
// Many if-else blocks.
// This is why Strategy Pattern is needed.

class PaymentService {
public:
    void pay(string method, int amount) {

        // All payment logic is inside one class
        if (method == "CARD") {
            cout << "Paid " << amount << " using Credit Card" << endl;
        }
        else if (method == "UPI") {
            cout << "Paid " << amount << " using UPI" << endl;
        }
        else if (method == "PAYPAL") {
            cout << "Paid " << amount << " using PayPal" << endl;
        }
        else {
            cout << "Invalid payment method" << endl;
        }
    }
};

int main() {
    PaymentService p;

    p.pay("CARD", 1000);
    p.pay("UPI", 500);

    return 0;
}