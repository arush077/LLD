interface VendingMachineState {
    void insertCoin(VendingMachine vm, int coins);
    void dispense(VendingMachine vm);
}

class VendingMachine {
    int balance;
    VendingMachineState currentState;
    // States owned by the machine
    NoCoin noCoinState;
    HasCoin hasCoinState;

    public VendingMachine() {
        balance = 0;
        noCoinState = new NoCoin();
        hasCoinState = new HasCoin();
        // Initial state
        currentState = noCoinState;
    }

    public void insertCoin(int coins) {
        currentState.insertCoin(this, coins);
    }

    public void dispense() {
        currentState.dispense(this);
    }
}

class NoCoin implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine vm, int coins) {
        vm.balance += coins;
        System.out.println("Coin inserted. Balance = " + vm.balance);
        // State transition
        vm.currentState = vm.hasCoinState;
    }

    @Override
    public void dispense(VendingMachine vm) {
        System.out.println("Insert coin first");
    }
}

class HasCoin implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine vm, int coins) {
        System.out.println("Already has coin");
    }

    @Override
    public void dispense(VendingMachine vm) {
        System.out.println("Dispensing item");
        vm.balance = 0;
        // State transition
        vm.currentState = vm.noCoinState;
    }
}

public class Main {
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        vm.dispense();
        vm.insertCoin(10);
        vm.insertCoin(5);
        vm.dispense();
        vm.dispense();
    }
}