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
	private static ArrayList<TaskType> taskTypes;

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
		
		// only task types for now, later on JobTypes and Stations will be read here
		// as well.
		readTaskTypes(wholeText);
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

	public static void main(String[] args) throws Exception{
		if (args.length < 2) {
			System.out.println("Usage:\n\tjava Main.java [workflow file path] [job file path]");
			return;
		}
		readWorkFlowFile(args[0]);
		for (TaskType i : TaskType.getAllTaskTypes()) {
			System.out.printf("TaskType{ name: %s, defaultSize: %f}%n", i.getTaskTypeName(), i.getDefaultTaskSize());
		}
		readJobFile(args[1]);
		for (Job i : jobs) {
			System.out.println(i);
		}
	}
}
