import java.util.HashMap;

public class ElectoralVoteEngine implements WinnerStrategy{
	int threshold;
	HashMap<String, Integer> electoralVotes;
	HashMap<String, Integer> totals;
	ElectionCenter center;
	
	public ElectoralVoteEngine(ElectionCenter myEC){
		center = myEC;
		electoralVotes = center.getElectoralVotes();
		totals = new HashMap<String, Integer>();
		int stateCount = electoralVotes.size();
		Integer [] voteValues = electoralVotes.values().toArray(new Integer[stateCount]); 
		int totalVotes = 0;
		for (int i = 0; i < stateCount; i++){
			totalVotes += voteValues[i];
		}
		float halfVotes = (float) totalVotes/2;
		if (halfVotes % 1 == 0){
			threshold = (int) halfVotes;
		} else {
			threshold = (int) halfVotes + 1;
		}
	}
	
	public String pickWinner(){
		totals.clear();
		return "WINNER";
	}
}
