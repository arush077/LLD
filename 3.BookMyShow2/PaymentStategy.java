


abstract class PaymentStrategy{
    abstract void pay(int amount);
}


class Cash extends PaymentStrategy{
    public void pay(int amount){
    System.out.println("Paid" + amount +  "using Cash");
    }
}

class UPI extends PaymentStrategy{
    public void pay(int amount){
        System.out.println("Paid" + amount +  "using UPI");
    }
}

class CreditCard extends PaymentStrategy{
    public void pay(int amount){
        System.out.println("Paid" + amount +  "using CreditCard");
    }
}


//Context Class : Payment Service
class PaymentService{
    //Payment Service has a payment strategy
    PaymentStrategy paymentStrategy;


    //Constructor of the PaymentService takes in the PaymentStrategy
    PaymentService(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment(int amount){
        paymentStrategy.pay(amount);
    }

}