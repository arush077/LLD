import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        User freeUser = new User("u1", UserType.FREE);


        // =========================================================
        // 1. SLIDING WINDOW LOG
        // =========================================================

        System.out.println("\n===== SLIDING WINDOW LOG =====");

        // 3 requests allowed in 10 seconds
        RateLimiterStrategy strategy =
                new SlidingWindowLog(3, 10);

        RateLimiterService service =
                new RateLimiterService(strategy); //Service ke constructor me strategy bhejo

        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // false

        Thread.sleep(11000);

        System.out.println(service.allow(new Request(freeUser))); // true


        // =========================================================
        // 2. FIXED WINDOW COUNTER
        // =========================================================

        System.out.println("\n===== FIXED WINDOW COUNTER =====");

        // 3 requests allowed in 5 seconds
        strategy =
                new FixedWindowCounter(3, 5);

        service =
                new RateLimiterService(strategy);  //Service ke constructor me strategy bhejo

        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // false

        Thread.sleep(6000);

        System.out.println(service.allow(new Request(freeUser))); // true


        // =========================================================
        // 3. TOKEN BUCKET
        // =========================================================

        System.out.println("\n===== TOKEN BUCKET =====");

        // 2 tokens added every second
        // Maximum 3 tokens in bucket

        strategy =
                new TokenBucket(2, 3);

        service =
                new RateLimiterService(strategy);  //Service ke constructor me strategy bhejo

        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // false

        // Wait for 1 second
        Thread.sleep(1000);

        // Approximately 2 tokens should be available
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // false


        // =========================================================
        // 4. LEAKY BUCKET
        // =========================================================

        System.out.println("\n===== LEAKY BUCKET =====");

        // 1 request leaks every second
        // Maximum 3 requests in queue

        strategy =
                new LeakyBucket(1, 3);

        service =
                new RateLimiterService(strategy);  //Service ke constructor me strategy ka obj bhejo

        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // true
        System.out.println(service.allow(new Request(freeUser))); // false

        // Wait for 1 second
        Thread.sleep(1000);

        System.out.println(service.allow(new Request(freeUser))); // true
    }
}

// -------------------- Service --------------------

// 1. Service maintains the strategy object
// 2. Why "Request" object not in attribute = because request har bar alag alag ayega (dont store it as state)

class RateLimiterService {

    private RateLimiterStrategy strategy;

