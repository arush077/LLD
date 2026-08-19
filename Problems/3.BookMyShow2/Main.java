import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Cash c1 = new Cash();
        PaymentService paymentService = new PaymentService(c1);
        

        User u1 = new User("Arush",20);

        BookingService bookingService = new BookingService(paymentService);
        ArrayList arr = new ArrayList<>();
        bookingService.bookTickets(u1, null,arr);

    }
}
