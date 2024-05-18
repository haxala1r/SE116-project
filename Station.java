import java.util.ArrayList;

public class Station {
    private String stationID;
    private int maxCapacity;
	private ArrayList<ProcessingSpeed> processingSpeeds;
    private ArrayList<Task> waitingTasks;
    private ArrayList<Task> tasksInProgress;
    private boolean MULTIFLAG;
    private boolean FIFOFLAG;
    private boolean idle;

    public Station(String stationID, int maxCapacity, ArrayList<ProcessingSpeed> pss, boolean MULTIFLAG, boolean FIFOFLAG) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
		processingSpeeds = pss;
        this.MULTIFLAG = MULTIFLAG;
        this.FIFOFLAG = FIFOFLAG;
        this.idle = true;
        this.waitingTasks = new ArrayList<>();
        this.tasksInProgress = new ArrayList<>();
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Task> getWaitingTasks() {
        return waitingTasks;
    }

    public void setWaitingTasks(ArrayList<Task> waitingTasks) {
        this.waitingTasks = waitingTasks;
    }

    public ArrayList<Task> getTasksInProgress() {
        return tasksInProgress;
    }

    public void setTasksInProgress(ArrayList<Task> tasksInProgress) {
        this.tasksInProgress = tasksInProgress;
    }

    public boolean isMULTIFAG() {
        return MULTIFLAG;
    }

    public void setMULTIFAG(boolean MULTIFLAG) {
        this.MULTIFLAG = MULTIFLAG;
    }

    public boolean isFIFOFLAG() {
        return FIFOFLAG;
    }

    public void setFIFOFLAG(boolean FIFOFLAG) {
        this.FIFOFLAG = FIFOFLAG;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    private void processTask(Task task) {
		if (tasksInProgress.size() >= maxCapacity)
			return;
		ProcessingSpeed speed = getProcessingSpeed(task);
		task.recalculateExecutionTime(speed.getSpeed(), speed.getDeviation());
		tasksInProgress.add(task);
        idle = false;
		
		Event e = new Event("Station " + stationID + " has begun processing Task " + task.getTaskID(), EventQueue.getCurrentTime());
		EventQueue.addEvent(e);
    }

    public void addTask(Task task) {
        if (task == null) {
            System.out.println("Invalid task type.");
            return;
        }

        if (idle || tasksInProgress.size() < maxCapacity) {
            processTask(task);
        } else {
            waitingTasks.add(task);
        }
    }

	private void reallocateCapacity() {
		while (!waitingTasks.isEmpty() && tasksInProgress.size() < maxCapacity) {
			processTask(waitingTasks.remove(0)); // TODO: allow for different scheduling systems
		}
	}

    public void executeTasks() throws Exception {
        for (Task task : tasksInProgress) {
            ProcessingSpeed speed = getProcessingSpeed(task);
            if (speed != null) {
                double stationSpeed = speed.getSpeed();
                task.reduceProcessingTime(stationSpeed);
            } else {
                throw new Exception("Processing speed not found for this task: " + task.getTaskID());
            }
        }
        tasksInProgress.removeIf(Task::isTaskCompleted);
        reallocateCapacity();
		if (tasksInProgress.isEmpty() && waitingTasks.isEmpty()) {
            idle = true;
        }
    }

    public ProcessingSpeed getProcessingSpeed(Task task) {
        TaskType taskType = task.getTaskType();
    
        for (ProcessingSpeed speed : processingSpeeds) {
            if (speed.getTaskType().equals(taskType)) {
                return speed;
            }
        }
        return null;
    }

	public boolean canHandleTaskType(TaskType taskType) {
		for (ProcessingSpeed speed : processingSpeeds) {
			if (speed.getTaskType().getTaskTypeName().equals(taskType.getTaskTypeName()))
				return true;
		}
		return false;
	}

    public Event nextEvent() {
        if (tasksInProgress.isEmpty() && waitingTasks.isEmpty()) {
            return null; // return null as a way of saying there's no Event at all
        }

        Task nextTask = null;
        double minRemainingTime = Double.MAX_VALUE;
		
		/* Check if any tasks are already finished 
		 * Sometimes doubles just aren't precise enough, such as when a station's speed is 7
		 * but task size is 3. In these cases, the time left can be a double that is bigger than
		 * zero, but practically so close to zero that doubles aren't precise enough to count that
		 * time properly. To prevent these cases, we pretend any task that has a small enough time left
		 * is completed.
		 */
		ArrayList<Task> completed = new ArrayList<>();
		for (Task task : tasksInProgress) {
			if (task.getTimeLeft() <= 0.000001) {
				completed.add(task);
			}
		}
		for (Task task : completed) {
			completeTask(task);
		}

		/* Check the remaining tasks for the earliest finishing one */
        for (Task task : tasksInProgress) {
            double remainingTime = task.getTimeLeft();
            if (remainingTime < minRemainingTime) {
                minRemainingTime = remainingTime;
                nextTask = task;
            }
        }
		
        if (nextTask != null) {
            return new Event("Task " + nextTask.getTaskID() + " will complete.", EventQueue.getCurrentTime() + minRemainingTime);
        } else {
            return null;
        }
    }
	public void passTime(double time) {
		// tasksInProgress holds tasks that are actually being executed, so
		// time passing will affect all of them at once. waitingTasks are unaffected.
		ArrayList<Task> completed = new ArrayList<>(); 
		// completed is necessary, because we can't modify tasksInProgress in-loop
		for (Task task : tasksInProgress) {
			task.reduceProcessingTime(time);
			if (task.isTaskCompleted()) {
				completed.add(task);
			}
		}
		for (Task task : completed) {
			completeTask(task);
		}
		reallocateCapacity();
	}

    public void removeTask(Task task) {
        waitingTasks.remove(task);
    }

    public void completeTask(Task task) {
        tasksInProgress.remove(task);
        EventQueue.addEvent(new Event("Station " + stationID + " completed Task " + task.getTaskID(), EventQueue.getCurrentTime()));
        if (tasksInProgress.isEmpty())
            idle = true;
    }

    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<>();
        for (Task task : tasksInProgress) {
            if (task.isTaskCompleted()) { 
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }
}
