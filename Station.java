import java.util.ArrayList;

public class Station {
    private String stationID;
    private int maxCapacity;
    private double stationSpeed;
    private double stationSpeedPercentage;
    private ArrayList<Task> waitingTasks;
    private ArrayList<Task> tasksInProgress;
    private boolean MULTIFLAG;
    private boolean FIFOFLAG;
    private boolean idle;

    public Station(String stationID, int maxCapacity, double stationSpeed, double stationSpeedPercentage, boolean MULTIFLAG, boolean FIFOFLAG) {
        this.stationID = stationID;
        this.maxCapacity = maxCapacity;
        this.stationSpeed = stationSpeed;
        this.stationSpeedPercentage = stationSpeedPercentage;
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

    public double getStationSpeed() {
        return stationSpeed;
    }

    public void setStationSpeed(double stationSpeed){
        this.stationSpeed = stationSpeed;
    }

    public double getStationSpeedPercentage() {
        return stationSpeedPercentage;
    }

    public void setStationSpeedPercentage(double stationSpeedPercentage) {
        this.stationSpeedPercentage = stationSpeedPercentage;
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
        tasksInProgress.add(task);
        idle = false; 
        System.out.println("Task " + task.getTaskID() + " is processing at station.");
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
            System.out.println("Task " + task.getTaskID() + " added to waiting list at station.");
        }
    }

    public void executeTasks() {
        if (waitingTasks.isEmpty()) {
            idle = true;
            return;
        }
        for (Task task : tasksInProgress) {
            task.reduceProcessingTime(stationSpeed);
        }
        tasksInProgress.removeIf(Task::isTaskCompleted);
        while (!waitingTasks.isEmpty() && tasksInProgress.size() < maxCapacity) {
            Task nextTask = waitingTasks.remove(0);
            processTask(nextTask);
        }
        if (tasksInProgress.isEmpty() && waitingTasks.isEmpty()) {
            idle = true;
        }
    }

    public void removeTask(Task task) {
        waitingTasks.remove(task);
    }

    public void completeTask(Task task) {
        tasksInProgress.remove(task);
        System.out.println("Task " + task.getTaskID() + " completed at station.");
        if (tasksInProgress.isEmpty()) {
            idle = true;
        }
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
