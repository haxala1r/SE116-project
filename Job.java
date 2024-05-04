public class Job{
  private String jobID;
  private JobType jobType;
  private int startTime;
  private int duration;
  public Job(String jobID, JobType jobType,int startTime,int duration){
       this.jobID = jobID;
       this.jobType = jobType;
       this.startTime = startTime;
       this.duration = duration;
  }
   public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobType() {
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
  public String toString() {
        return "Job{" +
                "jobID='" + jobID + '\'' +
                ", jobTypeID='" + jobTypeID + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
