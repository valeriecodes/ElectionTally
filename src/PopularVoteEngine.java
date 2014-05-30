import java.util.HashMap;


public class PopularVoteEngine implements WinnerStrategy, TallyStrategy{
	HashMap<String, Integer> totalCounts;
	public PopularVoteEngine(){
		totalCounts = new HashMap();
	}
	public String pickWinner(Iterator tallies){
		totalCounts.clear();
		String winner;
		while(tallies.hasNext()){
			Tally currentTally = tallies.next();
			Iterator current = currentTally.iterator();
		}
	}
	
	public void tally(String candidate, int voteCount) {
		// TODO Auto-generated method stub
		
	}
}
