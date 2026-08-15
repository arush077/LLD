#include <iostream>
#include "PaymentStrategy.hpp"
using namespace std;


//Core flow for bookmyshow
// City --> Theatre --> Screen --> Show ---> Movies + Seats

class Movie{
    string name; 
    public : 
    Movie(string name){
        this->name = name;
    }
};


class Seat{
    bool isBooked;

    public : 
    void markBooked(){
        isBooked = true;
    }

    bool isAvailable(){
        return !isBooked;
    }
};



class Show{
    Movie *movie;
    vector<Seat*> seats;
};


class Screen{
    vector<Show*> showList;
    void addshow(Show* show){
        showList.push_back(show);
    }
};


class Theatre{
    vector<Screen*> ScreenList;
    void addScreen(Screen* screen){
        ScreenList.push_back(screen);
    }
};

class City{
    vector<Theatre*> theatreList;

    public : 
    void addTheathre(Theatre* theatre){
        theatreList.push_back(theatre);
    }

};


class User;
class Booking {
    User* user;
    Show* show;
    vector<Seat*> bookedSeats;
    int amount;

    public : 
    Booking(User* user, Show* show,vector<Seat*> bookedSeats,int amount  ){
        this->user = user;
        this->show = show;
        this->bookedSeats = bookedSeats;
        this->amount = amount;
    }
};


//-----------------------------Notification Service----------------------------//
class User{
    string name;
    int age;
    public : 

    User(string name, int age){
        this->name = name;
        this->age = age;
    }

    void update(){
        cout<<name<<"User got updated"<<endl;
    }
};


class NotificationService{
    vector<User*> observerList;

    public : 
    void addObserver(User* u){
        observerList.push_back(u);
    }

    void notify(){
        for(int i=0;i<observerList.size();i++){
            observerList[i]->update();
        }
    }
};



//-----------------------------Booking Service----------------------------//

class BookingService{
    PaymentService *paymentService;
    NotificationService *notificationService;
    public : 

    //usually constructor me saare services ko daalo 
    BookingService(PaymentService *paymentService, NotificationService *notificationService){
        this->paymentService = paymentService;
        this->notificationService = notificationService;
    }



    // One user can book one show, but multiple seats
    Booking bookTicket(User* user, Show* show, vector<Seat*> seatList, PaymentStrategy *ps){

        if(seatList.empty()){
            throw runtime_error("No seats selected");
        }

        //lets check if these seats are available or not 
        for(int i=0;i<seatList.size();i++){
            if(!(seatList[i]->isAvailable())){
                cout<<"Seat :"<<seatList[i]<<"is already booked";
            }
        }

        //okay all seats are free to be booked, so mark all as booked
        vector<Seat*> bookedSeats;
        for(int i=0;i<seatList.size();i++){
            seatList[i]->markBooked();
            bookedSeats.push_back(seatList[i]);
        }

        int amount = seatList.size()*100;
        Booking b1(user,show,bookedSeats,amount);
        cout<<"tickets have been booked! Cost:"<<amount<<endl;



        // Payment Service me call marega
        paymentService->processPayment(amount);

        //Notification Service me call marega
        notificationService->notify();


        return b1;
    }
};

















int main(){

    //Payment service ka concrete class object banaya
    UPI upi; //Lets say you paid using upi, aur options bhi the like CreditCard or Cash
    PaymentService ps(&upi);


    User u1("Arush",23); 
    NotificationService ns;
    ns.addObserver(&u1); // NotificationService ka concrete class to user hi he
    


    // Booking Service me baaki services walo ko inject kardiya via constructor
    BookingService bs(&ps,&ns); 



    
    
    
    Movie Interstellar("Interstellar");
    Show show1;
    Seat s1;
    vector<Seat*> v = {&s1};
    bs.bookTicket(&u1,&show1,v,&upi);


}