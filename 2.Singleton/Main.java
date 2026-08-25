class Singleton {
    // The single instance, initially null
    private static Singleton instance;
    
     // Private constructor to prevent instantiation
    private Singleton() {}
    
    // public static Singleton getInstance() ==> static as it should be called without obj
    public static Singleton getInstance() { 
        if (instance == null) { 
            instance = new Singleton(); 
            System.out.println("Creating new instance");
        } 
        return instance;
    }
}

public class Main{
    public static void main(String[] args) {

        // You need to create obj like this only Singleton.getInstance(); as constructor is pvt
        Singleton.getInstance();
        Singleton.getInstance();
        Singleton.getInstance();
    }
}