#include<iostream>
#include <vector>
#include <string>
using namespace std;

class Observable{
    public:
    virtual void notify()= 0;
    virtual ~Observable() = default;
};

class NotificationService : public Observable{
    vector<Observer*> observerList;            

    public : 

    void addObserver(Observer *obs){
        observerList.push_back(obs);
    }

    void removeObserver(Observer *obs){
    }

    void notify(){
        for(int i=0;i<observerList.size();i++){
            observerList[i]->update();  
        }
    }
};

class Observer{
    public:
    virtual void update() = 0;
    virtual ~Observer() = default;
};

class ConcreteObserver : public Observer{
    int gotNotification = false;
    string name; int age;
    public:

    ConcreteObserver(string name, int age){
        this->name = name; 
        this->age = age;
    }

    void update(){
        gotNotification = true;
        cout << name << " notified\n";
    }
};

int main(){
    ConcreteObserver o1("Arush", 20);
    ConcreteObserver o2 ("Prush", 30);

    NotificationService co;
    co.addObserver(&o1);
    co.addObserver(&o2);
    co.notify();

}
