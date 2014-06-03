import java.util.HashMap;
import java.util.Iterator;

public class ElectoralTally extends Tally{
	private int totalVotes;
	private int threshold;
	public ElectoralTally(HashMap<String, Integer> myCounts, int ItemCount){
		super(myCounts, ItemCount);
		totalVotes = 0;
		Iterator<Integer> votesIter = this.totalsIterator();
		while(votesIter.hasNext()){
			totalVotes += votesIter.next();
		}
		threshold = 1 + totalVotes/2;
	}
	
	public int getTotalVotes(){
		return totalVotes;
	}
	
	public int getThreshold(){
		return threshold;
	}
	
	public Iterator<String> statesIterator(){
		return this.keysIterator();
	}
}
