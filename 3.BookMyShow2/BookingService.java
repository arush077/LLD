import java.util.ArrayList;


class BookingService{
    private PaymentService paymentService;

    //Injecting another service via the constructor only
    BookingService(PaymentService paymentService){
       this.paymentService = paymentService;
    }


    public BookingTicket bookTickets(User user,Show show, ArrayList<Seat> seatList){


        if(seatList.isEmpty()){
            System.out.println("Pls select seats first!");
        }

        for(int i=0;i<seatList.size();i++){
            if(!(seatList.get(i).isAvailable())){
                System.out.println("Seat: " + i + " is booked man! pls try another seat");
                return null;
            }
        }

        //All are empty so mark them booked now
        ArrayList<Seat> booked_seats = new ArrayList<>();
        for(int i=0;i<seatList.size();i++){
            seatList.get(i).markBooked();
            booked_seats.add(seatList.get(i));
        }

        int amount = seatList.size() * 100;
        paymentService.processPayment(amount);


        BookingTicket b1 = new BookingTicket(user,show,booked_seats,amount);
        return b1;
    }
}

class City{
    ArrayList<Theatre> theatreList;
}

class Theatre{
    ArrayList<Screen> screenList;
}

class Screen{
    ArrayList<Show> showList;
}

class Show{
    ArrayList<Seat> seatList;
    Movie movie;
}

class Movie{
    private String name;

    Movie(String name){
        this.name = name;
    }
}

class Seat{
     boolean isAvailable = true;

     boolean isAvailable(){
        if(isAvailable){return true;}
        return false;
     }

     void markBooked(){
        isAvailable = false;
        return ;
     }
}

class User{
    private String name;
    private int age;

    User(String name, int age){
        this.name = name;
        this.age = age;
    }
}

class BookingTicket{
    User user;
    Show show;
    ArrayList<Seat> seatList;
    int amount;

    BookingTicket(User user, Show show, ArrayList<Seat> seatList, int amount){
        this.user = user;
        this.show = show;
        this.seatList = seatList;
        this.amount = amount;
        System.out.println("Booking Ticket Generated");
    }
}
