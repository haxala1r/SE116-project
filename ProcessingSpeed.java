public class ProcessingSpeed {
	private TaskType taskType;
	private double speed;
	private double deviation;

	public ProcessingSpeed(TaskType t, double s, double d) {
		taskType = t;
		speed = s;
		deviation = d;
	}

	public TaskType getTaskType() {
		return taskType;
	}
	public double getSpeed() {
		return speed;
	}
	public double getDeviation() {
		return deviation;
	}
}
