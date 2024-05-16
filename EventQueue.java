public class EventQueue {
	private static double currentTime = 0;
	private static ArrayList<Job> activeJobs;
	private static ArrayList<Job> waitingJobs;
	private static ArrayList<Station> stations;
	private ArrayList<Event> events = new ArrayList<>();
	
	public void addJob(Job j) {
		waitingJobs.add(j);
	}

	public void addEvent(Event event) {
		events.add(event);
	}
	
	public double getCurrentTime() {
		return currentTime;
	}
	
	private static Event nextEvent() {
		double earliest = Double.MAX_VALUE;
		Job startingJob = null;
		Station station = null;
		Event e = null;
		
		// Check all stations for new events.
		for (Station s : allStations) {
			double t = s.timeUntilNextEvent();
			if (t <= earliest) {
				earliest = t;
				station = s;
			}
		}

		// Check all jobs for any jobs that need to start soon.
		for (Job j : activeJobs) {
			double t = (j.getStartTime() - current);
			if (t <= earliest) {
				earliest = t;
				startingJob = j;
				station = null;
			}
		}
		
		if (station == null && startingJob == null) {
			/* No event has actually happened, no events left. */
			return new Event("All Tasks and Jobs finished.");
		}
		
		if (station != null) {
			// TODO: Pass time in all stations
		} else {
			// a job must start now.
			waitingJobs.remove(startingJob);
			activeJobs.add(startingJob);
		}

		currentTime += earliest;
	}
}
	
