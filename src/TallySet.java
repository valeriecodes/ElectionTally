import java.util.HashMap;

public class TallySet {
	private HashMap<String, Tally> tallies;
	public TallySet(HashMap<String, Tally> myTallies){
		tallies = myTallies;
	}
	
	public TallySet(){
		tallies = new HashMap<String, Tally>();
	}
	
	public TallySet(int size){
		tallies = new HashMap<String, Tally>(size);
	}
	
	public Tally tallyFor(String stateName){
		return tallies.get(stateName);
	}
	
	public String winnerOf(String stateName){
		return tallyFor(stateName).pickWinner();
	}
	
	public MyIterator<Tally> iterator(){
		return new MyIterator<Tally>(tallies.values()
				.toArray(new Tally [tallies.size()]));
	}
}
