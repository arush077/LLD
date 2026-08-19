// Code 2: With Observer Pattern
// Idea
// Subject = YouTubeChannel
// Observers = Subscribers
// Subscribers register themselves.
// Channel notifies all registered subscribers.
// Uploaded: Observer Pattern Tutorial
// Alice received notification: Observer Pattern Tutorial
// Bob received notification: Observer Pattern Tutorial
// Why this is good
// Channel does not know concrete subscriber types.
// New subscribers can be added without changing channel code.
// Loose coupling.
// One upload notifies many users automatically.
// Easy to extend and test.
#include<iostream>
#include <vector>
#include <string>
using namespace std;

// ----------------Observable and Concrete Obserable--------------- //


class Observable{
    public:
    virtual void notify()= 0;
    virtual ~Observable() = default;
};

//NotificationService is the concrete Observable [its like the context class with which user interacts]
class NotificationService : public Observable{
    // VVVIMP : <Observer*> as Observer is abstract and you cant have an object, so have pointer to its address
    // It is not pointing to the abstract class itself.
    //“I point to something that behaves like an Observer.” Can be concrete_observer1 , concrete_observer2 , concrete_observer3 ...... and so on
    // Dont put concrete_observer1 in the observerList we put Observer* so that it can be any of the concrete implementations of Observer
    vector<Observer*> observerList;            

    public : 

    void addObserver(Observer *obs){
        observerList.push_back(obs);
    }

    void removeObserver(Observer *obs){
        // observerList.erase(obs);
    }

    void notify(){
        for(int i=0;i<observerList.size();i++){
            observerList[i]->update();  //since its a pointer, we need to use "->"
            
        }
    }
};

// ----------------Observer and Concrete Observer--------------- //
class AlarmService : public Observable{
    vector<Observer*> observerList;
public:
    void addObserver(Observer *obs){
        observerList.push_back(obs);
    }
    void removeObserver(Observer *obs){
        // No implementation needed for now
    }
    void notify() override{
        for(size_t i = 0; i < observerList.size(); ++i){
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


// ----------------int main--------------- //

int main(){
    //First make concr observer
    ConcreteObserver o1("Arush", 20);
    ConcreteObserver o2 ("Prush", 30);

    //Then make one conc observable
    NotificationService co;
    co.addObserver(&o1); //since addObserver(Observer *obs) takes a pointer, send the address of o1
    co.addObserver(&o2);
    co.notify();

}
