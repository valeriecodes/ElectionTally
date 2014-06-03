import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ElectionCenter {
	private static ElectionCenter instance = null;
	private HashMap<String,Election> elections;
	private HashMap<String, Precinct> precincts;
	private BallotMaker ballotMaker;
	
	private ElectionCenter(){
		elections = new HashMap<String, Election>();
		precincts = new HashMap<String, Precinct>();
		ballotMaker = null;
	}
	
	public void reset(){
		elections = new HashMap<String, Election>();
		precincts = new HashMap<String, Precinct>();
		ballotMaker = null;
	}
	
	public static ElectionCenter getElectionCenter() {
		if (instance == null) {
			instance = new ElectionCenter();
		}
		return instance;
	}
	
	public void addElection(String electionName){
		if (!elections.containsKey(electionName)){
			elections.put(electionName, new Election());
		}
	}
	
	public void setElectoralVotes(String fileName, String electionName){
		Election election = elections.get(electionName);
		election.setElectoralVotes(fileName);
	}
	
	public void setElectoralVotes(String voteFile){
		Collection<Election> vals = elections.values();
		Iterator<Election> iter = vals.iterator();
		while(iter.hasNext()){
			Election currentElection = iter.next();
			currentElection.setElectoralVotes(voteFile);
		}
	}
	
	public void addState(String state){
		ArrayList<String> ballotStates = ballotMaker.getStates();
		if(!ballotStates.contains(state)){
			ballotMaker.addState(state);
		}
		if(!precincts.containsKey(state)){
			precincts.put(state, new Precinct(state));
		}
	}
	
	public void setupBallotMaker(String voteFile){
		ballotMaker = new BallotMaker(voteFile);
		String[] possibleElections = ballotMaker.getElections();
		for(int i = 0; i < possibleElections.length; i++){
			if(!elections.containsKey(possibleElections[i])){
				this.addElection(possibleElections[i]);
			}
		}
	}
	
	public void castVotes() throws Exception{
		ballotMaker.castVotes();
	}
	
	public void countVotes() throws Exception{
		Collection<Precinct> vals = precincts.values();
		Iterator<Precinct> iter = vals.iterator();
		while(iter.hasNext()){
			Precinct currentPrecinct = iter.next();
			currentPrecinct.countVotes();
		}
	}
	
	public void distributeVotes() throws Exception{
		CamelContext context = new DefaultCamelContext();
		ConsumerTemplate consumer = context.createConsumerTemplate();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62060");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		while(true){
			Exchange msg = consumer.receive("jms:queue:ELECTION_CENTER", 3000);
			if(msg == null){
				break;
			}
			String currentElectionName = msg.getIn().getHeader("Election", String.class);
			String state = msg.getIn().getHeader("State", String.class);
			String content = msg.getIn().getBody(String.class);
			String[] voteCounts = content.split(",");
			Election currentElection = elections.get(currentElectionName);
			for(int i = 0; i < voteCounts.length; i++){
				String [] voteInfo = voteCounts[i].split(":");
				String candidate = voteInfo[0];
				int votes = Integer.parseInt(voteInfo[1]);
				currentElection.addVotes(state, candidate, votes);
			}
		}
	}
	
	public Election getElection(String electionName){
		return elections.get(electionName);
	}
	
	public void summarizeResults(){
		Iterator<Map.Entry<String, Election>> iter = elections.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, Election> currentEntry = iter.next();
			System.out.println("Results of the " + currentEntry.getKey() + " Election");
			Election currentElection = currentEntry.getValue();
			PopularVoteEngine popVotes = new PopularVoteEngine(currentElection);
			ElectoralVoteEngine electVotes = new ElectoralVoteEngine(currentElection);
			System.out.println("Popular vote:");
			popVotes.printResults();
			System.out.println();
			System.out.println("Electoral vote:");
			electVotes.printResults();
			System.out.println();
		}
	}
}