import java.util.Iterator;
import java.util.Map;

public class PopularVoteEngine implements WinnerStrategy {
	CandidateTally totals;
	TallySet tallies;
	Election election;
	public PopularVoteEngine(Election myElection){
		election = myElection;
		totals = null;
	}
	
	public String pickWinner(){
		if (totals == null){
			this.voteBreakdown();
		}
		String winner = null;
		int winnerVoteCount = 0;
		Iterator<Map.Entry<String, Integer>> iter = totals.iterator();
		while(iter.hasNext()){
			Map.Entry<String, Integer> candidateInfo = iter.next();
			String candidate = candidateInfo.getKey();
			int voteCount = candidateInfo.getValue();
			if(voteCount > winnerVoteCount){
				winner = candidate;
				winnerVoteCount = voteCount;
			}
		}
		return winner;
	}
	
	public void voteBreakdown(){
		if(totals != null){
			totals.reset();
		} else {
			totals = new CandidateTally();
		}
		Iterator<CandidateTally> talliesIter = election.getTallies().iterator();
		while (talliesIter.hasNext()){
			Tally currentTally = talliesIter.next();
			Iterator<Map.Entry<String, Integer>> voteIter = currentTally.iterator();
			while (voteIter.hasNext()){
				Map.Entry<String, Integer> candidateInfo = voteIter.next();
				String candidate = candidateInfo.getKey();
				int voteCount = candidateInfo.getValue();
				if (totals.candidateExists(candidate)){
					totals.addCandidateVotes(candidate, voteCount);
				} else {
					totals.addCandidate(candidate, voteCount);
				}
			}
		}
	}
	
	public void printResults(){
		String winner = this.pickWinner();
		int totalVotes = 0;
		Iterator<Integer> votesIter = totals.totalsIterator();
		while(votesIter.hasNext()){
			totalVotes += votesIter.next();
		}
		float percent = ((float) totals.lookupCount(winner)/totalVotes) * 100;
		System.out.format("The winner is " + winner + " with %.2f percent of the vote", percent);
		System.out.println();
		Iterator<String> candidatesIter = totals.candidatesIterator();
		while(candidatesIter.hasNext()){
			String candidate = candidatesIter.next();
			if(candidate != winner){
				percent = ((float) totals.lookupCount(candidate)/totalVotes) * 100;
				System.out.format(candidate + " got %.2f percent of the vote", percent);
				System.out.println();
			}
		}
		System.out.println("Total votes cast: " + totalVotes);
	}
}