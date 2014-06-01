import java.util.Map;
import java.util.Map.Entry;

public class ElectoralVoteEngine implements WinnerStrategy{
	int threshold;
	ElectoralTally electoralVotes;
	CandidateTally totals;
	Election election;
	
	public ElectoralVoteEngine(Election myElection){
		election = myElection;
		electoralVotes = election.getElectoralVotes();
		totals = null;
	}
	
	public String pickWinner(){
		if (totals == null){
			this.voteBreakdown();
		}
		String winner = null;
		MyIterator<Entry<String, Integer>> iter = totals.iterator();
		boolean toContinue = iter.hasNext();
		while(toContinue){
			Map.Entry<String, Integer> candidateInfo = iter.next();
			if (candidateInfo.getValue() >= electoralVotes.getThreshold()){
				winner = candidateInfo.getKey();
				toContinue = false;
			} else {
				toContinue = iter.hasNext();
			}
		}
		return winner;
	}
	
	public CandidateTally voteBreakdown(){
		if(totals != null){
			totals.reset();
		} else {
			totals = new CandidateTally();
		}
		TallySet tallies = election.getTallies();
		ElectoralTally electoralVotes = election.getElectoralVotes();
		MyIterator<String> statesIter = electoralVotes.statesIterator();
		while (statesIter.hasNext()){
			String currentState = statesIter.next();
			String stateWinner = tallies.winnerOf(currentState);
			if (totals.candidateExists(stateWinner)){
				int stateElectoralVotes = electoralVotes.lookupCount(currentState);
				totals.addCandidateVotes(stateWinner, stateElectoralVotes);
			}
		}
		return totals;
	}
}