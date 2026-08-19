#include <iostream>
using namespace std;

// Without State Pattern: lots of if-else checks
class VendingMachine {
    string state = "IDLE";

public:
    void insertCoin() {
        if (state == "IDLE") {
            state = "HAS_COIN";
            cout << "Coin inserted\n";
        } else {
            cout << "Coin already inserted\n";
        }
    }

    void dispense() {
        if (state == "HAS_COIN") {
            state = "IDLE";
            cout << "Item dispensed\n";
        } else {
            cout << "Insert coin first\n";
        }
    }
};

int main() {
    VendingMachine vm;
    vm.insertCoin();
    vm.dispense();
}