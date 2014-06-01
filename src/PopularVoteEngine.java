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
		MyIterator<Map.Entry<String, Integer>> iter = totals.iterator();
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
	
	public CandidateTally voteBreakdown(){
		if(totals != null){
			totals.reset();
		} else {
			totals = new CandidateTally();
		}
		MyIterator<Tally> talliesIter = election.getTallies().iterator();
		while (talliesIter.hasNext()){
			Tally currentTally = talliesIter.next();
			MyIterator<Map.Entry<String, Integer>> voteIter = currentTally.iterator();
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
		return totals;
	}
}