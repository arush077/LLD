
import java.util.*;
import java.time.LocalDate;

class HotelManagementService{
    private static HotelManagementService instance;
    PaymentStrategy paymentStrategy;
    PricingStrategyFactory pricingStrategyFactory;
    HotelService hotelService;

    private HotelManagementService(PaymentStrategy paymentStrategy, PricingStrategyFactory pricingStrategyFactory, HotelService hotelService){
        this.paymentStrategy = paymentStrategy;
        this.pricingStrategyFactory = pricingStrategyFactory;
        this.hotelService = hotelService;
    }

    public static HotelManagementService getInstance(PaymentStrategy paymentStrategy, PricingStrategyFactory pricingStrategyFactory, HotelService hotelService){
        if(instance == null){instance = new HotelManagementService(paymentStrategy, pricingStrategyFactory, hotelService);}
        return instance;
    }


    //bookRoom
    Booking bookRoom(User user, Hotel hotel, RoomType roomType, LocalDate checkInDate, LocalDate checkouDate){

        // First get the available rooms 
        // If available room found
        // Do the payment 
        // hen change its status to reserved  
        // Return the Booking Object

        List<Room> availableRooms = hotel.getAvailableRooms(roomType);
        if(availableRooms.size() <= 0){System.out.println("No rooms available at the moment"); return null;}

            //Happy path : Rooms are available
            // Assigning first room 
            Room room = availableRooms.get(0);

            PricingStrategy pricingStrategy = pricingStrategyFactory.getStrategy(roomType);
            
            //Calculating the amount using pricing strat
            double amount = pricingStrategy.calculatePrice(room, checkInDate, checkouDate);

            //Processing the payment using payment strat
            paymentStrategy.processPayment(amount);

            //Mark the status as reserved
            hotel.markReserved(room);     

            //return the booking 
            return new Booking(user, hotel, room, amount, checkInDate, checkouDate);
    }
}

class HotelService{
    List<Hotel> hotelList = new ArrayList<>();

    public void addHotel(Hotel hotel){
        hotelList.add(hotel);
    }

    public void removeHotel(Hotel hotel){
        hotelList.remove(hotel);
    }
}

class Hotel{
    String name;
    List<Room> roomList;
    
    Hotel(String name, List<Room> roomList){
        this.name = name;
        this.roomList = roomList;
    }
    
    public List<Room> getAvailableRooms(RoomType roomType){
        List<Room> availableRoomList = new ArrayList<>();
        for(int i=0;i<roomList.size();i++){
            Room room = roomList.get(i);
            if(room.status == RoomStatus.AVAILABLE && room.roomType == roomType){
                availableRoomList.add(room);
            }
        }
        return availableRoomList;
    }
    
    public void markAvailable(Room room){
        room.status = RoomStatus.AVAILABLE;
    }
    
    public void markReserved(Room room){
        room.status = RoomStatus.RESERVED;
    }  
}

// -----------------------------------------Room------------------------------------------------------------//

class Room{
    RoomType roomType;
    RoomStatus status;

    Room(RoomType roomType){
        this.roomType = roomType;
        status = RoomStatus.AVAILABLE;
    }
}

enum RoomType{
    Single,
    Double,
    Deluxe,
}

enum RoomStatus{
    AVAILABLE,
    RESERVED
}

class User{
    int id;
    String name;
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Booking{
    User user;
    Hotel hotel;
    Room room;
    double amount;
    LocalDate checkInDate;
    LocalDate checkouDate;

    Booking(  User user, Hotel hotel, Room room, double amount, LocalDate checkInDate, LocalDate checkouDate){
        this.user = user;
        this.hotel = hotel;
        this.room = room;
        this.amount = amount;
        this.checkInDate = checkInDate;
        this.checkouDate = checkouDate;
    }

}

//-------------------------------- Pricing Strategy -----------------------------------//

class PricingStrategyFactory {
    public PricingStrategy getStrategy(RoomType roomType) {
        if(roomType == RoomType.Single) return new SingleRoomPricing();
        if(roomType == RoomType.Double) return new DoubleRoomPricing();
        if(roomType == RoomType.Deluxe) return new DeluxePricing();
        return null;
    }
}

interface PricingStrategy{
    double calculatePrice(Room room, LocalDate checkInDate, LocalDate checkoutDate); 
}

class SingleRoomPricing implements PricingStrategy{
    public double calculatePrice(Room room, LocalDate checkInDate, LocalDate checkoutDate){
        double duration = checkoutDate.toEpochDay() - checkInDate.toEpochDay();
        //Random Logic
        int multiplier = 1;
        double amount = multiplier * duration;
        return amount;
    }
}

class DoubleRoomPricing implements PricingStrategy{
    public double calculatePrice(Room room, LocalDate checkInDate, LocalDate checkoutDate){
        double duration = checkoutDate.toEpochDay() - checkInDate.toEpochDay();
        //Random Logic
        int multiplier = 2;
        double amount = multiplier * duration;
        return amount;
    }
}

class DeluxePricing implements PricingStrategy{
    public double calculatePrice(Room room, LocalDate checkInDate, LocalDate checkoutDate){
        double duration = checkoutDate.toEpochDay() - checkInDate.toEpochDay();
        //Random Logic
        int multiplier = 3;
        double amount = multiplier * duration;
        return amount;
    }
}

//-------------------------------- Payment Strategy -----------------------------------//

interface PaymentStrategy{
    // Price depends on 2 things
    // 1. The roomType (luxury wale will be costlier)
    // 2. The duration of the stay
    void processPayment(double amount); 
}

class CashPayment implements PaymentStrategy{
    public void processPayment(double amount){
        System.out.println("Payment done using cash : " + amount);
    }
}


class CardPayment implements PaymentStrategy{
    public void processPayment(double amount){
        System.out.println("Payment done using card : " + amount);;
    }
}

class UPIPayment implements PaymentStrategy{
    public void processPayment(double amount){
        System.out.println("Payment done using UPI : " + amount);
    }
}
//-----------------------------------------------------------------------------------------

class Main{
    public static void main(String []arg){

        // Making a user 
        User user1 = new User(1,"Arush");

        // Making a room 
        Room singleRoom = new Room(RoomType.Single);
        Room doubleRoom = new Room(RoomType.Double);

        // Making a hotel 
        Hotel h1 = new Hotel("GrandHotel",List.of(singleRoom, doubleRoom));


        //Putting it in the hotelService 
        HotelService hotelService = new HotelService();
        hotelService.addHotel(h1);


        //Making the payment strategy
        PaymentStrategy paymentStrategy = new UPIPayment();

        //Making the pricing strategy factory
        PricingStrategyFactory pricingStrategyFactory = new PricingStrategyFactory();

        HotelManagementService hotelManagementService = HotelManagementService.getInstance(paymentStrategy,pricingStrategyFactory, hotelService);
        Booking booking = hotelManagementService.bookRoom(user1, h1, RoomType.Single, LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 2));

        System.out.println(
        "Amount : " +  booking.amount + "\n" + 
        "User : " + booking.user.name + "\n" + 
        "Room" + booking.room.status
        );
    }
}