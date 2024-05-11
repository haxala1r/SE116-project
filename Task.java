public class Task {
	private String taskID;
	private TaskType taskType;
	private double taskSize;
	private double processingTime; // New added
	
	public Task(String taskID, TaskType taskType, double taskSize) {
		this.taskID = taskID;
		this.taskType = taskType;
		this.taskSize = taskSize;
		this.processingTime = calculateExecutionTime(taskSize, 0, 0); // New added
	}
	
	public Task(String taskID, TaskType taskType) throws TaskType.MissingSizeException {
		this(taskID, taskType, taskType.getDefaultTaskSize());
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

	// New added
	public void reduceProcessingTime(double time) {
        processingTime -= time;
        if (processingTime < 0) {
            processingTime = 0; 
        }
    }

	private double calculateExecutionTime(double taskSize, double stationSpeed, double stationSpeedPercentage) {
        double actualSpeed = stationSpeed * (1 + (stationSpeedPercentage * (Math.random() * 2 - 1)));
        return taskSize / actualSpeed;
    }
}
