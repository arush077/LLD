import java.util.*;

class Publisher{
    int id;
    Server server;

    public Publisher(int id, Server server) {
        this.id = id;
        this.server = server;
    }

    void sendMessageToServer(String msg){
        server.recieveMessageFromPublisher(msg);
    }
}

class Server{
    Subscriber subscriber;    

    public Server(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    void recieveMessageFromPublisher(String msg){
        //Its receiving it from the Publisher, so add it in the queue
        System.out.println("Reached the server" + msg);
        sendMessageToSubscriber(msg);
    }

    //Lets now send this to the Subscriber
    void sendMessageToSubscriber(String msg){
        subscriber.recieveMessageFromServer(msg);
    }  
}

class Subscriber{
    int id;

    void recieveMessageFromServer(String msg){
        System.out.println("Reached the Subscriber" + msg);
    }
}



class Topic{
    int id;
    String topicName;
    List<MessageQueue> messageQueuesList = new ArrayList<>(); //each topic can have multiple queue
}

class MessageQueue{
    int id;
    Queue<Message> queue = new ArrayDeque<>();

}

class Message{
    Publisher p;
    Subscriber s;
    String text;

    Message(String input){
        text = input;
    }
}



class Main{
    public static void main(String[] args){
        String msg = "Hi, this is just a check";

        Subscriber subscriber1 = new Subscriber();
        Server server = new Server(subscriber1);
        Publisher publisher = new Publisher(10,server);

        publisher.sendMessageToServer(msg);
    }
}