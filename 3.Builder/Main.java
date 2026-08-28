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

// ================= BUILDER CLASS =================
class UserBuilder {
    // Saare attribues of the Product wapas copy maar :<
    private String name; // Required
    private int age; // Required
    private String job = "Not set"; // Optional (have given them default values in case when not set)
    private String location = "Not set"; // Optional (have given them default values in case when not set)

    // 1. Mandatory fields, set them in the builder constructor
    // 2. OPTIONAL FIELDs (Not set in the builder constructor)
    // Confusion point: Why UserBuilder& ? Return reference to SAME object, not a
    // copy. So that baadme "." marke chain kar paae
    UserBuilder(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserBuilder setJob(String job) {
        this.job = job;
        return this;
    }

    public UserBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    // IMP : Finally call the build method : which will create a new object of the product
    User build() {
        return new User(name, age, job, location);
    }
}

// ================= MAIN =================
public class Main {
    public static void main(String[] args) {

        // Build object using CHAINING (dot dot dot karke, no need to remember how
        // parameters were adjusted)
        User u1 = new UserBuilder("Arush", 22).setJob("SWE").setLocation("Mumbai").build();

        User u2 = new UserBuilder("Arush", 22).setLocation("Mumbai").build();

        System.out.println(u1.toString());
        System.out.println(u2.toString());
    }
}