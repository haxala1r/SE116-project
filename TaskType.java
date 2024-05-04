import java.util.ArrayList;
public class TaskType {
	public class MissingSizeException extends Exception {
		public MissingSizeException() {
			super("No size specified for the task nor the task type had a default size.");
		}
	}
	private String taskTypeName;
	private double defaultTaskSize;
	private static ArrayList<TaskType> allTaskTypes = new ArrayList<>();
	
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
	
	public double getDefaultTaskSize() throws MissingSizeException {
		if (defaultTaskSize == -1.0) throw new MissingSizeException();
		return defaultTaskSize;
	}
	
	public static void addNewTaskType(TaskType newTaskType) {
		allTaskTypes.add(newTaskType);
	}
	public static ArrayList<TaskType> getAllTaskTypes() {
		// return a copy to avoid breaking encapsulation.
		return new ArrayList<>(allTaskTypes);
	}
}
