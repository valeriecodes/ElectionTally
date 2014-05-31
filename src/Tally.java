import java.util.HashMap;
import java.util.Map;

abstract class Tally {
	protected HashMap<String, Integer> counts;
	protected int numElements;
	
	public Tally(HashMap<String, Integer> myCounts, int myItemCount){
		counts = myCounts;
		numElements = myItemCount;
	}
	
	public MyIterator<Map.Entry<String, Integer>> iterator(){
		@SuppressWarnings("unchecked")
		Map.Entry<String, Integer> [] contents = (Map.Entry<String, Integer>[]) counts.entrySet().toArray();
		return new MyIterator<Map.Entry<String, Integer>>(contents);
	}
	
	public MyIterator<Integer> totalsIterator(){
		Integer [] items = counts.values()
				.toArray(new Integer[counts.size()]);
		return new MyIterator<Integer>(items);
	}
	
	public MyIterator<String> keysIterator(){
		String [] keys = counts.keySet()
				.toArray(new String[counts.size()]);
		return new MyIterator<String>(keys);
	}
	
	public int lookupCount(String toLookup){
		return counts.get(toLookup);
	}
}
