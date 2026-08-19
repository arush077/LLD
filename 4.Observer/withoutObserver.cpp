// Code 1: Without Observer Pattern
// Problem
// All subscribers are hardcoded inside the channel class.
// Why this is bad
// Channel knows every subscriber.
// Adding a new subscriber requires editing channel code.
// Tight coupling between channel and subscribers.
// Notification logic is duplicated.
// Hard to reuse and test.
// Why Observer Pattern is needed
// The channel should only say “a new video is uploaded”. It should not care who receives the notification.

#include <iostream>
using namespace std;

class YouTubeChannel {
public:
    void uploadVideo(string title) {

        // Upload video
        cout << "Uploaded: " << title << endl;

        // Notify subscriber 1
        cout << "Notify Alice" << endl;

        // Notify subscriber 2
        cout << "Notify Bob" << endl;
    }
};

int main() {
    YouTubeChannel channel;

    channel.uploadVideo("Observer Pattern Tutorial");

    return 0;
}