import java.util.ArrayList;

public class Station {
    private String stationID;
    private int maxCapacity;
    private double stationSpeed;
    private double stationSpeedPercentage;
    private ArrayList<Task> waitingTasks;
    private ArrayList<String> handleTypes;
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
        this.handleTypes = new ArrayList<>();
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

    public ArrayList<String> getHandleTypes() {
        return handleTypes;
    }

    public void setHandleTypes(ArrayList<String> handleTypes) {
        this.handleTypes = handleTypes;
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

    public void addTask(Task task) {
        waitingTasks.add(task);
    }

    // This can change after execute 
    public void removeTask(Task task) {
        waitingTasks.remove(task);
    }

    // TODO: Execute Tasks

    public void executeTasks() {
        if (waitingTasks.isEmpty()) {
            idle = true;
            return;
        }
        // TODO: while loop (index <= (?) waitingTasks.size())
    }

    // TODO: Assign Tasks (public / private (?))
    private void assignNextTask(Task task) {
        // TODO: Decide how to choose the task (?) if random:
        // int randomIndex = (int) (Math.random() * )
    }

}
