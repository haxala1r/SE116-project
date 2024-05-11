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
        // boolean taskAlreadyAdded = false;
        if (task == null) {
            System.out.println("Invalid task type.");
            return;
        }

        // if (taskAlreadyAdded) {
        //     System.out.println("This task type already added.");
        //     return;
        // }

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
        for (int i = 0; i < tasksInProgress.size(); i++) {
            Task task = tasksInProgress.get(i);
            // task.reduceProcessingTime(); // Take from task
            // if (task.getProcessingTime() <= 0) {
            //     completeTask(task);
            //     i--; 
            // }
        }
        while (!waitingTasks.isEmpty() && tasksInProgress.size() < maxCapacity) {
            Task nextTask = waitingTasks.remove(0); 
            processTask(nextTask); 
        }
    }

    private double calculateExecutionTime(double taskSize) {
        double actualSpeed = stationSpeed * (1 + (stationSpeedPercentage * (Math.random() * 2 - 1)));
        return taskSize / actualSpeed;
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

}
