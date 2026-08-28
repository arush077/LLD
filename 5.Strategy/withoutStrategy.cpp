// Code :  Without Strategy Pattern
// All payment logic is inside one PaymentService class.
// Why this is bad:
// Adding a new method means editing PaymentService.
// One class keeps growing + Violates Open/Closed Principle.
// Many if-else blocks in the payment svc.

class PaymentService {
    void pay(String method,int amount) {
        if(method.equals("CARD")) System.out.println("Paid "+amount+" using Credit Card");
        else if(method.equals("UPI")) System.out.println("Paid "+amount+" using UPI");
        else if(method.equals("PAYPAL")) System.out.println("Paid "+amount+" using PayPal");
        else System.out.println("Invalid payment method");
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentService p=new PaymentService();
        p.pay("CARD",1000);
        p.pay("UPI",500);
        p.pay("PAYPAL",700);
    }
}