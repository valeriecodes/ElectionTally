
public class Iterator {
	int currentPosition;
	Object[] items;
	
	public Iterator(Object [] myItems){
		items = myItems;
		currentPosition = 0;
	}
	
	public boolean hasNext(){
		return currentPosition < items.length;
	}
	
	public Object next(){
		Object toReturn = items[currentPosition];
		currentPosition ++;
		return toReturn;
	}
}
