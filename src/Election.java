import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Election {
	private TallySet stateTallies;
	private ElectoralTally electoralVotes;
	
	public Election(){
		stateTallies = new TallySet();
	}
	
	public Election(String voteFile){
		HashMap<String, Integer> electoralVotesMap = new HashMap<String, Integer>();
		int stateCount = 0;
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
				electoralVotesMap.put(info[0], Integer.valueOf(info[1]));
				stateCount += 1;
			}
			fileStream.close();
		} catch (NumberFormatException e) {
			System.err.print("There was an invalid number in the electoral vote count");
			System.exit(1);
		} catch (IOException e) {
			System.err.print("There was an I/O exception");
			System.exit(1);
		}
		electoralVotes = new ElectoralTally(electoralVotesMap, stateCount);
		stateTallies = new TallySet();
	}
	
	public TallySet getTallies(){
		return stateTallies;
	}
	
	public ElectoralTally getElectoralVotes(){
		return electoralVotes;
	}
}