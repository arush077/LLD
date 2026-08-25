import java.util.*;

// With Observer Pattern

// Subject = YouTubeChannel
// Observers = Subscribers
// Subscribers register themselves.
// Channel notifies all registered subscribers.

// ---------------- Observable and Concrete Observable ----------------
interface Observable {
    void notifyObservers();
}

class YouTubeChannel implements Observable {
    List<Observer> observerList = new ArrayList<>();

    void addObserver(Observer obs) {
        observerList.add(obs);
    }

    void removeObserver(Observer obs) {
        observerList.remove(obs);
    }

    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }

    void uploadVideo(String videoTitle) {
        System.out.println("Uploaded: " + videoTitle);
        notifyObservers();
    }
}

// ---------------- Observer and Concrete Observer ----------------
interface Observer {
    void update();
}

class Subscriber implements Observer {
    String name;

    Subscriber(String name) {
        this.name = name;
    }

    public void update() {
        System.out.println(name + " received notification");
    }
}

// ---------------- Main ----------------
public class Main {
    public static void main(String[] args) {

        // First make concrete observers
        Subscriber s1 = new Subscriber("Alice");
        Subscriber s2 = new Subscriber("Bob");

        // Then make concrete observable
        YouTubeChannel channel = new YouTubeChannel();

        // Subscribers register themselves
        channel.addObserver(s1);
        channel.addObserver(s2);

        // Channel uploads video and notifies all subscribers
        channel.uploadVideo("Observer Pattern Tutorial");
    }
}