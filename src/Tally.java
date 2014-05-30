
import java.util.HashMap;
import java.util.Map;

public class Tally implements WinnerStrategy {
	HashMap<String, Integer> totals;
	
	public Tally(){
		totals = new HashMap<String, Integer>();
	}
	
	public void tally(String candidate, int voteCount){
		if (totals.containsKey(candidate)){
			int currentTotal = totals.get(candidate);
			totals.put(candidate, currentTotal + voteCount);
		} else {
			totals.put(candidate, voteCount);
		}
	}
	
	public MyIterator<Map.Entry<String, Integer>> iterator(){
		@SuppressWarnings("unchecked")
		Map.Entry<String, Integer> [] contents = (Map.Entry<String, Integer>[]) totals.entrySet().toArray();
		return new MyIterator<Map.Entry<String, Integer>>(contents);
	}

	public int voteCount(){
		MyIterator<Map.Entry<String, Integer>> votesIter = this.iterator();
		int total = 0;
		while(votesIter.hasNext()){
			Map.Entry<String, Integer> currentCandidate = votesIter.next();
			total += currentCandidate.getValue();
		}
		return total;
	}
	
	public int votesFor(String candidate){
		return totals.get(candidate);
	}
	
	public String pickWinner(){
		String currentWinner =  null;
		int currentWinnerVotes = 0;
		MyIterator<Map.Entry<String, Integer>> candidates = this.iterator(); 
		while(candidates.hasNext()){
			Map.Entry<String, Integer> currentCandidate = candidates.next();
			int currentVotes = votesFor(currentCandidate.getKey());
			if (currentVotes > currentWinnerVotes){
				currentWinner = currentCandidate.getKey();
				currentWinnerVotes = currentVotes;
			}
		}
		return currentWinner;
	}
}
