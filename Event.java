public class Event {
	private String message;
	private double startTime;
	public Event(String message, double startTime) {
		this.message = message;
		this.startTime = startTime;
	}

	public double getStartTime() {
		return startTime;
	}
	public String getMessage() {
		return message;
	}
}
