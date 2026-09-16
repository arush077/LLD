import java.util.*;

// ShoppingCartService is singleton because it is the central service or central coordinator
class ShoppingCartService{
    private static ShoppingCartService instance;
    InventoryService inventoryService;
    PaymentStrategy paymentStrategy;

    private ShoppingCartService(InventoryService inventoryService, PaymentStrategy paymentStrategy){
        this.inventoryService = inventoryService;
        this.paymentStrategy = paymentStrategy;
    }

    public static ShoppingCartService getInstance(InventoryService inventoryService, PaymentStrategy paymentStrategy){
        if(instance == null){instance = new ShoppingCartService(inventoryService, paymentStrategy);}
        return instance;
    }

    public void addProductToInventory(Product product){
        inventoryService.addProductToInventory(product);
    }

    //-------------------------------------------------------------------//

    // 1. Main function for adding to Cart
    public void addToCart(User user, Product product, int requestedQuantity){
        CartItem cartItem = new CartItem(product, requestedQuantity); 
        user.cart.addCartItem(cartItem);
    }

    //-------------------------------------------------------------------//

    // 2. Main function for placing the Order
    public void placeOrder(User user){

    // 1. Check if the inventory has enough stock?
    for (CartItem cartItem : user.cart.itemList) {
        if(!(inventoryService.isAvailable(cartItem.product,cartItem.requestedQuantity))){
            System.out.println("Not enough stock in the inventory!");
            return ;
        }
    }

    // 2. Calculate the price
    double totalPrice = user.cart.calculateTotalPriceCart();
    System.out.println("Thanks" +user.name + " for placing order, total price : " +  totalPrice);

    // 3. Processing payment
    paymentStrategy.processPayment();

    // 4. Cleanup : update the inventory
    for (CartItem cartItem : user.cart.itemList) {
        inventoryService.reduceStock(cartItem.product,cartItem.requestedQuantity);
    }

    // 5. Cleanup : clear the cart
    user.cart.clearCart();
    }
}

//---------------------------------------User---------------------------------------------//
class User{
    int id;
    String name;
    Cart cart; // IMP : User has a Cart and not the central service
    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.cart = new Cart();
    }
}

//---------------------------------------Cart---------------------------------------------//
class Cart{
    List<CartItem> itemList = new ArrayList<>();

    void addCartItem(CartItem cartItem){
        itemList.add(cartItem);
    }

    void removeCartItem(CartItem cartItem){
        itemList.remove(cartItem);
    }

    void updateCartItem(CartItem cartItem, int newQuantity){
        cartItem.requestedQuantity = newQuantity;
    }

    void clearCart() {
        itemList.clear();
    }

    public double calculateTotalPriceCart(){
        // all the products of the cart are purchased 
        double totalCost = 0;
        for(int i=0;i<itemList.size();i++){
            CartItem cartItem = itemList.get(i);
            int requestedQuantity = cartItem.requestedQuantity;
            double productPrice = cartItem.product.price;
            totalCost = totalCost + requestedQuantity*productPrice;
        }
        return totalCost;
    }
}

class CartItem{
    Product product;
    int requestedQuantity;
    public CartItem(Product product, int requestedQuantity) {
        this.product = product;
        this.requestedQuantity = requestedQuantity;
    }
}

//---------------------------------------Inventory Service---------------------------------------------//
class InventoryService {
    private List<Product> productsList = new ArrayList<>();

    public void addProductToInventory(Product product){
        productsList.add(product);
    }

    public boolean isAvailable(Product product, int requestedQuantity) {
        return product.stock >= requestedQuantity;
    }

    public void reduceStock(Product product, int requestedQuantity) {
        product.stock -= requestedQuantity;
    }
}

class Product{
    String name;
    double price;
    String description;
    int stock;
    public Product(String name, double price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }
}

//---------------------------------------Payment Strategies---------------------------------------------//

interface PaymentStrategy{
    public void processPayment();
}

class UPIPayment implements PaymentStrategy{
    public void processPayment(){
        System.out.println("Paying via UPI");
    }
}

class CashPayment implements PaymentStrategy{
    public void processPayment(){
        System.out.println("Paying via Cash");
    }
}

//---------------------------------------Main---------------------------------------------//
public class Main {
    public static void main(String []args){
    
        // Making the inventory and filling the products first
        InventoryService inventoryService = new InventoryService();
        Product Pen = new Product("Pen", 10, null, 2);
        Product Book = new Product("Book", 20, null, 2);
        Product Shirt = new Product("Shirt", 100, null, 2);

        inventoryService.addProductToInventory(Pen);
        inventoryService.addProductToInventory(Book);
        inventoryService.addProductToInventory(Shirt);

        
        UPIPayment upiPayment = new UPIPayment();

        // Singleton / Main service
        ShoppingCartService shoppingCartService = ShoppingCartService.getInstance(inventoryService, upiPayment);


        // Now user can addToCart and placeOrder
        User Arush = new User(10,"Arush");

        shoppingCartService.addToCart(Arush, Pen,5);
        shoppingCartService.placeOrder(Arush);

    }
}
