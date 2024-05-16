import java.util.ArrayList;
import java.util.List;

public class JobType{
   private String jobTypeID;
    private List<Task> tasks;
    private static ArrayList<JobType> allJobTypes = new ArrayList<>();

    public JobType(String jobTypeID) {
        this.jobTypeID = jobTypeID;
        this.tasks = new ArrayList<>();
        allJobTypes.add(this);
    }
   public void addTask(Task task) {
        tasks.add(task);
    }
  public String getJobTypeID() {
        return jobTypeID;
    }
  public void setJobTypeID(String jobTypeID) {
        this.jobTypeID = jobTypeID;
    }
   public List<Task> getTasks() {
        return tasks;
    }
   public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static ArrayList<JobType> getAllJobTypes() {
        return allJobTypes;
    }
}
