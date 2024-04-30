public class Task {
	private String taskID;
	private TaskType taskType;
	private double taskSize;
	
	public Task(String taskID, TaskType taskType, double taskSize) {
		this.taskID = taskID;
		this.taskType = taskType;
		this.taskSize = taskSize;
	}
	
	public Task(String taskID, TaskType taskType) {
		this(taskID, taskType, taskType.getDefaultSize());
	}

	public String getTaskID() {
		return taskID;
	}
	
	public TaskType getTaskType() {
		return taskType;
	}
	
	public double getTaskSize() {
		return taskSize;
	}
}
