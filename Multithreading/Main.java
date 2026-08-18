package Multithreading;

class KitchenExample implements Runnable{ 
    public void run(){
        //call the method having critical section code
        useOven();
    }

    public void useOven(){  //Oven is one, there can be multiple threads
        System.out.println("Order received");

        try{Thread.sleep(1000);} //Making the order
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Order delivered");
    }
}

public class Main{
    public static void main(String []args){

        KitchenExample k = new KitchenExample();
        Thread t1 = new Thread(k);
        Thread t2 = new Thread(k);
        Thread t3 = new Thread(k);

        t1.start();
        t2.start();
        t3.start();

        // Which is better : Java does not support multiple inheritance (extends multiple nhi ho skte) | but supports mutiple implements
        // So, Runnable is better ==> class c1 implements Runnable,c2,c3,c4..... bohot saare implement kr skta

    }
}
