#include<iostream>
using namespace std;

class Singleton{
    private :

    //1. Make the object static 
    static Singleton* s;
    Singleton(){}


    public : 
    int a = 10;

    //2. static Singleton function, so it can be called without making the object
    static Singleton* getInstance(){
        if(s==NULL){
        s = new Singleton();
        }
        return s;
    }
};


//3. Definition of the static member
// In C++, a static data member needs one definition outside the class.
Singleton* Singleton::s = NULL;

int main(){
    Singleton* s = Singleton::getInstance();
    cout<< s->a<<endl;
}