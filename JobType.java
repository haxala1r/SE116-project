import java.util.ArrayList;
import java.util.List;

public class JobType{
   private String jobTypeID;
    private ArrayList<Task> tasks;
    private static ArrayList<JobType> allJobTypes = new ArrayList<>();

    public JobType(String jobTypeID) throws Exception {
        this.jobTypeID = jobTypeID;
        this.tasks = new ArrayList<>();
        allJobTypes.add(this);
		if (!Character.isLetter(jobTypeID.charAt(0)))
			throw new Exception(jobTypeID + " is not a valid JobType ID.");
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
   public ArrayList<Task> getTasks() {
        return tasks;
    }
   public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
	
   public static JobType getJobTypeByID(String s) {
		for (JobType i : allJobTypes) {
			if (i.getJobTypeID().equals(s))
				return i;
		}
		return null;
   }
    public static ArrayList<JobType> getAllJobTypes() {
        return allJobTypes;
    }
}
