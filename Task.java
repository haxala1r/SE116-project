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
	private double jobDeadline;
	private boolean isCompleted;
	private boolean processing;
	
	public Task(String taskID, TaskType taskType, double taskSize) {
		this.taskID = taskID;
		this.taskType = taskType;
		this.taskSize = taskSize;
		processingTime = 0;
		isCompleted = false;
		processing = false;
	}
	
	public Task(String taskID, TaskType taskType) throws MissingSizeException {
		this(taskID, taskType, taskType.getDefaultTaskSize());
		if (!taskType.hasDefaultSize())
			throw new MissingSizeException();
	}

	// copy constructor (can't help it, C++ has spoiled me)
	public Task(Task other, double jd) {
		this(other.taskID, other.taskType, other.taskSize);
		jobDeadline = jd;
	}

	public double getJobDeadline() {
		return jobDeadline;
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
        if (processingTime < 0.000001) {
            processingTime = 0; 
			completeTask();
        }
    }

	public void recalculateExecutionTime(double stationSpeed, double stationSpeedPercentage) {
        double actualSpeed = stationSpeed * (1 + (stationSpeedPercentage * (Math.random() * 2 - 1)));
        processingTime = taskSize / actualSpeed;
		processing = true;
    }

	public double getTimeLeft() {
		return processingTime;
	}

	public void completeTask() {
		isCompleted = true;
	}
	public void startProcessing() {
		processing = true;
	}
	
	public boolean isTaskCompleted() {
		return isCompleted;
	}
	public boolean isBeingProcessed() {
		return processing;
	}
}
