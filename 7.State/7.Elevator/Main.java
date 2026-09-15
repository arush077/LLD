import java.util.*;

class Main{
    public static void main(String[] args){
        Building building = new Building(10);

        Elevator e1 = new Elevator();
        Elevator e2 = new Elevator();

        building.addElevator(e1);
        building.addElevator(e2);

        Request request = new Request(2, 8);

        e1.addRequest(request);

        while(!(e1.currentState instanceof DoorOpen)){
            e1.move();
        }

        e1.closeDoor();
    }
}

// context class, which will be used as the shared common object
class Elevator{
    List<Request> pendingReq = new ArrayList<>();
    int currentFloor;
    
    ElevatorState currentState;
    Idle idleState;
    MovingUp movingUpState;
    MovingDown movingDownState;
    DoorOpen doorOpenState;

    public Elevator(){
        idleState = new Idle();
        movingUpState = new MovingUp();
        movingDownState = new MovingDown();
        doorOpenState = new DoorOpen();

        currentState = idleState;
        currentFloor = 1;
    }

    public void addRequest(Request request){
        currentState.addRequest(this, request);
    }

    public void move(){
        currentState.move(this);
    }

    public void closeDoor(){
        currentState.closeDoor(this);
    }
}



class Building{
    List<Elevator> elevatorList = new ArrayList<>();
    List<Floor> floorList = new ArrayList<>();

    public Building(int floorsCount){
        for(int i = 1; i <= floorsCount; i++){
            floorList.add(new Floor(i));
        }
    }

    public void addElevator(Elevator elevator){
        elevatorList.add(elevator);
    }
}

class Floor{
    int floorNo;

    public Floor(int floorNo){
        this.floorNo = floorNo;
    }
}

class Request{
    int srcFloor;
    int destFloor;

    public Request(int srcFloor, int destFloor){
        this.srcFloor = srcFloor;
        this.destFloor = destFloor;
    }
}

interface ElevatorState{
    void addRequest(Elevator elevator, Request request);
    void move(Elevator elevator);
    void closeDoor(Elevator elevator);
}

class Idle implements ElevatorState{

    public void addRequest(Elevator elevator, Request request){
        elevator.pendingReq.add(request);

        if(elevator.currentFloor < request.srcFloor){
            elevator.currentState = elevator.movingUpState;
        }
        else if(elevator.currentFloor > request.srcFloor){
            elevator.currentState = elevator.movingDownState;
        }
        else if(elevator.currentFloor < request.destFloor){
            elevator.currentState = elevator.movingUpState;
        }
        else if(elevator.currentFloor > request.destFloor){
            elevator.currentState = elevator.movingDownState;
        }
        else{
            elevator.currentState = elevator.doorOpenState;
        }
    }

    public void move(Elevator elevator){
        System.out.println("Elevator is idle");
    }

    public void closeDoor(Elevator elevator){
        System.out.println("Door is already closed");
    }
}

class MovingUp implements ElevatorState{

    public void addRequest(Elevator elevator, Request request){
        elevator.pendingReq.add(request);
    }

    public void move(Elevator elevator){
        Request request = elevator.pendingReq.get(0);

        int targetFloor;

        if(elevator.currentFloor < request.srcFloor){
            targetFloor = request.srcFloor;
        }
        else{
            targetFloor = request.destFloor;
        }

        elevator.currentFloor++;

        System.out.println("Moving up to floor " + elevator.currentFloor);

        if(elevator.currentFloor == targetFloor){
            if(targetFloor == request.srcFloor && request.srcFloor != request.destFloor){
                if(request.destFloor > request.srcFloor){
                    elevator.currentState = elevator.movingUpState;
                }
                else{
                    elevator.currentState = elevator.movingDownState;
                }
            }
            else{
                elevator.currentState = elevator.doorOpenState;
            }
        }
    }

    public void closeDoor(Elevator elevator){
        System.out.println("Cannot close door while elevator is moving");
    }
}

class MovingDown implements ElevatorState{

    public void addRequest(Elevator elevator, Request request){
        elevator.pendingReq.add(request);
    }

    public void move(Elevator elevator){
        Request request = elevator.pendingReq.get(0);

        int targetFloor;

        if(elevator.currentFloor > request.srcFloor){
            targetFloor = request.srcFloor;
        }
        else{
            targetFloor = request.destFloor;
        }

        elevator.currentFloor--;
        System.out.println("Moving down to floor " + elevator.currentFloor);
        if(elevator.currentFloor == targetFloor){
            if(targetFloor == request.srcFloor && request.srcFloor != request.destFloor){
                if(request.destFloor > request.srcFloor){
                    elevator.currentState = elevator.movingUpState;
                }
                else{
                    elevator.currentState = elevator.movingDownState;
                }
            }
            else{
                elevator.currentState = elevator.doorOpenState;
            }
        }
    }

    public void closeDoor(Elevator elevator){
        System.out.println("Cannot close door while elevator is moving");
    }
}

class DoorOpen implements ElevatorState{

    public void addRequest(Elevator elevator, Request request){
        elevator.pendingReq.add(request);
    }

    public void move(Elevator elevator){
        System.out.println("Cannot move while door is open");
    }

    public void closeDoor(Elevator elevator){
        System.out.println("Door closed at floor " + elevator.currentFloor);

        elevator.pendingReq.remove(0);

        if(elevator.pendingReq.isEmpty()){
            elevator.currentState = elevator.idleState;
        }
        else{
            Request request = elevator.pendingReq.get(0);

            if(elevator.currentFloor < request.srcFloor){
                elevator.currentState = elevator.movingUpState;
            }
            else if(elevator.currentFloor > request.srcFloor){
                elevator.currentState = elevator.movingDownState;
            }
            else if(elevator.currentFloor < request.destFloor){
                elevator.currentState = elevator.movingUpState;
            }
            else if(elevator.currentFloor > request.destFloor){
                elevator.currentState = elevator.movingDownState;
            }
        }
    }
}