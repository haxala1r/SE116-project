public class Event {
	private String msg;
	private double time;
	public Event(String msg, double time) {
		this.msg = msg;
		this.time = time;
	}

	public double getTime() {
		return time;
	}
	public String getMsg() {
		return msg;
	}
}
