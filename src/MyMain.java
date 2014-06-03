public class MyMain {
	public static void main(String[] args) throws Exception {
		ElectionCenter center = ElectionCenter.getElectionCenter();
		center.reset();
		center.setupBallotMaker("voteBreakdown.txt");
		center.setElectoralVotes("ElectoralVotes.txt");
		center.castVotes();
		center.countVotes();
		center.distributeVotes();
	}
}