package jhkim105.tutorials.es.service;

import java.util.List;
import java.util.UUID;
import jhkim105.tutorials.es.domain.User;
import jhkim105.tutorials.es.event.Event;
import jhkim105.tutorials.es.event.UserCreatedEvent;
import jhkim105.tutorials.es.repository.EventStore;

public class UserUtility {

    public static User recreateUserState(EventStore store, String userId) {
        User user = null;

        List<Event> events = store.getEvents(userId);
        for (Event event : events) {
            if (event instanceof UserCreatedEvent) {
                UserCreatedEvent e = (UserCreatedEvent) event;
                user = new User(userId, e.getName());
            }
        }

        return user;
    }

}