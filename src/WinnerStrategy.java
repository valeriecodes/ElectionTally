public interface WinnerStrategy {
	
	public String pickWinner();
	
	public void voteBreakdown();
	
	public void enumerateResults(String winner);
	
	public void finalSummary();
}