public class Task {
	private TaskType taskType;
	private double taskSize;
	
	public Task(String taskType, double taskSize) {
		this.taskType = new TaskType(taskType);
		this.taskSize = taskSize;
	}
	
	public Task(String taskType) {
		this(taskType, taskType.getDefaultSize());
	}

	public Task() {
		this("default");
	}
	
}
