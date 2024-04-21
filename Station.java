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
}
