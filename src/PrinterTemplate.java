abstract class PrinterTemplate {
	
	public void print(String winner){
		System.out.print("The winner is " + winner);
		enumerateResults(winner);
		finalSummary();
	}
	
	abstract protected void enumerateResults(String winner);
	abstract protected void finalSummary();
}
