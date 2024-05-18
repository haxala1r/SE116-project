import java.util.ArrayList;
public class TaskType {
	private String taskTypeName;
	private double defaultTaskSize;
	private boolean hasDefaultSize;
	private static ArrayList<TaskType> allTaskTypes = new ArrayList<>();
	
	public TaskType(String taskTypeName, double defaultTaskSize) {
		this.taskTypeName = taskTypeName;
		this.defaultTaskSize = defaultTaskSize;
		hasDefaultSize = true;
	}
	
	public TaskType(String taskTypeName) {
		this.taskTypeName = taskTypeName;
		hasDefaultSize = false;
	}
	
	public String getTaskTypeName() {
		return taskTypeName;
	}
	
	public double getDefaultTaskSize() {
		return defaultTaskSize;
	}
	public boolean hasDefaultSize() {
		return hasDefaultSize;
	}
	
	public static void addNewTaskType(TaskType newTaskType) {
		allTaskTypes.add(newTaskType);
	}
	public static ArrayList<TaskType> getAllTaskTypes() {
		// return a copy to avoid breaking encapsulation.
		return new ArrayList<>(allTaskTypes);
	}
	public static TaskType getTaskTypeByID(String id) {
		for (TaskType i : allTaskTypes) {
			if (i.taskTypeName.equals(id))
				return i;
		}
		return null;
	}
}
