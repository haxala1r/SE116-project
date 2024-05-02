public class Job{
  String jobID;
  String jobTypeID;
  int startTime;
  int duration;
  public Job(String jobID,String jobTypeID,int startTime,int duration){
       this.jobID = jobID;
       this.jobTypeID = jobTypeID;
       this.startTime = startTime;
       this.duration = duration;
  }
   public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobTypeID() {
        return jobTypeID;
    }

    public void setJobTypeID(String jobTypeID) {
        this.jobTypeID = jobTypeID;
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