    //Strategy service ke constructor me hi set kar dena 100%
    // Dependecy injection 
    // Dependecy = strategy usko inject karrahe he using constructor into Service Class
    public RateLimiterService(RateLimiterStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean allow(Request request) {
        return strategy.allowRequest(request);
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

    public SlidingWindowLog(int maxRequests, int windowSeconds) {
        this.maxRequests = maxRequests;
        this.windowSeconds = windowSeconds;
    }

    @Override
    public boolean allowRequest(Request request) {

        String userId = request.user.userId;
        long now = request.timestamp; //

        userQueues.putIfAbsent(userId, new ArrayDeque<>());

        Deque<Long> q = userQueues.get(userId);

        // Remove expired timestamps
        while (!q.isEmpty() &&
                now - q.peekFirst() >= windowSeconds * 1000L) {

            q.pollFirst();
        }

        // Check limit
        if (q.size() >= maxRequests) {
            System.out.println(
                    "Error 429 : Rate limit exceeded for " + userId
            );
            return false;
        }

        // Allow request
        q.offerLast(now);

        return true;
    }
}


// -------------------- Fixed Window Counter --------------------

class FixedWindowCounter implements RateLimiterStrategy {

    private int maxRequests;
    private int windowSeconds;

    // userId -> window state
    private Map<String, WindowState> userWindows = new HashMap<>();

    public FixedWindowCounter(int maxRequests, int windowSeconds) {
        this.maxRequests = maxRequests;
        this.windowSeconds = windowSeconds;
    }

    @Override
    public boolean allowRequest(Request request) {

        String userId = request.user.userId;
        long now = request.timestamp;

        userWindows.putIfAbsent(
                userId,
                new WindowState(now)
        );

        WindowState window = userWindows.get(userId);

        // Check if current window has expired
        if (now - window.windowStartTime
                >= windowSeconds * 1000L) {

            // Start a new window
            window.windowStartTime = now;
            window.requestCount = 0;
        }

        // Check limit
        if (window.requestCount >= maxRequests) {

            System.out.println(
                    "Error 429 : Rate limit exceeded for " + userId
            );

            return false;
        }

        // Allow request
        window.requestCount++;

        return true;
    }

    static class WindowState {

        long windowStartTime;
        int requestCount;

        WindowState(long windowStartTime) {
            this.windowStartTime = windowStartTime;
            this.requestCount = 0;
        }
    }
}


// -------------------- Token Bucket --------------------

class TokenBucket implements RateLimiterStrategy {

    int refillRate;
    int capacity;

    // userId -> bucket
    private Map<String, Bucket> buckets = new HashMap<>();

    TokenBucket(int refillRate, int capacity) {
        this.refillRate = refillRate;
        this.capacity = capacity;
    }

    @Override
    public boolean allowRequest(Request request) {

        String userId = request.user.userId;
        long now = request.timestamp;

        // Create bucket for user if it doesn't exist
        buckets.putIfAbsent(
                userId,
                new Bucket(capacity, now)
        );

        Bucket bucket = buckets.get(userId);

        // Calculate how much time has passed
        long elapsedMillis = now - bucket.lastRefillTime;

        double elapsedSeconds = elapsedMillis / 1000.0;

        // Add tokens based on elapsed time
        double tokensToAdd = elapsedSeconds * refillRate;

        bucket.tokens = Math.min(
                capacity,
                bucket.tokens + tokensToAdd
        );

        // Update last refill time
        bucket.lastRefillTime = now;

        //request will check if there is a token present in the bucket?
        if (bucket.tokens >= 1) {

            // Consume one token
            bucket.tokens--;

            return true;
        }

        System.out.println(
                "Error 429 : Rate limit exceeded for " + userId
        );

        return false;
    }

    static class Bucket {

        double tokens;
        long lastRefillTime;

        Bucket(double tokens, long lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }
}


// -------------------- Leaky Bucket --------------------

class LeakyBucket implements RateLimiterStrategy {

    double leakRate;
    int capacity;

    // userId -> bucket
    private Map<String, Bucket> buckets = new HashMap<>();

    LeakyBucket(double leakRate, int capacity) {
        this.leakRate = leakRate;
        this.capacity = capacity;
    }

    @Override
    public boolean allowRequest(Request request) {

        String userId = request.user.userId;
        long now = request.timestamp;

        // Create bucket for user if it doesn't exist
        buckets.putIfAbsent(
                userId,
                new Bucket(now)
        );

        Bucket bucket = buckets.get(userId);

        // Calculate how much time has passed
        long elapsedMillis = now - bucket.lastProcessTime;

        double elapsedSeconds = elapsedMillis / 1000.0;

        // Remove requests based on leak rate
        int requestsToRemove =
                (int) (elapsedSeconds * leakRate);

        for (int i = 0;
             i < requestsToRemove && !bucket.queue.isEmpty();
             i++) {

            bucket.queue.pollFirst();
        }

        // Update last process time
        if (requestsToRemove > 0) {
            bucket.lastProcessTime = now;
        }

        // Check if bucket is full
        if (bucket.queue.size() >= capacity) {

            System.out.println(
                    "Error 429 : Rate limit exceeded for " + userId
            );

            return false;
        }

        // Add request to bucket
        bucket.queue.offerLast(request);

        return true;
    }

    static class Bucket {

        Deque<Request> queue = new ArrayDeque<>();
        long lastProcessTime;

        Bucket(long lastProcessTime) {
            this.lastProcessTime = lastProcessTime;
        }
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
