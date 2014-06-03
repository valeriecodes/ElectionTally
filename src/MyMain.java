public class MyMain {
	public static void main(String[] args) throws Exception {
		Election myElection = new Election("ElectoralVotes.txt");
		System.out.print(myElection.getElectoralVotes().lookupCount("CA"));
	}
}