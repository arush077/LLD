#include <iostream>
using namespace std;

class VendingMachine; // forward declaration

// ---------------- State Interface ----------------
class VendingMachineState {
public:
    virtual void insertCoin(VendingMachine* vm, int coins) = 0;
    virtual void dispense(VendingMachine* vm) = 0;
    virtual ~VendingMachineState() = default;
};

// Forward declarations of concrete states
class NoCoin;
class HasCoin;

// ---------------- Context ----------------
class VendingMachine {
public:
    int balance;
    VendingMachineState* currentState;

    // States owned by the machine itself
    NoCoin* noCoinState;
    HasCoin* hasCoinState;

    VendingMachine();

    void insertCoin(int coins) {
        currentState->insertCoin(this, coins);
    }

    void dispense() {
        currentState->dispense(this);
    }
};

// ---------------- Concrete States ----------------
class NoCoin : public VendingMachineState {
public:
    void insertCoin(VendingMachine* vm, int coins) override;
    void dispense(VendingMachine* vm) override;
};

class HasCoin : public VendingMachineState {
public:
    void insertCoin(VendingMachine* vm, int coins) override;
    void dispense(VendingMachine* vm) override;
};

// ---------------- VendingMachine Constructor ----------------
VendingMachine::VendingMachine() {
    balance = 0;

    noCoinState = new NoCoin();
    hasCoinState = new HasCoin();

    currentState = noCoinState; // initial state
}

// ---------------- State Behaviors ----------------
void NoCoin::insertCoin(VendingMachine* vm, int coins) {
    vm->balance += coins;
    cout << "Coin inserted. Balance = " << vm->balance << endl;

    // State jump
    vm->currentState = vm->hasCoinState;
}

void NoCoin::dispense(VendingMachine* vm) {
    cout << "Insert coin first\n";
}

void HasCoin::insertCoin(VendingMachine* vm, int coins) {
    cout << "Already has coin\n";
}

void HasCoin::dispense(VendingMachine* vm) {
    cout << "Dispensing item\n";
    vm->balance = 0;

    // State jump back
    vm->currentState = vm->noCoinState;
}

// ---------------- Main ----------------
int main() {
    VendingMachine vm;

    vm.dispense();
    vm.insertCoin(10);
    vm.insertCoin(5);
    vm.dispense();
    vm.dispense();

    return 0;
}