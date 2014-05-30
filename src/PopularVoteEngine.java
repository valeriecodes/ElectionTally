import java.util.HashMap;


public class PopularVoteEngine implements WinnerStrategy {
	HashMap<String, Integer> totals;
	TallySet tallies;
	ElectionCenter center;
	public PopularVoteEngine(ElectionCenter myEC){
		center = myEC;
		totals = new HashMap<String, Integer>();
	}
	
	public String pickWinner(){
		totals.clear();
		MyIterator<Tally> talliesIter = center.getTallies().iterator();
		String winner = null;
		int winnerVoteCout = 0;
		while(talliesIter.hasNext()){
			Tally currentTally = talliesIter.next();
			//TODO: Finish this. 
		}
		return winner;
	}
}