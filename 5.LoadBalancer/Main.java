import java.util.ArrayList;
import java.util.List;

//-------------------------------------Client Side Code---------------------------------------------//
public class Main{
    public static void main(String []args){

        //LoadBalancerService banane ke lie jo jo chahiye pehle wo sab bana rahe
        //We need serverList and ek RoundRobin ka object
        Server s1 = new Server(0);
        Server s2 = new Server(1);
        Server s3 = new Server(2);
        ArrayList<Server> serverList = new ArrayList<>( List.of(s1,s2,s3) );
        RoundRobin rr = new RoundRobin();

        // Service ka object banaya
        LoadBalancerService lb = new LoadBalancerService(serverList,rr); 


        // Ab request maarte gaye LB pe 
        Request r1 = new Request("/");
        lb.routeRequest(r1);
        lb.routeRequest(new Request("/abc"));
        lb.routeRequest(new Request("/abc2"));
        lb.routeRequest(new Request("/abc3"));
        lb.routeRequest(new Request("//"));
        lb.routeRequest(new Request("//"));
    }
}

//-------------------------------------Service---------------------------------------------//
// 1. Service maintains the serverList + strategy object
// 2. It will call the strategy.routeRequest method using the strategy object and pass serverList in parameter
// 3. Why "Request" object not in attribute = because request har bar alag alag ayega (dont store it as state)
// 4. serverList in attribute because serverList is permanent
// 5. isilie we take request as parameter and forward it to Strategy ka routeRequest

class LoadBalancerService{
    ArrayList<Server> serverList;
    LoadBalancerStrategy loadBalancerStrategy;

    //Strategy service ke constructor me hi set kar dena 100%
    public LoadBalancerService(ArrayList<Server> serverList, LoadBalancerStrategy loadBalancerStrategy){
        this.serverList = serverList;
        this.loadBalancerStrategy = loadBalancerStrategy;
    }

    public Server routeRequest(Request r){
        return loadBalancerStrategy.routeRequest(serverList, r);
    }
}

//-------------------------------------Server and request---------------------------------------------//

// Server ki bas ek id hogi jisse we can identify it
class Server{
    int id;

    public Server(int id) {
        this.id = id;
    }
}

// Request me User not needed for round robin, haan agar userIP based chahiye hota tab rakh skte the
class Request {
    // User user;
    String path;

    public Request(String path) {
        this.path = "/";
    }
}

//-------------------------------------Strategy Layer---------------------------------------------//
// Most IMP function: Server routeRequest(serverList, req) : it returns a Server, picking one from the serverList

interface LoadBalancerStrategy{
    Server routeRequest(ArrayList <Server> serverList, Request req);
}


// Most IMP Logic of RR : new_id = (prev_id + 1)%n; and then return serverList.get(new_id) from the array
class RoundRobin implements LoadBalancerStrategy{
    private int prev_id = -1;

    public Server routeRequest(ArrayList <Server> serverList, Request req){
        //Simply round robin karna he 
        int n = serverList.size();
        int new_id = (prev_id + 1)%n;
        System.out.println("Routing to server :" + new_id);

        //Updating the prev_index 
        prev_id = new_id;
        //Returning the server from the serverList having the new_id
        return serverList.get(new_id);
    }
}
