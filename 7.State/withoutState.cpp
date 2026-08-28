class VendingMachine {
    int balance=0;

    void insertCoin(int coins) {
        if(balance==0) {
            balance+=coins;
            System.out.println("Coin inserted. Balance = "+balance);
        } else {
            System.out.println("Already has coin");
        }
    }

    void dispense() {
        if(balance==0) {
            System.out.println("Insert coin first");
        } else {
            System.out.println("Dispensing item");
            balance=0;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        VendingMachine vm=new VendingMachine();
        vm.dispense();
        vm.insertCoin(10);
        vm.insertCoin(5);
        vm.dispense();
        vm.dispense();
    }
}