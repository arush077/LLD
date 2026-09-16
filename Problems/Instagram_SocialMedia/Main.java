import java.util.*;

class SocialMediaService{
    private static SocialMediaService instance;
    NotificationService notificationService;

    private SocialMediaService(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    public static SocialMediaService getInstance(NotificationService notificationService){
        if(instance == null){instance = new SocialMediaService(notificationService);}
        return instance;
    }
}


class User{
    int id;
    String name;
    List<User> friendsList = new ArrayList<>();
    List<Post> postList = new ArrayList<>();

    void addFriend(User friend){
        friendsList.add(friend);
    }

    void removeFriend(User friend){
        friendsList.remove(friend);
    }
}

class Post{
    String title;
    String description;
    User author;

    List<Like> likes = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    List<Share> shares = new ArrayList<>();
}

class Like{
    User user;
}

class Comment{
    User user;
    String commentString;
}

class Share{
    User user;
}

interface NotificationObserver{
    notify();
}

class EmailObserver implements NotificationObserver {
    public void update(User user, String message) {
        System.out.println("Email to " + user.name + ": " + message);
    }
}





public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
