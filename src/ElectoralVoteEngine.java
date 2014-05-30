import java.util.HashMap;

public class ElectoralVoteEngine implements WinnerStrategy{
	int threshold;
	HashMap<String, Integer> electoralVotes;
	public ElectoralVoteEngine(HashMap<String, Integer> myElectoralVotes){
		electoralVotes = myElectoralVotes;
	}
	public String pickWinner(){
		return "WINNER";
	}
}
