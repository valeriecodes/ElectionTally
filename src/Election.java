import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Election {
	private TallySet stateTallies;
	private ElectoralTally electoralVotes;
	private ElectionCenter center;
	
	public Election(){
		stateTallies = new TallySet();
		center = ElectionCenter.getElectionCenter();
	}
	
	public void setElectoralVotes(String voteFile){
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
				center.addState(info[0]);
				stateTallies.addTally(info[0]);
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
	}
	
	public void addVotes(String state, String candidate, int votes){
		CandidateTally currentTally = stateTallies.tallyFor(state);
		currentTally.addCandidateVotes(candidate, votes);
	}
	
	public TallySet getTallies(){
		return stateTallies;
	}
	
	public ElectoralTally getElectoralVotes(){
		return electoralVotes;
	}
}