import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

abstract class Tally {
	protected HashMap<String, Integer> counts;
	protected int numElements;
	
	public Tally(HashMap<String, Integer> myCounts, int myItemCount){
		counts = myCounts;
		numElements = myItemCount;
	}
	
	public Iterator<Map.Entry<String, Integer>> iterator(){
		return counts.entrySet().iterator();
	}
	
	public Iterator<Integer> totalsIterator(){
		return counts.values().iterator();
	}
	
	public Iterator<String> keysIterator(){
		return counts.keySet().iterator();
	}
	
	public int lookupCount(String toLookup){
		return counts.get(toLookup);
	}
}
