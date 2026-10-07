class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
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

        Singleton.getInstance();
        Singleton.getInstance();
        Singleton.getInstance();
    }
}