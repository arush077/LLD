#include <iostream>
#include <string>
using namespace std;

class User {
private:
    string name;
    int age;
    string job;
    string location;

public:
    // All fields must be passed together
    User(string n, int a, string j, string l)
        : name(n), age(a), job(j), location(l) {}

    void display() {
        cout << "Name     : " << name << endl;
        cout << "Age      : " << age << endl;
        cout << "Job      : " << job << endl;
        cout << "Location : " << location << endl;
    }
};

int main() {

    // Must remember the exact parameter order
    User u1("Arush", 22, "Software Engineer", "Mumbai");

    cout << "User 1" << endl;
    u1.display();

    cout << endl;

    // Must still pass placeholder values for optional fields as we have a single constructor only 
    // Other option is to have multiple constructor : One constructor for each possibility
    // Without Builder : The user of the class must remember the order of all parameters.
    User u2("Rahul", 25, "Not specified", "Not specified");

    cout << "User 2" << endl;
    u2.display();

    return 0;
}