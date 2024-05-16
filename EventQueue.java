public class EventQueue {
	private static double currentTime = 0;
	private ArrayList<Event> events;
	
	public EventQueue() {
		events = new ArrayList<Event>();
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public double getCurrentTime() {
		return currentTime;
	}
}
	
