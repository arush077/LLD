import java.util.*;

// Publisher class will have a sendMessageToServer(msg,topicName) : we need to send msg to a topicName so have both
// so we are telling the server ki ye raha msg and ye raha the topic where u need to push the msg to.
class Publisher{
    int id;
    Server server;

    public Publisher(int id, Server server) {
        this.id = id;
        this.server = server;
    }

    void sendMessageToServer(String msg,String topicName){
        server.recieveMessageFromPublisher(msg,topicName);
    }
}

// Server will have a list of topics == so also keep a functionality to addTopic
// While receiving from the Publisher recieveMessageFromPublisher(msg,topicName) then find the topic and add in its queue
class Server{
    List<Topic> topicList = new ArrayList<>();  

    void recieveMessageFromPublisher(String msg,String topicName){
        //Its receiving it from the Publisher, so add it in that topic ka queue
        //Searching that topic in the topicList of the server
        for(int i=0;i<topicList.size();i++){
            if(topicList.get(i).topicName == topicName){
                //found it 
                Topic topic = topicList.get(i);
                topic.addinQueue(msg);
                break;
            }
        }
        System.out.println("Reached the server" + msg);
    }

    // We need to add topics to the server
    void addTopic(Topic newTopic){
        topicList.add(newTopic);
    }
}

// Topic will have a list of subscribers who can subscribe to it == so also keep a functionality to addSubscriber
// addinQueue fn of the Topic acts as the reciever end for Server ka recieveMessageFromPublisher
// sendMessageToAllSubscribers will send the queue elements to all the subscribers and pop it from queue
class Topic{
    int id;
    String topicName;
    public Topic(String topicName) {
        this.topicName = topicName;
    }

   Queue<String> q = new ArrayDeque<>();
   List<Subscriber> subscriberList = new ArrayList<>();


   void addinQueue(String msg){
        q.add(msg);
   }

    //Lets now send this to all the subscribed Subscriber
    //No message parameter, kyuki queue ke peek me jobhi hoga wahi denge aur pop(remove) karenge na
    void sendMessageToAllSubscribers(){
        String msg = q.peek();
        q.remove();

        for(int i=0;i<subscriberList.size();i++){
            Subscriber subscriber = subscriberList.get(i);
            subscriber.recieveMessageFromTopic(msg);
        } 
    } 
    
    void addSubscriber(Subscriber newsubscriber){
        subscriberList.add(newsubscriber);
    }
}

class Subscriber{
    int id;
    String name;

    public Subscriber(String name) {
        this.name = name;
    }

    void recieveMessageFromTopic(String msg){
        System.out.println("Reached the Subscriber" + msg);
    }
}

class Main{
    public static void main(String[] args){
        //Message banao
        String msg = "Hi, this is just a check";

        //Start writing from the end of the flow (ULTA)
        // First make subsriber -->then make topic and add subscriber ---> then make server and add the topic ---> then make publisher
       
        // Making subscriber
        Subscriber subscriber1 = new Subscriber("Arush");
        
        // Making topic and adding subscriber
        Topic sportsTopic = new Topic("Sports");
        sportsTopic.addSubscriber(subscriber1);

         // Making topic and adding topics
        Server server = new Server();
        server.addTopic(sportsTopic);

        // Making publisher
        Publisher publisher = new Publisher(10,server);

        //sendMessageToServer will send the msg to server which will push the msg in the queue of this "Sports" topic
        //sendMessageToAllSubscribers will send all the queue elements from the topic to all the subscribers
        publisher.sendMessageToServer(msg,"Sports");
        sportsTopic.sendMessageToAllSubscribers();
    }
}