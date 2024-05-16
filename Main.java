import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.lang.Integer;
import java.lang.NumberFormatException;
public class Main {
	public static class InvalidSyntaxException extends Exception {
		public InvalidSyntaxException(String message) {
			super(message);
		}
	}
	private static ArrayList<Station> allStations = new ArrayList<>();
	private static ArrayList<JobType> jobTypes = new ArrayList<>();

	private static void readTaskTypes(String wholeStr) throws InvalidSyntaxException {
		// we take the whole file as input, then figure out where the task types are.
		// This probably isn't necessary, but it allows for more flexibility in the input file,
		// and also lets us avoid reading line-by-line (which can cause some awkward situations
		// with regex)
		Pattern subPattern = Pattern.compile("(\\w+)\\s*(\\d+(\\.\\d+)?)?\\s*");
		Pattern wholePattern = Pattern.compile("\\(TASKTYPES\\s+((\\w+)(\\s+\\d+(\\.\\d+)?)?\\s*)+\\)");
		Matcher matcher = wholePattern.matcher(wholeStr);
		
		if (!matcher.find())
			throw new InvalidSyntaxException("Could not find a valid TaskType list in workflow file.");
		
		String sub = wholeStr.substring(matcher.start() + 10, matcher.end());
		matcher = subPattern.matcher(sub);

		while (matcher.find()) {
			if (!Character.isLetter(matcher.group(1).charAt(0))) {
				// invalid TaskType name.
				System.out.println("'" + matcher.group(1) + "' is not a valid TaskType ID.");
				continue;
			}

			if (matcher.group(2) == null) {
				// no default size.
				TaskType.addNewTaskType(new TaskType(matcher.group(1)));
				continue;
			}

			// default size exists: parse it.
			double parsed = 0.0;
			try {
				parsed = Double.parseDouble(matcher.group(2));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			TaskType.addNewTaskType(new TaskType(matcher.group(1), parsed));
		}
	}
	
	public static void readJobTypes(String wholeStr) throws InvalidSyntaxException {
		// Task type and size regex. Task Type ID followed optionally by a size.
		String tsRegex = "\\s*(\\w+)(\\s+(\\d+(\\.\\d+)?))?\\s*";
		Pattern tsPattern = Pattern.compile(tsRegex);

		// Description of a single jobType.
		String jobtypeRegex = "\\s*\\((\\w+)((" + tsRegex + ")+)\\)\\s*";
		
		Pattern subPattern = Pattern.compile(jobtypeRegex);
		Pattern wholePattern = Pattern.compile("\\(JOBTYPES(" + jobtypeRegex + ")+\\)");
		
		Matcher wm = wholePattern.matcher(wholeStr);
		if (!wm.find())
			throw new InvalidSyntaxException("Could not find a valid JobType list in workflow file.");

		String jobtypes = wholeStr.substring(wm.start() + 9, wm.end());
		Matcher sm = subPattern.matcher(jobtypes);
		while (sm.find()) {
			String jobtypeName = sm.group(1);
			String tasks = sm.group(2);
			Matcher tsm = tsPattern.matcher(tasks);
			
			if (!Character.isLetter(jobtypeName.charAt(0)))
				throw new InvalidSyntaxException("'" + jobtypeName + "' is not a valid job type name.");
			JobType jt = new JobType(jobtypeName);
			while (tsm.find()) {
				try {
					if (tsm.group(3) == null) {
						// task size wasn't specified.
						jt.addTask(new Task(tsm.group(1), TaskType.getTaskTypeByID(tsm.group(1))));
					} else {
						double parsed = Double.parseDouble(tsm.group(3));
						jt.addTask(new Task(tsm.group(1), TaskType.getTaskTypeByID(tsm.group(1)), parsed));
					}
				} catch (Task.MissingSizeException e) {
					System.out.println("Error for task " + tsm.group(1) + ": " + e.getMessage());
				}
			}
			// TODO: reorganize all jobtypes list, and also check if a TaskType or JobType has
			// been defined before. throw if yes.
			jobTypes.add(jt);
		}
	}

	public static void readStations(String wholeText) throws InvalidSyntaxException {
		// It's easier to define the regexes in terms of each other.
		// much easier to debug, too.
		String boolRegex = "\\s*([YN])"; // Y or N
		String numRegex = "\\s*(\\d+(\\.\\d+)?)"; // a (possibly floating point) number.
		String wordRegex = "\\s*(\\w+)";
		String taskRegex = wordRegex + numRegex + numRegex + "?"; 
		String stationRegex = "\\(" + wordRegex + numRegex + "?" + boolRegex + boolRegex + "((" + taskRegex + ")+)" + "\\)";

		String finalRegex = "\\(STATIONS((\\s*" + stationRegex + ")+)\\s*\\)";
		
		Matcher fm = Pattern.compile(finalRegex).matcher(wholeText);

		if (!fm.find())
			throw new InvalidSyntaxException("no valid Station list found in workflow file.");
		
		Matcher sm = Pattern.compile(stationRegex).matcher(fm.group(1));
		// loop over each station
		while (sm.find()) {
			Matcher tm = Pattern.compile(taskRegex).matcher(sm.group(6));
			ArrayList<ProcessingSpeed> pss = new ArrayList<>();
			
			// Add all TaskTypes and their processing speeds into an ArrayList
			while (tm.find()) {
				TaskType tt = TaskType.getTaskTypeByID(tm.group(1));
				double s = Double.parseDouble(tm.group(2));
				double deviation = 0.0;
				if (tm.group(4) != null) {
					deviation = Double.parseDouble(tm.group(4));
				}
				pss.add(new ProcessingSpeed(tt, s, deviation));
			}
			
			// Pass all the necessary info to Station
			int cap = Integer.parseInt(sm.group(2));
			Station s = new Station(sm.group(1), cap, pss, sm.group(4).equals("Y"), sm.group(5).equals("Y"));
			allStations.add(s);
		}
	}

	public static void readWorkFlowFile(String fname) throws InvalidSyntaxException {
		Scanner sc = null;
		try {
			sc = new Scanner(Paths.get(fname));
		} catch (Exception e) {
			System.out.println("Error: Failed to open workflow file '" + fname + "'");
			return;
		}
		String wholeText = "";
		while (sc.hasNextLine()) {
			wholeText += sc.nextLine() + "\n";
		}
		
		readTaskTypes(wholeText);
		readJobTypes(wholeText);
		readStations(wholeText);
	}

	public static ArrayList<Job> jobs;
	public static void readJobFile(String fname) throws Exception {
		jobs = new ArrayList<>();
		Scanner sc = new Scanner(Paths.get(fname));
		Pattern p = Pattern.compile("(\\w+)\\s+(\\w+)\\s+(\\d+)\\s+(\\d+)");
		
		int line = 0;
		boolean hadError = false;
		while (sc.hasNextLine()) {
			line++;
			Matcher m = p.matcher(sc.nextLine());
			if (!m.matches()) {
				hadError = true;
				System.out.println("Line #" + line + " in " + fname + " is an invalid Job declaration.");
				continue; // still continue though, so we can report all errors at once.
			}
			
			jobs.add(new Job(m.group(1), new JobType(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))));
		}
		if (hadError)
			throw new Exception("Stopped execution because Job file contains error(s).");
	}

	
	private static ArrayList<Station> findNewStations(TaskType taskType, ArrayList<Station> allStations) {
		ArrayList<Station> suitableStations = new ArrayList<>();
    	for (Station station : allStations) {
			if (station.canHandleTaskType(taskType)) {
				suitableStations.add(station);
        	}
    	}
		return suitableStations;
	}

