Remember 
1. private static Singleton instance; 
2. private Singleton(){}
3. public static Singleton getInstance()



why static? in both the instance and getInstance() ?
ANS : 
Reason1 : because now we want only one instance of the class, not instance of every object

Reason2 : we have static getInstance method, so the variables in it need to be static