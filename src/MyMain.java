public class MyMain {
	public static void main(String[] args) throws Exception {
		BallotMaker myBallotMaker = new BallotMaker("voteBreakdown.txt", "states.txt");
		myBallotMaker.routeVotes();
		myBallotMaker.routeVotes();
	}
}