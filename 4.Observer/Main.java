import java.util.*;

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
// ----------------Observable and Concrete Obserable--------------- //

interface Observable {
    public void notifyObservers();
}

//NotificationService is the concrete Observable [its like the context class with which user interacts]
class NotificationService implements Observable {
    List<Observer> observerList = new ArrayList<>();

    void addObserver(Observer obs) {
        observerList.add(obs);
    }

    void removeObserver(Observer obs) {
        // observerList.remove(obs);
    }

    public void notifyObservers() {
        for (int i = 0; i < observerList.size(); i++) {
            observerList.get(i).update();
        }
    }
}


// ----------------Observer and Concrete Observer--------------- //

interface Observer {
    void update();
}


class ConcreteObserver implements Observer {

    boolean gotNotification = false;
    String name;
    int age;

    ConcreteObserver(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void update() {
        gotNotification = true;
        System.out.println("Notified");
    }
}


// ---------------- Main --------------- //

public class Main {

    public static void main(String[] args) {

        //First make concr observer
        ConcreteObserver o1 = new ConcreteObserver("Arush", 20);
        ConcreteObserver o2 = new ConcreteObserver("Prush", 30);

        //Then make one conc observable
        NotificationService co = new NotificationService();

        co.addObserver(o1);
        co.addObserver(o2);

        co.notifyObservers();
    }
}
