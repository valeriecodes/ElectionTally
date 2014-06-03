import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class BallotMaker {
	HashMap<String, ArrayList<String>> options;
	ArrayList<String> states;
	ElectionCenter center;

	public BallotMaker(String votesFile) {
		options = new HashMap<String, ArrayList<String>>();
		states = new ArrayList<String>();
		center = ElectionCenter.getElectionCenter();
		
		BufferedReader fileStream = null;
		try {
			fileStream = new BufferedReader(new FileReader(votesFile));
		} catch (FileNotFoundException e) {
			System.err.print("Oops, there was a problem the  votes file, make sure it exists.");
			System.exit(1);
		}
		String line;
		try {
			while ((line = fileStream.readLine()) != null) {
				String electionName = line;
				ArrayList<String> weightedCandidates = new ArrayList<String>();
				while ((line = fileStream.readLine()) != null && !line.equals("")) {
					String[] info = line.split(" ");
					int occurences = Integer.valueOf(info[1]);
					for (int i = 0; i < occurences; i++) {
						weightedCandidates.add(info[0]);
					}
				}
				options.put(electionName, weightedCandidates);
			}
			fileStream.close();
		} catch (NumberFormatException e) {
			System.err.print("There was an invalid number in the votes file");
			System.exit(1);
		} catch (IOException e) {
			System.err.print("There was an I/O exception");
			System.exit(1);
		}
	}
	public void addState(String state){
		states.add(state);
	}
	
	public ArrayList<String> getStates(){
		return states;
	}
	
	public void addElection(String electionName){
		options.put(electionName, new ArrayList<String>());
		center.addElection(electionName);
	}
	
	public void castVotes() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ProducerTemplate template = context.createProducerTemplate();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62060");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		Random r = new Random();
		String[] elections = options.keySet().toArray(
				new String[options.size()]);
		System.out.println(states.size());
		for (int i = 0; i < states.size(); i++) {
			String state = states.get(i);
			for (int j = 0; j < 100; j++){
				String election = elections[r.nextInt(elections.length)];
				ArrayList<String> candidates = options.get(election);
				String candidate = candidates.get(r.nextInt(candidates.size()));
				template.sendBodyAndHeader("jms:queue:BALLOTS_" + state, candidate, "Election", election);
			}
		}
	}
	
	public String[] getElections(){
		return options.keySet().toArray(new String [options.size()]);
	}
}
