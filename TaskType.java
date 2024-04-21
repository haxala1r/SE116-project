public class TaskType {
	private String taskTypeName;
	private int defaultTaskSize;
	
	public TaskType(String taskTypeName, int defaultTaskSize) {
		this.taskTypeName = taskTypeName;
		this.defaultTaskSize = defaultTaskSize;
	}
	
	public TaskType(String taskTypeName) {
		this(taskTypeName, 5);
	}
	
	public TaskType() {
		this("default");
	}
	
	public String getTaskTypeName() {
		return taskTypeName;
	}
	
	public int getDefaultTaskSize() {
		return defaultTaskSize;
	}
}
