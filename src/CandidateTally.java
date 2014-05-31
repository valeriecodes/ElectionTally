import java.util.HashMap;
import java.util.Map;

public class CandidateTally extends Tally implements WinnerStrategy{
	public CandidateTally(){
		super(new HashMap<String, Integer>(), 0);
	}
	
	public CandidateTally(HashMap<String, Integer> myCounts, int itemCount){
		super(myCounts, itemCount);
	}
	
	public boolean candidateExists(String candidate){
		return counts.containsKey(candidate);
	}
	
	public void addCandidate(String candidate, int voteCount){
		counts.put(candidate, voteCount);
		numElements ++;
	}
	
	public void addCandidateVotes(String candidate, int voteCount){
		int previousCount = counts.get(candidate);
		counts.put(candidate, previousCount + voteCount);
	}
	
	public MyIterator<String> candidatesIterator(){
		return super.keysIterator();
	}
	
	public void reset(){
		counts.clear();
	}
	
	public String pickWinner(){
		String currentWinner =  null;
		int currentWinnerVotes = 0;
		MyIterator<Map.Entry<String, Integer>> iter = this.iterator(); 
		while(iter.hasNext()){
			Map.Entry<String, Integer> currentCandidate = iter.next();
			int currentVotes = currentCandidate.getValue();
			if (currentVotes > currentWinnerVotes){
				currentWinner = currentCandidate.getKey();
				currentWinnerVotes = currentVotes;
			}
		}
		return currentWinner;
	}
	
	public CandidateTally voteBreakdown(){
		return this;
	}
}
