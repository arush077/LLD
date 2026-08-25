class Singleton {

    // Public constructor
    public Singleton() {
        System.out.println("Creating new instance");
    }
}

public class Main {
    public static void main(String[] args) {

        // Without Singleton, constructor private nahi hoga, static instance nahi hoga, 
        // Aur har new Singleton() se new object banega:
        Singleton a = new Singleton();
        Singleton b = new Singleton();
        Singleton c = new Singleton();
    }
}