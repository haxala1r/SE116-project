import java.util.ArrayList;
public class Job{
  private String jobID;
  private JobType jobType;
  private ArrayList<Task> tasks;
  private int startTime;
  private int duration;
  public Job(String jobID, JobType jobType,int startTime,int duration){
       this.jobID = jobID;
       this.jobType = jobType;
       this.startTime = startTime;
       this.duration = duration;
	   this.tasks = new ArrayList<>();
		
	   // We need to clone each Task because multiple jobs may instantiate from
	   // the same JobType, and thus share the same tasks. This would mean if
	   // that task completes in one Job, the other will also see it as complete.
	   // This is not wanted.
	   for (Task t : jobType.getTasks()) {
			this.tasks.add(new Task(t));
	   }
  }
   public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType( JobType jobType) {
        this.jobType = jobType;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

	public ArrayList<Task> getTasks() {
		return tasks;
	}
	public boolean isComplete() {
		for (Task t : tasks) {
			if (!t.isTaskCompleted())
				return false;
		}
		return true;
	}

	public String toString() {
        return "Job{" +
                "jobID='" + jobID + '\'' +
                ", jobTypeID='" + jobType.getJobTypeID() + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
