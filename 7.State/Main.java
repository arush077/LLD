import java.util.*;


interface VendingMachineState {
    void insertCoin(VendingMachine vm, int coins);
    void selectItem(VendingMachine vm, int itemNo);
    void dispense(VendingMachine vm);
    void refill(VendingMachine vm, int itemNo, int quantity);
}

class VendingMachine {
    double balance;
    List <Product> productList = new ArrayList<>();
    Product selectedProduct;

    VendingMachineState currentState;
    // States owned by the machine
    NoCoinState noCoinState;
    HasCoinState hasCoinState;
    DispenseState dispenseState;
    SoldOutState soldOutState;

    public VendingMachine() {
        balance = 0;
        noCoinState = new NoCoinState();
        hasCoinState = new HasCoinState();
        dispenseState = new DispenseState();
        soldOutState = new SoldOutState();
        // Initial state
        currentState = noCoinState;
    }

    public void insertCoin(int coins) {
        currentState.insertCoin(this, coins);
    }

    public void selectItem(int itemNo){
        currentState.selectItem(this,itemNo);
    }

    public void dispense() {
        currentState.dispense(this);
    }

    public void refill(int itemNo, int quantity){
        currentState.refill(this, itemNo, quantity);
    }


    public void addProduct(Product p){
        productList.add(p);
    }

    public void addProduct(int itemNo,int quantity){
        for(int i=0;i<productList.size();i++){
            if(productList.get(i).itemNo == itemNo){
                productList.get(i).quantity = quantity;
                break;
            }
        }
    }
}


class Product{
    int itemNo;
    double price;
    int quantity;
}

class NoCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine vm, int coins) {
        vm.balance += coins;
        System.out.println("Coin inserted. Balance = " + vm.balance);
        // State transition
        vm.currentState = vm.hasCoinState;
    }

    @Override 
    public void selectItem(VendingMachine vm, int itemNo){
        System.out.println("Insert coin first");
    }

    @Override
    public void dispense(VendingMachine vm) {
        System.out.println("Insert coin first");
    }

    @Override 
    public void refill(VendingMachine vm, int itemNo, int quantity){
        System.out.println("Insert coin first");
    }
}

class HasCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine vm, int coins) {
        System.out.println("Coin already inserted");
    }

    @Override 
    public void selectItem(VendingMachine vm, int itemNo){
        for(int i=0;i<vm.productList.size();i++){
            if(vm.productList.get(i).itemNo == itemNo){
                Product product = vm.productList.get(i);
                if(product.quantity == 0){
                    System.out.println("this product is not available, pls select other item");
                    break;
                }
                else if(vm.balance < product.price){
                    System.out.println("Select another item, not enough balance");
                    break;
                }
                else{
                    System.out.println("Item selection done!");
                    vm.selectedProduct = vm.productList.get(i);
                    vm.currentState = vm.dispenseState;
                    break;
                }
             }
        }
    }

    @Override
    public void dispense(VendingMachine vm) {
        System.out.println("Insert item first, to dispense");
    }

    @Override 
    public void refill(VendingMachine vm, int itemNo, int quantity){
        System.out.println("");
    }
}

class DispenseState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine vm, int coins) {
        System.out.println("Coin already inserted, pls wait for dispense");
    }

    @Override 
    public void selectItem(VendingMachine vm, int itemNo){
        System.out.println("Item already selected, pls wait for dispense");
    }

    @Override
    public void dispense(VendingMachine vm) {
        vm.balance = vm.balance - vm.selectedProduct.price;
        vm.selectedProduct.quantity = vm.selectedProduct.quantity - 1;
        System.out.println("Dispensing product: " + vm.selectedProduct.itemNo);
        vm.selectedProduct = null;
    
        boolean allSoldOut = true;
        for(Product product : vm.productList){
            if(product.quantity > 0){
                allSoldOut = false;
                break;
            }
        }
        if(allSoldOut){
            vm.currentState = vm.soldOutState;
        }
        else{
            if(vm.balance > 0){
                vm.currentState = vm.hasCoinState;
            }
            else{
                vm.currentState = vm.noCoinState;
            }
        }
    }

    @Override 
    public void refill(VendingMachine vm, int itemNo, int quantity){
        System.out.println("I");
    }
}

class SoldOutState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine vm, int coins) {
        System.out.println("Machine is sold out");
    }

    @Override
    public void selectItem(VendingMachine vm, int itemNo) {
        System.out.println("Machine is sold out");
    }

    @Override
    public void dispense(VendingMachine vm) {
        System.out.println("Machine is sold out");
    }

    @Override
    public void refill(VendingMachine vm, int itemNo, int quantity){
        vm.addProduct(itemNo,quantity);
        System.out.println("Machine refilled");
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