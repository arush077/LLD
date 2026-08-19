#pragma once
#include <iostream>
using namespace std;

//-----------------------------payment == strategy pattern----------------------------//
class PaymentStrategy{
    public : 
    virtual void pay(int amount) = 0;
};

class UPI : public PaymentStrategy{
    public : 
    void pay(int amount) override{
        cout<<"Paid amount:"<<amount<<  "using UPI"<<endl;
    }
};

class CreditCard : public PaymentStrategy{
    public : 
    void pay(int amount) override{
        cout<<"Paid amount:"<<amount<<  "using UPI"<<endl;
    }
};

class Cash : public PaymentStrategy{
    public : 
    void pay(int amount) override{
        cout<<"Paid amount:"<<amount<<  "using UPI"<<endl;
    }
};


class PaymentService{
    PaymentStrategy *ps;

    public : 

    // Constructor in the context class for setting the strategy
    PaymentService(PaymentStrategy *ps){
        this->ps = ps;
    }

    void processPayment(int amount){
        ps->pay(amount);
    }
};

