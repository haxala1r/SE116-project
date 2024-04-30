import java.util.ArrayList;
public class TaskType {
	private String taskTypeName;
	private double defaultTaskSize;
	private ArrayList<TaskType> allTaskTypes = new ArrayList<>();
	
	public TaskType(String taskTypeName, double defaultTaskSize) {
		this.taskTypeName = taskTypeName;
		this.defaultTaskSize = defaultTaskSize;
	}
	
	public TaskType(String taskTypeName) {
		this(taskTypeName, -1.0);
	}
	
	public String getTaskTypeName() {
		return taskTypeName;
	}
	
	public double getDefaultTaskSize() {
		return defaultTaskSize;
	}
	
	public static void addNewTaskType(TaskType newTaskType) {
		allTaskTypes.add(newTaskType);
	}
}
