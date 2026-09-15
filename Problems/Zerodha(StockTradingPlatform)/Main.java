import java.util.*;

class User {
    public int userId;
    public String userName;
    Account account;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.account = new Account();
    }
}

class Account {
    double balance;
    Portfolio portfolio;

    public Account() {
        balance = 0;
        portfolio = new Portfolio();
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }
}

class Portfolio {
    Map<Stock, Integer> holdings = new HashMap<>();

    public void addStock(Stock stock, int quantity) {
        holdings.put(stock, holdings.getOrDefault(stock, 0) + quantity);
    }

    public void removeStock(Stock stock, int quantity) {
        int currentQuantity = holdings.getOrDefault(stock, 0);

        if (currentQuantity >= quantity) {
            holdings.put(stock, currentQuantity - quantity);
        }
    }
}

class Stock {
    public int id;
    public String name;
    public int costPerUnit;

    public Stock(int id, String name, int costPerUnit) {
        this.id = id;
        this.name = name;
        this.costPerUnit = costPerUnit;
    }
}

abstract class Order {
    int orderId;
    Account account;
    Stock stock;
    int quantity;
    int price;
    OrderStatus status;

    public Order(int orderId, Account account, Stock stock, int quantity, int price) {
        this.orderId = orderId;
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.status = OrderStatus.PENDING;
    }

    abstract void execute();
}

class BuyOrder extends Order {
    public BuyOrder(int orderId, Account account, Stock stock, int quantity, int price) {
        super(orderId, account, stock, quantity, price);
    }

    @Override
    void execute() {
        int totalCost = quantity * price;

        if (account.balance >= totalCost) {
            account.withdraw(totalCost);
            account.portfolio.addStock(stock, quantity);
            status = OrderStatus.EXECUTED;
        }
    }
}

class SellOrder extends Order {
    public SellOrder(int orderId, Account account, Stock stock, int quantity, int price) {
        super(orderId, account, stock, quantity, price);
    }

    @Override
    void execute() {
        int totalAmount = quantity * price;
        int currentQuantity = account.portfolio.holdings.getOrDefault(stock, 0);

        if (currentQuantity >= quantity) {
            account.portfolio.removeStock(stock, quantity);
            account.deposit(totalAmount);
            status = OrderStatus.EXECUTED;
        }
    }
}

enum OrderStatus {
    PENDING,
    EXECUTED,
    CANCELLED
}

class StockBroker {
    private static StockBroker instance;

    private List<User> users = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    private StockBroker() {}

    public static StockBroker getInstance() {
        if (instance == null) {
            instance = new StockBroker();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void placeBuyOrder(int orderId, Account account, Stock stock, int quantity, int price) {
        Order order = new BuyOrder(orderId, account, stock, quantity, price);
        orders.add(order);
        order.execute();
    }

    public void placeSellOrder(int orderId, Account account, Stock stock, int quantity, int price) {
        Order order = new SellOrder(orderId, account, stock, quantity, price);
        orders.add(order);
        order.execute();
    }
}

public class Main {
    public static void main(String[] args) {
        StockBroker broker = StockBroker.getInstance();

        User user = new User(1, "Arush");
        Stock apple = new Stock(101, "Apple", 1000);

        broker.addUser(user);
        broker.addStock(apple);

        user.account.deposit(10000);

        broker.placeBuyOrder(1, user.account, apple, 5, 1000);
        broker.placeSellOrder(2, user.account, apple, 2, 1000);

        System.out.println(user.account.balance);
        System.out.println(user.account.portfolio.holdings.get(apple));
    }
}