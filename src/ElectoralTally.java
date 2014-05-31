import java.util.HashMap;

public class ElectoralTally extends Tally{
	private int totalVotes;
	private int threshold;
	public ElectoralTally(HashMap<String, Integer> myCounts, int ItemCount){
		super(myCounts, ItemCount);
		totalVotes = 0;
		MyIterator<Integer> votesIter = this.totalsIterator();
		while(votesIter.hasNext()){
			totalVotes += votesIter.next();
		}
		float halfVotes = (float) totalVotes/2;
		if (halfVotes % 1 == 0){
			threshold = (int) halfVotes;
		} else {
			threshold = (int) halfVotes + 1;
		}
	}
	
	public int getTotalVotes(){
		return totalVotes;
	}
	
	public int getThreshold(){
		return threshold;
	}
	
	public MyIterator<String> statesIterator(){
		return this.keysIterator();
	}
}
