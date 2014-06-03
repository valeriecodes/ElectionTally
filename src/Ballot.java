import org.apache.camel.dataformat.bindy.annotation.*;

@CsvRecord(separator = ",", crlf = "UNIX")
public class Ballot {
	@DataField(pos = 1)
	String candidate;
	
	@DataField(pos=2)
	String election;
	
	public Ballot(String myCandidate, String myElection){
		candidate = myCandidate;
		election = myElection;
	}
}