	private static Station selectStation(ArrayList<Station> suitableStations) {
		// Random selection
		int randomIndex = (int) (Math.random() * suitableStations.size());
    	return suitableStations.get(randomIndex);
	}

	private static void proceedAfterTaskCompletion(Task task, ArrayList<Station> allStations) {
		// TODO find next task
	}

	private static void manageTaskCompletionEvent(Task completedTask, ArrayList<Station> allStations) {
		proceedAfterTaskCompletion(completedTask, allStations);
	}

	private static void executeTasksInAllStations() {
		for (Station station : allStations) {
			station.executeTasks();
		}
	}

	private static void manageTaskCompletionEvents(ArrayList<Station> allStations) {
		for (Station station : allStations) {
			ArrayList<Task> completedTasks = station.getCompletedTasks();
			for (Task completedTask : completedTasks) {
				proceedAfterTaskCompletion(completedTask, allStations);
			}
		}
	}

	public static void printInfo() {
		for (TaskType i : TaskType.getAllTaskTypes()) {
			System.out.printf("TaskType '%s' with default size %f%n", i.getTaskTypeName(), i.getDefaultTaskSize());
		}
		for (JobType i : jobTypes) {
			System.out.printf("JobType '%s' with tasks:%n", i.getJobTypeID());
			for (Task j : i.getTasks()) {
				System.out.printf("\tTask '%s' with size %f%n", j.getTaskID(), j.getTaskSize());
			}
		}
	}

	public static void main(String[] args) throws Exception{
		if (args.length < 2) {
			System.out.println("Usage:\n\tjava Main.java [workflow file path] [job file path]");
			return;
		}
		readWorkFlowFile(args[0]);
		printInfo();

		readJobFile(args[1]);
		for (Job i : jobs) {
			System.out.println(i);
		}

		while (true) { //This will change
			executeTasksInAllStations();
		}
	}
}
