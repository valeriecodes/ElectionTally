import java.util.HashMap;
import java.util.Iterator;

public class TallySet {
	private HashMap<String, CandidateTally> tallies;
	private int tallyCount;
	
	public TallySet(HashMap<String, CandidateTally> myTallies, int myCount){
		tallies = myTallies;
		tallyCount = myCount;
	}
	
	public TallySet(){
		tallies = new HashMap<String, CandidateTally>();
		tallyCount = 0;
	}
	
	public void addTally(String tallyName){
		tallies.put(tallyName, new CandidateTally());
		tallyCount ++;
	}
	
	public int getTallyCount(){
		return tallyCount;
	}
	
	public CandidateTally tallyFor(String stateName){
		return tallies.get(stateName);
	}
	
	public String winnerOf(String stateName){
		return tallyFor(stateName).pickWinner();
	}
	
	public Iterator<CandidateTally> iterator(){
		return tallies.values().iterator();
	}
}