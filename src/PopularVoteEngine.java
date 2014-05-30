import java.util.HashMap;


public class PopularVoteEngine implements WinnerStrategy {
	HashMap<String, Integer> totalCounts;
	TallySet tallies;
	public PopularVoteEngine(){
		totalCounts = new HashMap<String, Integer>();
	}
	
	public String pickWinner(){
		totalCounts.clear();
		return "WINNER";
		//while(talliesIter.hasNext()){
		//	Tally currentTally = (Tally) tallies.next();
		//	MyIterator current = currentTally.iterator();
		//}
	}
}