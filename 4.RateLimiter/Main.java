import java.util.*;
public class Main {

    public static void main(String[] args) throws Exception {

        // 3 requests allowed in 10 seconds
        RateLimiterConfig config = new RateLimiterConfig(10, 3);

        RateLimiterStrategy strategy = new SlidingWindowLog(config);

        RateLimiterService service = new RateLimiterService(strategy);

        User freeUser = new User("u1", UserType.FREE);

        Request r1 = new Request(freeUser);
        Request r2 = new Request(freeUser);
        Request r3 = new Request(freeUser);
        Request r4 = new Request(freeUser);

        System.out.println(service.allow(r1)); // true
        System.out.println(service.allow(r2)); // true
        System.out.println(service.allow(r3)); // true
        System.out.println(service.allow(r4)); // false

        Thread.sleep(11000);

        Request r5 = new Request(freeUser);

        System.out.println(service.allow(r5)); // true
    }
}

// -------------------- Service --------------------

// 1. Service maintains the strategy object
// 2. Why "Request" object not in attribute = because request har bar alag alag ayega (dont store it as state)

class RateLimiterService {

    private RateLimiterStrategy strategy;

    //Strategy service ke constructor me hi set kar dena 100%
    public RateLimiterService(RateLimiterStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean allow(Request request) {
        return strategy.allowRequest(request);
    }
}

// -------------------- Config --------------------

class RateLimiterConfig {

    int windowSeconds;
    int maxRequests;

    public RateLimiterConfig(int windowSeconds, int maxRequests) {
        this.windowSeconds = windowSeconds;
        this.maxRequests = maxRequests;
    }
}

// -------------------- Strategy --------------------

interface RateLimiterStrategy {
     boolean allowRequest(Request request);
}

// -------------------- Sliding Window Log--------------------

class SlidingWindowLog implements RateLimiterStrategy {

    private int maxRequests;
    private int windowSeconds;

    // userId -> queue of timestamps
    private Map<String, Deque<Long>> userQueues = new HashMap<>();

    public SlidingWindowLog(RateLimiterConfig config) {
        this.maxRequests = config.maxRequests;
        this.windowSeconds = config.windowSeconds;
    }

    @Override
    public boolean allowRequest(Request request) {

        String userId = request.user.userId;
        long now = request.timestamp;

        userQueues.putIfAbsent(userId, new ArrayDeque<>());

        Deque<Long> q = userQueues.get(userId);

        // Remove expired timestamps
        while (!q.isEmpty() &&
                now - q.peekFirst() >= windowSeconds * 1000L) {

            q.pollFirst();
        }

        // Check limit
        if (q.size() >= maxRequests) {
            System.out.println("Rate limit exceeded for " + userId);
            return false;
        }

        // Allow request
        q.offerLast(now);

        return true;
    }
}

// -------------------- Request --------------------

// Taaki main se proper request banake RateLimiterService me bhej paae isilie Req object
class Request {

    User user;
    long timestamp;

    public Request(User user) {
        this.user = user;
        this.timestamp = System.currentTimeMillis();
    }
}

// -------------------- User --------------------

class User {

    String userId;
    UserType userType;

    public User(String userId, UserType userType) {
        this.userId = userId;
        this.userType = userType;
    }
}

enum UserType {
    FREE,
    PREMIUM
}