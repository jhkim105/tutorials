package jhkim105.tutorials.escqrs.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jhkim105.tutorials.escqrs.event.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventStore {

  private Map<String, List<Event>> store = new HashMap<>();

  public void addEvent(String id, Event event) {
    List<Event> events = store.get(id);
    if (events == null) {
      events = new ArrayList<Event>();
      events.add(event);
      store.put(id, events);
    } else {
      events.add(event);
    }
  }

  public List<Event> getEvents(String id) {
    return store.get(id);
  }
}
