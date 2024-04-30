import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Integer;
public class Main {
	public static class InvalidSyntaxException extends Exception {
		public InvalidSyntaxException(String message, String fname, int lineNumber) {
			super("[line #" + lineNumber + " in file '" + fname + "'] " + message);
		}
	}
	private static ArrayList<TaskType> taskTypes;
	private static int lineNumber = 1;

	private static void skipWhiteSpace(Scanner sc) {
		while (sc.hasNext("\\s")) {
			if (sc.hasNext("\n")) {
				lineNumber++;
			}
			sc.skip("\\s");
		}
	}
	private static void readTaskTypes(Scanner sc, String fname) throws InvalidSyntaxException {
		skipWhiteSpace(sc);
		taskTypes = new ArrayList<>();
		
		// check for beginning parenthesis
		sc.useDelimiter("[^(]");
		if (!sc.hasNext("[(]")) {
			throw new InvalidSyntaxException("line doesn't start with '('", fname, lineNumber); 
		}
		sc.next("[(]");
		sc.useDelimiter("\\s+");

		// check for the TASKTYPES word
		String word = sc.next("\\w*");
		if (!word.equals("TASKTYPES")) 
			throw new InvalidSyntaxException("expected \"TASKTYPES\", found \"" + word + "\"", fname, lineNumber);
		
		// parse each task type and add it to the global list of tasktypes.
		// TODO: maybe add the global list of task types as a static member of TaskType
		// instead. It doesn't really fit in main.
		for(;;) {
			skipWhiteSpace(sc);
			sc.useDelimiter("[^\\w.]+");
			if (!sc.hasNext("[A-Za-z][A-Za-z_0-9]*")) {
				// next word isn't available. check if we're at the end of the
				// list, otherwise signal exception
				sc.useDelimiter("[^)]");
				boolean has = sc.hasNext("[)]");
				sc.useDelimiter("\\s+");
				if (!has) {
					throw new InvalidSyntaxException("expected ')' "  + sc.nextLine(), fname, lineNumber);
				}
				break;
			}
			String typeName = sc.next("\\w*");
			
			skipWhiteSpace(sc);
			// check for default size
			String defaultSize = "";
			if (sc.hasNext("-?[0-9]+([.][0-9]+)?")) {
				System.out.println();
				defaultSize = sc.next("-?[0-9]+(\\.[0-9]+)?");

				// TODO: change this to use the double directly
				// once TaskType is updated to use double as well.
				double parsed = Double.parseDouble(defaultSize);
				if (parsed < 0) {
					throw new InvalidSyntaxException("TaskType " + typeName + " has negative default size.", fname, lineNumber);
				}
				
				taskTypes.add(new TaskType(typeName, parsed));
			} else {
				taskTypes.add(new TaskType(typeName));
			}
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
		
		// only task types for now, later on JobTypes and Stations will be read here
		// as well.
		readTaskTypes(sc, fname);
	}

	public static void readJobFile(String fname) {
		/* Try and open the file. Print an error message on failure. */
		Scanner sc = null;
		try {
			sc = new Scanner(Paths.get(fname));
		} catch (Exception e) {
			System.out.println("Error: Failed to open job file '" + fname + "'");
			return;
		}

		/* Now we have a real scanner, we can parse the file. 
		 * The file format has no multi-line structures, so we will
		 * parse line-by-line.
		 */
		int lineNumber = 1; // keep track of line number for error messages
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			// JobID, JobTypeID, start and duration
			// all seperated by spaces.
			String[] fields = line.split(" ");
			if (fields.length != 4) {
				System.out.printf("Error in Job file [line %d]: Expected 4 space seperated elements but found %d elements", lineNumber, fields.length);
				lineNumber++;
				continue;
			}
			String jobID = fields[0];
			String jobTypeID = fields[1];
			int startTime = 0;
			int duration = 0;
      
			/* Try to parse the integers, print error messages in case of failure. */
			try {
				startTime = Integer.parseInt(fields[2]);
			} catch (Exception e) {
				System.out.printf("Error in Job file [line %d]: Expected integer for start time, but found '%s'", lineNumber, fields[2]);
				lineNumber++;
				continue;
			}
			try {
				duration = Integer.parseInt(fields[3]);
			} catch (Exception e) {
				System.out.printf("Error in Job file [line %d]: Expected integer for duration, but found '%s'", lineNumber, fields[3]);
			}
      
			//TODO: Uncomment this when the Job class is defined.
			//Job j = new Job(jobID, jobTypeID, startTime, duration);
			// Then add j into an ArrayList outside the loop.
			lineNumber++;
		}
	}
	public static void main(String[] args) throws Exception{
		//TODO: catch exceptions
		readWorkFlowFile("workflow.txt");
		for (TaskType i : taskTypes) {
			System.out.printf("TaskType{ name: %s, defaultSize: %f}%n", i.getTaskTypeName(), i.getDefaultTaskSize());
		}
	}
}
