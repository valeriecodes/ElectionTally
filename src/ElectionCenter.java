import java.util.HashMap;

public class ElectionCenter {
	private static ElectionCenter instance = null;
	private HashMap<String,Election> elections;
	
	private ElectionCenter(){}
	
	public static ElectionCenter getElectionCenter() {
		if (instance == null) {
			instance = new ElectionCenter();
		}
		else {
			System.err.print("Sorry, an election center instance already exists.\n");
		}
		return instance;
	}

	public void addElection(String electionName, Election myElection){
		elections.put(electionName, myElection);
	}
	
	
}
