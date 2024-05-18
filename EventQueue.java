import java.util.ArrayList;
public class EventQueue {
	private static double currentTime = 0;
	private static ArrayList<Job> waitingJobs = new ArrayList<>();
	private static ArrayList<Job> activeJobs = new ArrayList<>();
	private static ArrayList<Job> completedJobs = new ArrayList<>();
	private static ArrayList<Station> stations = new ArrayList<>();
	private static ArrayList<Event> events = new ArrayList<>();
	
	public static void addJob(Job j) {
		waitingJobs.add(j);
	}
	public static ArrayList<Job> getCompletedJobs() {
		return completedJobs;
	}

	public static void addEvent(Event event) {
		events.add(event);
	}

	public static void addStation(Station s) {
		stations.add(s);
	}
	public static ArrayList<Station> getStations() {
		return stations;
	}
	
	public static double getCurrentTime() {
		return currentTime;
	}
	
	private static void executeAllJobs() {
		for (Job j : activeJobs) {
			for (Task t : j.getTasks()) {
				// try to find a station that can execute this task.
				if (t.isTaskCompleted() || t.isBeingProcessed())
					continue;
				for (Station s : stations) {
					if (s.canHandleTaskType(t.getTaskType())) {
						s.addTask(t);
						break; // add it to only one station.
					}
				}
			}
		}
	}

	private static void checkFinishedJobs() {
		ArrayList<Job> finishedJobs = new ArrayList<>();
		for (Job j : activeJobs) {
			boolean allTasksCompleted = true;
			for (Task t : j.getTasks()) {
				if (!t.isTaskCompleted()) {
					allTasksCompleted = false;
					break;
				}
			}
			if (allTasksCompleted) {
				finishedJobs.add(j);
				addEvent(new Event("Job " + j.getJobID() + " has been completed.", currentTime));
				j.setActualEndTime(currentTime);
			}
		}
		activeJobs.removeAll(finishedJobs);
		completedJobs.addAll(finishedJobs);
	}

	private static boolean nextEvent() {
		executeAllJobs();
		double earliest = Double.MAX_VALUE;
		Job startingJob = null;
		Station station = null;
		
		// Check all stations for new events.
		for (Station s : stations) {
			Event e = s.nextEvent();
			if (e != null && e.getStartTime() <= earliest) {
				earliest = e.getStartTime();
				station = s;
			}
		}

		// Check all jobs for any jobs that need to start soon.
		for (Job j : waitingJobs) {
			double t = j.getStartTime();
			if (t <= earliest) {
				earliest = t;
				startingJob = j;
				station = null;
			}
		}

		if (station == null && startingJob == null) {
			checkFinishedJobs();
			/* No event has actually happened, no events left. */
			addEvent(new Event("All Tasks and Jobs finished.", currentTime));
			return false;
		}
		
		double old = currentTime;
		currentTime = earliest;
		for (Station s : stations) {
			s.passTime(earliest - old);
		}
		checkFinishedJobs();
		if (startingJob != null) {
			// a job must start now.
			addEvent(new Event("Job " + startingJob.getJobID() + " has started.", earliest));
			waitingJobs.remove(startingJob);
			activeJobs.add(startingJob);
		}

		return true;
	}

	public static void fill() {
		while (nextEvent()) {
		
		}
		for (Event e : events) {
			System.out.println("[Time " + e.getStartTime() + "] " + e.getMessage());
		}
	}
}
	
