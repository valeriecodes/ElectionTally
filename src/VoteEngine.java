public class VoteEngine extends PrinterTemplate{
	private WinnerStrategy strategy;
		
	public VoteEngine(WinnerStrategy myStrategy){
		strategy = myStrategy;
	}
	
	public String winner(){
		return this.strategy.pickWinner();
	}

	@Override
	protected void enumerateResults(String winner) {
		this.strategy.enumerateResults(winner);
	}

	@Override
	protected void finalSummary() {
		this.strategy.finalSummary();
	}
}