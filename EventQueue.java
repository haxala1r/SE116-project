public class EventQueue {
	public static double currentTime = 0;
	public ArrayList<Event> events;
	
	public EventQueue() {
		events = new ArrayList<Event>;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
}
	