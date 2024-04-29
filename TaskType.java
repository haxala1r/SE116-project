public class TaskType {
	private String taskTypeName;
	private double defaultTaskSize;
	
	public TaskType(String taskTypeName, double defaultTaskSize) {
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
	
	public double getDefaultTaskSize() {
		return defaultTaskSize;
	}
}
