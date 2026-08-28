// Why this is good : Each payment method has its own class.
// PaymentService never changes when a new method is added.
// Easy to add WalletPayment later.
// Behavior can be selected at runtime.

// Add new payment method, then just add 
// class WalletPayment implements PaymentStrategy {
//         public void pay(int amount) {
//            System.out.println("Paid " + amount + " using Wallet");
//         }
//     };


// PaymentService --> PaymentStrategy --> CardPayment

//---------------------------- Strategy interface ----------------------------//
interface PaymentStrategy {
    public void pay(int amount);
}

//---------------------------- Concrete impl of Strategy interface class ----------------------------//

class CardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Card");
    }
}

class UPIPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using UPI");
    }
}

//---------------------------- Context Class ----------------------------//
class PaymentService {
    private PaymentStrategy strategy;

    // VIMP :  Constructor in the context class for setting the strategy
    PaymentService(PaymentStrategy strategy){
        this.strategy = strategy;
    }

    // Once strategy is set in the constructor then call the pay method of that strategy
    void processPayment(int amount) {
        strategy.pay(amount);
    }
};

//---------------------------- Client Code ----------------------------//

public class Main{
    public static void main(String []args){
        UPIPayment upiPayment = new UPIPayment();
        CardPayment cardPayment = new CardPayment();

        // PaymentService ko bol raha hoon ki tu payment karne ke liye UPI strategy use kar.
        // PaymentService ko bol raha hoon ki tu payment karne ke liye card strategy use kar.
        PaymentService paymentServiceUPI = new PaymentService(upiPayment);
        PaymentService paymentServiceCARD = new PaymentService(cardPayment); 

        
        paymentServiceUPI.processPayment(100);
        paymentServiceCARD.processPayment(150);
    }
}