import org.hsqldb.lib.Iterator;

public class Tally implements TallyStrategy, WinnerStrategy {
	HashMap<String, int> totals;
	
	public Tally(){
		totals = new Hashmap();
	}
	
	public void tally(String candidate, int voteCount){
		if (totals.containsKey(candidate)){
			int currentTotal = totals.get(candidate);
			totals.put(candidate, currentTotal + voteCount);
		} else {
			totals.put(candidate, voteCount);
		}
	}
	
	public int voteCount(){
		Collection <int> votes = totals.values();
		Iterator votesIter = votes.iterator();
		int total = 0;
		while(votesIter.hasNext()){
			total += votesIter.next();
		}
		return total;
	}
	
	public int votesFor(String candidate){
		return totals.get(candidate);
	}
	
	public String pickWinner(Iterator<String> candidates){
		String currentWinner;
		int currentWinnerVotes = 0;
		while(candidates.hasNext()){
			currentCandidate = candidates.next();
			currentVotes = votesFor(currentCandidate);
			if (currentVotes > currentWinnerVotes){
				currentWinner = currentCandidate;
				currentWinnerVotes = currentVotes;
			}
		}
	}
}
