public class Task {
	public class MissingSizeException extends Exception {
		public MissingSizeException() {
			super("No size specified for the task nor the task type it has had a default size.");
		}
	}
	
	private String taskID;
	private TaskType taskType;
	private double taskSize;
	private double processingTime;
	private boolean isCompleted;
	
	public Task(String taskID, TaskType taskType, double taskSize) {
		this.taskID = taskID;
		this.taskType = taskType;
		this.taskSize = taskSize;
		processingTime = calculateExecutionTime(taskSize, 1.0, 0.0);
		isCompleted = false;
	}
	
	public Task(String taskID, TaskType taskType) throws MissingSizeException {
		this(taskID, taskType, taskType.getDefaultTaskSize());
		if (taskType.getDefaultTaskSize() == -1.0)
			throw new MissingSizeException();
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

	public void reduceProcessingTime(double time) {
        processingTime -= time;
        if (processingTime < 0) {
            processingTime = 0; 
        }
        if (processingTime == 0) {
            completeTask();
        }
    }

	private double calculateExecutionTime(double taskSize, double stationSpeed, double stationSpeedPercentage) {
        double actualSpeed = stationSpeed * (1 + (stationSpeedPercentage * (Math.random() * 2 - 1)));
        return taskSize / actualSpeed;
    }

	public void completeTask() {
		isCompleted = true;
	}
	
	public boolean isTaskCompleted() {
		return isCompleted;
	}
}
