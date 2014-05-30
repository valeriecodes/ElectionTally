public class MyIterator<E>{
	private E [] contents;
	private int currentPosition;
	
	public MyIterator(E [] myContents){
		contents = myContents;
		currentPosition = 0;
	}
	public boolean hasNext(){
		return currentPosition < contents.length;
	}
	
	public E next(){
		E toReturn = contents[currentPosition];
		currentPosition++;
		return toReturn;
	}
	
	public void reset(){
		currentPosition = 0;
	}
}
