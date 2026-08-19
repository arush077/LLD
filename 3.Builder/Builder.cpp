#include <iostream>
#include <string>
using namespace std;

// Forward declaration as we need to declare friend class inside UserClass
class UserBuilder;

// ================= PRODUCT CLASS =================
class User {
private:
    string name;
    int age;
    string job;
    string location;

    // PRIVATE CONSTRUCTOR Confusion point: Why private? So that nobody can create User directly. This forces everyone to use UserBuilder.
    // Just one constructor is enough setting all the values
    User(string n, int a, string j, string l) : name(n), age(a), job(j), location(l) {}

    // Confusion point: Why friend? Because constructor is private. UserBuilder is allowed to access private members/constructors.
    friend class UserBuilder;

public:
    void display() {
        cout << "Name     : " << name << endl;
        cout << "Age      : " << age << endl;
        cout << "Job      : " << job << endl;
        cout << "Location : " << location << endl;
    }
};

// ================= BUILDER CLASS =================
class UserBuilder {
private:
    // Saare attribues of the Product wapas copy maar :<
    string name; //Required
    int age; //Required
    string job = "Not specified"; //Optional
    string location = "Not specified"; //Optional

public:
    // 1. Mandatory fields, set them in the builder constructor
    UserBuilder(string n, int a)
        : name(n), age(a) {}

    // 2. OPTIONAL FIELDs (Not set in the builder constructor)
    // Confusion point: Why UserBuilder& ? Return reference to SAME object, not a copy. So that baadme "." marke chain kar paae
    UserBuilder& setJob(string job) {
        this->job = job;
        // Confusion point: Why return *this ? *this = current object itself. Returning it allows chaining: // builder.setJob(...).setLocation(...)
        // If we wrote 'return this', type would be UserBuilder* and chaining would become: builder.setJob(...)->setLocation(...)
        return *this;
    }

    UserBuilder& setLocation(string location) {
        this->location = location;
        return *this;
    }


    // IMP : Finally call the build method : which calls the constructor of the Product to actually make it
    // 3. Build method in the builder which calls the actual User class
    User build() {
        // Allowed because UserBuilder is friend of User
        return User(name, age, job, location);
    }
};



// ================= MAIN =================
int main() {

    // Build object using CHAINING (dot dot dot karke, no need to remember how parameters were adjusted)
    User u1 = UserBuilder("Arush", 22)
                    .setJob("Software Engineer")
                    .setLocation("Mumbai")
                    .build();

    cout << "User 1" << endl;
    u1.display();

    cout << endl;

    // Only mandatory fields
    User u2 = UserBuilder("Rahul", 25).build();

    cout << "User 2" << endl;
    u2.display();

    // This would give compilation error because constructor of User is private:
    // U are only allowed to use UserBuilder to make Users and not User class to make Users
    // User u3("Amit", 30, "Dev", "Pune");

    return 0;
}