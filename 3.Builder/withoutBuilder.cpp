// ================= PRODUCT CLASS =================
class User {

    private String name;
    private int age;
    private String job;
    private String location;

    User(String name, int age, String job, String location) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Name=" + name + " Age=" + age + " Job=" + job + " Location=" + location;
    }
}

// ================= MAIN =================

public class Main {

    public static void main(String[] args) {

        // All parameters have to be passed manually
        User u1 = new User("Arush", 22, "SWE", "Mumbai");

        // Even if job is not needed, we still have to provide it
        User u2 = new User("Arush", 22, "Not set", "Mumbai");

        System.out.println(u1.toString());
        System.out.println(u2.toString());
    }
}