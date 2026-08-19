class Kitchen implements Runnable{ 
    public void run(){
        //call the method having critical section code
        useOven();
    }

    // Oven is one, there can be multiple threads
    public void useOven(){  
        
        String LOCK = "Lock";
        // Synchronized block : 
        // only one thread can enter at a time
        synchronized(LOCK){
            //Preparing the dish in here
            System.out.println( Thread.currentThread().getName());
            System.out.println("Preparing started");

            try{Thread.sleep(1000);} 
            catch(InterruptedException e){ e.printStackTrace();}   

            System.out.println("Preparing ended");
        }
   
    }
}

class Main{
    public static void main(String []args){

        Kitchen k = new Kitchen();
        Thread t1 = new Thread(k);
        Thread t2 = new Thread(k);
        Thread t3 = new Thread(k);

        t1.start();
        t2.start();
        t3.start();

        // Interview qn 
        // Which is better : extends Thread or implements Runnable
        // Java does not support multiple inheritance (extends multiple nhi ho skte) | but supports mutiple implements
        // So, Runnable is better ==> class c1 implements Runnable,c2,c3,c4..... bohot saare implement kr skta
    }
}
