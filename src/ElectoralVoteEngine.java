import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ElectoralVoteEngine implements WinnerStrategy{
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
		Iterator<Entry<String, Integer>> iter = totals.iterator();
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
		Iterator<String> statesIter = electoralVotes.statesIterator();
		while (statesIter.hasNext()){
			String currentState = statesIter.next();
			String stateWinner = tallies.winnerOf(currentState);
			int stateElectoralVotes = electoralVotes.lookupCount(currentState);
			if (totals.candidateExists(stateWinner)){
				totals.addCandidateVotes(stateWinner, stateElectoralVotes);
			} else {
				totals.addCandidate(stateWinner, stateElectoralVotes);
			}
		}
		return totals;
	}
	
	public void printResults(){
		String winner = this.pickWinner();
		int votes = totals.lookupCount(winner);
		System.out.format("The winner is " + winner + " with %d electoral votes", votes);
		System.out.println();
		Iterator<String> candidatesIter = totals.candidatesIterator();
		while(candidatesIter.hasNext()){
			String candidate = candidatesIter.next();
			if(candidate != winner){
				votes = totals.lookupCount(candidate);
				System.out.format(candidate + " got %d electoral votes", votes);
				System.out.println();
			}
		}
		System.out.println("Total electoral votes: " + electoralVotes.getTotalVotes());
		System.out.println("Electoral votes needed to win election: " + electoralVotes.getThreshold());
	}
}