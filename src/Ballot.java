
public class Ballot {
	String candidateName;
	String electionName;
	String stateName;
	public Ballot(String myCandidate, String myElection, String myState){
		candidateName = myCandidate;
		electionName = myElection;
		stateName = myState;
	}
	
	public String toCSV(){
		return stateName + "," + electionName + "," + candidateName; 
	}
}