//Observer Design Pattern
// Observable and an Observer 

#include<iostream>
#include<vector>
using namespace std;

// ----------------------------Abs Observer + Conc Observer-----------------------//
class Observer{
    public : 
    virtual void update() = 0;
};

// Notifications can go via Email,SMS,WA
class Email : public Observer{
    void update(){
        cout<<"Notifying Email"<<endl;
    }
};

class SMS : public Observer{
    void update(){
    cout<<"Notifying SMS"<<endl;
    }
};

class WA : public Observer{
    void update(){
    cout<<"Notifying WA"<<endl;
    }
};

// ----------------------------Abs Observable + Conc Observable-----------------------//
class Observable{
    public : 
    virtual void notify() = 0;
};

//NotificationService is the concrete Observable usually
class NotificationService{
    vector<Observer*> observerList;

    public : 
    void add(Observer* obs){
        observerList.push_back(obs);
    }

    void notify(){
        for(int i=0;i<observerList.size();i++){
            observerList[i]->update();
        }
    }
};


// ----------------------------int main()-----------------------//
int main(){
    Email email;
    SMS sms;
    WA wa;

    NotificationService ns;
    ns.add(&email);
    ns.add(&sms);
    ns.add(&wa);
    ns.notify();

}