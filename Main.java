import java.nio.file.Paths;
import java.util.Scanner;
import java.lang.Integer;
public class Main {
	public static void readJobFile(String fname) {
		/* Try and open the file. Print an error message on failure. */
		Scanner sc = null;
		try {
			sc = new Scanner(Paths.get(fname));
		} catch (Exception e) {
			System.out.println("Error: Failed to open file '" + fname + "'");
			return;
		}
		/* Now we have a real scanner, we can parse the file. 
		 * The file format has no multi-line structures, so we will
		 * parse line-by-line.
		 */
		int lineNumber = 0; // keep track of line number for error messages
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
	public static void main(String[] args) {

	}
}
