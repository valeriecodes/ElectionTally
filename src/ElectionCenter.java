import java.util.HashMap;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ElectionCenter {
	private static ElectionCenter instance = null;
	private HashMap<String,Election> elections;
	private BallotMaker ballotMaker;
	
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
	
	public void castVotes(String voteFile){
		ballotMaker = new BallotMaker(voteFile);
		String[] possibleElections = ballotMaker.getElections();
		for(int i = 0; i < possibleElections.length; i++){
			if(!elections.containsKey(possibleElections[i])){
				this.addElection(possibleElections[i], new Election());
			}
		}
	}
	
	public void distributeVotes() throws Exception{
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62060");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {
			public void configure () throws Exception{
				from("jms:queue:ELECTION_CENTER")
					.to("jms:queue:" + header("Election"));
			}
		});
		
		context.start();
		Thread.sleep(1000);

		context.stop();
	}

	public void addElection(String electionName, Election myElection){
		elections.put(electionName, myElection);
	}
}