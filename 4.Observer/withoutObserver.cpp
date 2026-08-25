// Code : Without Observer Pattern
// Issue : All subscribers are hardcoded inside the YouTubeChannel class.
// Why this is bad
// YouTubeChannel knows every subscriber.
// Adding a new subscriber requires editing YouTubeChannel code.
// Tight coupling between YouTubeChannel and subscribers.

import java.util.*;

// Without Observer Pattern

class YouTubeChannel {
    List<Subscriber> subscribers = new ArrayList<>();

    void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    void uploadVideo(String videoTitle) {
        System.out.println("Uploaded: " + videoTitle);
        for (Subscriber subscriber : subscribers) {
            subscriber.notifyUser();
        }
    }
}

class Subscriber {
    String name;
    Subscriber(String name) {
        this.name = name;
    }

    void notifyUser() {
        System.out.println(name + " received notification");
    }
}

public class Main {
    public static void main(String[] args) {
        Subscriber s1 = new Subscriber("Alice");
        Subscriber s2 = new Subscriber("Bob");
        YouTubeChannel channel = new YouTubeChannel();

        channel.addSubscriber(s1);
        channel.addSubscriber(s2);

        channel.uploadVideo("Observer Pattern Tutorial");
    }
}