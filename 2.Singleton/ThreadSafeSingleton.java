public class ThreadSafeSingleton {
    // volatile ensures that multiple threads handle the instance variable correctly
    private static volatile ThreadSafeSingleton instance;

    // Private constructor prevents external instantiation
    private ThreadSafeSingleton() {
        System.out.println("Creating thread-safe singleton instance");
    }

    // Double‑checked locking for lazy, thread‑safe initialization
    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}