import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String args []){
        Building be = new Building(10); //iske andar hi 10 floor ban jaenge lol
        Elevator e1 = new Elevator();
        Elevator e2 = new Elevator();

        be.addElevatorList(List.of(e1,e2));



        e1.addRequest(new Request(new Floor(1),new Floor(2)));
    }
    
}




class Building {
    List <Elevator> elevatorList;
    List<Floor> floorList;

    public Building(int floors_count){

        floorList = new ArrayList<>(); //setting the floorList initially to empty default value (IMP)

        for(int i=1;i<floors_count;i++){
            Floor floor = new Floor(i);
            floorList.add(floor);
        }
    }


    public void addElevatorList(List<Elevator> elevatorList){
        this.elevatorList = elevatorList;
    }


}

class Floor{
    int floorNumber;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}

class Elevator{
    ArrayList <Request> pendingReq; //Maintaining Pending Req ka Array
    ElevatorState currenState; //Maintaing the common state variable

    public Elevator(){
        pendingReq = new ArrayList<>();
    }

    void addRequest(Request re){
        System.out.println("Request recieved" + re);
        pendingReq.add(re);
    }
}

interface ElevatorState{
    public void addRequest(Elevator elevator, Request req);
    public void move(Elevator elevator);
    public void closeDoor(Elevator elevator);
}


class Idle implements ElevatorState{
    DoorOpen doorOpenState;
    MovingUp movingUpState;
    MovingDown movingDownState;
    public void addRequest(Elevator elevator, Floor src, Floor dest){

        if(src == dest){
            // koi request nahi add karne vector me lol 
        }
        else if(dest.getFloorNumber()>src.getFloorNumber()){elevator.currenState =  movingUpState;}
        else if(dest.getFloorNumber()<src.getFloorNumber()){elevator.currenState =  movingDownState;}
    }

    
    public void move(Floor src, Floor dest){}

    public void closeDoor(Floor src, Floor dest){}

}


class MovingUp implements ElevatorState{
    public void addRequest(Elevator elevator, Floor src, Floor dest){}
    public void move( Elevator elevator, Floor src, Floor dest){

    }
    public void closeDoor(Floor src, Floor dest){}
}

class MovingDown implements ElevatorState{
    public void addRequest(Elevator elevator, Floor src, Floor dest){}
    public void move(Floor src, Floor dest){}
    public void closeDoor(Floor src, Floor dest){}
}

class DoorOpen implements ElevatorState{
    public void addRequest(Elevator elevator, Floor src, Floor dest){}
    public void move(Floor src, Floor dest){}
    public void closeDoor(Floor src, Floor dest){}
}




class Request{
    Floor srcFloor;
    Floor destinationFloor;
    public Request(Floor srcFloor, Floor destinationFloor) {
        this.srcFloor = srcFloor;
        this.destinationFloor = destinationFloor;
    } 
}