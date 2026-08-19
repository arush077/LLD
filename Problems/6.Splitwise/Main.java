import java.util.ArrayList;
import java.util.List;

//--------------------------------------------------------Main Layer----------------------------------------------------------//
public class Main {
    public static void main(String args[]){

        
        User a = new User(1, "A");
        User b = new User(2, "B");
        User c = new User(3, "C");
        
        Group trip = new Group();
        trip.addMember(a);
        trip.addMember(b);
        trip.addMember(c);

        Expense expense = new Expense(100, a);

        ExactSplit ExactSplit = new ExactSplit();
        SplitwiseService splitwiseService = new SplitwiseService(ExactSplit);
        splitwiseService.addExpense(expense);
        splitwiseService.addGroup(trip);

        splitwiseService.addUser(a);
        splitwiseService.addUser(b);
        splitwiseService.addUser(c);

        ArrayList <User> useArrayList = new ArrayList<>(List.of(a,b,c));

        splitwiseService.splitCalculate(expense, useArrayList);

    }
}

// ---------------------------------------------------------------Split Service----------------------------------------------------//

class SplitwiseService{
    // U G E, remember for split service (User,Group,Expense)

    ArrayList<Group> groupList;
    ArrayList<User> userList;
    ArrayList<Expense> expenseList;
    SplitStrategy splitStrategy;

    // Empty UGE assign kara diya constructor me 
    // But Splitstrategy sahi assign karwa diya
    public SplitwiseService(SplitStrategy splitStrategy) {
        groupList = new ArrayList<>();
        userList = new ArrayList<>();
        expenseList = new ArrayList<>();
        this.splitStrategy = splitStrategy;
    }

    void addUser(User user){
        userList.add(user);
    }

    void addGroup(Group g){
        groupList.add(g);
    }

    void addExpense(Expense e){
        expenseList.add(e);
    }

    void splitCalculate(Expense expense, ArrayList<User> userList){
        splitStrategy.calculateSplit(expense, userList);
    }
}

//----------------------------------------------------Group + User ------------------------------------------------//

// Group maintains a list of user and few methods like addMember, removeMember 
class Group{
    ArrayList<User> userList = new ArrayList<>();

    void addMember(User user){
        userList.add(user);
    }

    void removeMember(User user){
        userList.remove(user);
    }
}

// User standard = id hoga aur ek naam hoga
class User{
    int userId;
    String name;

    public User(int userId, String name){
        this.userId = userId;
        this.name = name;
    }
}


//----------------------------------------------------Expense + Split ------------------------------------------------//


// What is expense even 
// 100 paid by A, and split between A, B, C 
// 100 paid by A is expense (amount = 100, paidBy = A)
// splits = (A,33) , (B,33) , (C,33) are also stored inside of the Expense


class Expense{
    ArrayList<Split> splitList = new ArrayList<>();

    private int amount;
    private User paidBy;
    public Expense(int amount, User paidBy) {
        this.amount = amount;
        this.paidBy = paidBy;
    }

    public int getAmount() {
        return amount;
    }

    public User getpaidBy(){
        return paidBy;
    }
}


// What is expense even 
// 100 paid by A, and split between A, B, C 
// splits = (A,33) , (B,33) , (C,33) so (user,amount) is one individual split
class Split{
    int userId;
    int amount;
    public Split(int userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}

// ---------------------------------------------------------------Split Strategy----------------------------------------------------//

interface SplitStrategy{
    //Takes 2 parameters : 1. Expense +  2.Users {Iss Expense ko Users ke beech me split maaro and after splitting store these splits in the expense only}
    ArrayList<Split> calculateSplit(Expense expense, ArrayList<User> userList); 
}


// Use Splitting strat to split and then after the expense is split among the users then store these splits in the expense only.
class ExactSplit implements SplitStrategy{
    public ArrayList<Split> calculateSplit(Expense expense, ArrayList<User> userList){
        int n = userList.size();

        int total_amount = expense.getAmount();
        User user_who_paid = expense.getpaidBy();

        int amount_per_user = total_amount/n;

        System.out.println("Splitting expense with " + n + "people. Which was initially paid by :" + amount_per_user);

        ArrayList<Split> splitList = new ArrayList<>();
        for(int i=0;i<n;i++){
        User user_i = userList.get(i);
        Split split = new Split(user_i.userId,amount_per_user);
        splitList.add(split);
        }

        // Saving the splits back again in the Expense Class only 
        // Expense ko Users me split maarke, wapas expenseClass me hi save karo
        expense.splitList = splitList; 
        return splitList;
    }
}



