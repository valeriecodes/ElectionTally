public class MyMain {
	public static void main(String[] args) throws Exception {
		BallotMaker myBallotMaker = new BallotMaker("voteBreakdown.txt");
		myBallotMaker.addState("CA");
		myBallotMaker.routeVotes();
		Precinct myPrecinct = new Precinct("CA");
		myPrecinct.countVotes();
	}
}