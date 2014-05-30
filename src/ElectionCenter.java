import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ElectionCenter {
	private TallySet stateTallies;
	private HashMap<String, Integer> electoralVotes;
	
	public ElectionCenter(String voteFile){
		electoralVotes = new HashMap<String, Integer>();
		BufferedReader fileStream = null;
		try {
			fileStream = new BufferedReader(new FileReader(voteFile));
		} catch (FileNotFoundException e) {
			System.err.print("Oops, there was a problem finding that file");
			System.exit(1);
		}
		String line;
		try {
			while((line = fileStream.readLine()) != null){
				String[] info = line.split(" ");
				electoralVotes.put(info[0], Integer.valueOf(info[1]));
			}
			fileStream.close();
		} catch (NumberFormatException e) {
			System.err.print("There was an invalid number in the electoral vote count");
			System.exit(1);
		} catch (IOException e) {
			System.err.print("There was an I/O exception");
			System.exit(1);
			
		}
	}
	
	public TallySet getTallies(){
		return stateTallies;
	}
	public HashMap<String, Integer> getElectoralVotes(){
		return electoralVotes;
	}
}
