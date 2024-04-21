public class Task {
	private TaskType taskType;
	private int taskSize;
	
	public Task(String taskType, int taskSize) {
		this.taskType = new TaskType(taskType);
		taskSize = taskSize;
	}
	
	public Task(String taskType) {
		this(taskType, taskType.getDefaultSize());
	}

	public Task() {
		this("default");
	}
	
}
