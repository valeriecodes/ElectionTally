import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class BallotMaker {
	HashMap<String, ArrayList<String>> options;
	ArrayList<String> states;

	public BallotMaker(String votesFile, String statesFile) {
		options = new HashMap<String, ArrayList<String>>();
		states = new ArrayList<String>();
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
				System.out.println(line);
				ArrayList<String> weightedCandidates = new ArrayList<String>();
				while ((line = fileStream.readLine()) != null && !line.equals("")) {
					String[] info = line.split(" ");
					System.out.println("info[0]: " + info[0]);
					System.out.println("info[1]: " + info[1]);
					int occurences = Integer.valueOf(info[1]);
					for (int i = 0; i < occurences; i++) {
						weightedCandidates.add(info[0]);
					}
				}
				options.put(electionName, weightedCandidates);
			}
			fileStream.close();
			fileStream = new BufferedReader(new FileReader(statesFile));
			while ((line = fileStream.readLine()) != null) {
				states.add(line);
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

	public void castVotes() throws FileNotFoundException {
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
				Ballot myBallot = new Ballot(candidate, election, state);
				PrintWriter fileWriter = new PrintWriter("data/inbox/" + state + "-" + j + ".csv");
				fileWriter.print(myBallot.toCSV());
				fileWriter.close();
			}
		}
	}

	public void routeVotes() throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62060");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("file:data/inbox?noop=false").log("RETREIVED ${file:name}")
						.unmarshal().csv().split(body()).choice()
						.when(header("CamelFileName").startsWith("CA")).to("jms:queue:BALLOTS_CA")
						.when(header("CamelFileName").startsWith("OR")).to("jms:queue:BALLOTS_OR")
						.when(header("CamelFileName").startsWith("WA")).to("jms:queue:BALLOTS_WA")
						.when(header("CamelFileName").startsWith("NV")).to("jms:queue:BALLOTS_NV")
						.when(header("CamelFileName").startsWith("ID")).to("jms:queue:BALLOTS_ID")
						.when(header("CamelFileName").startsWith("AZ")).to("jms:queue:BALLOTS_AZ")
						.when(header("CamelFileName").startsWith("UT")).to("jms:queue:BALLOTS_UT")
						.when(header("CamelFileName").startsWith("ND")).to("jms:queue:BALLOTS_ND")
						.when(header("CamelFileName").startsWith("SD")).to("jms:queue:BALLOTS_SD")
						.when(header("CamelFileName").startsWith("CO")).to("jms:queue:BALLOTS_CO")
						.when(header("CamelFileName").startsWith("NM")).to("jms:queue:BALLOTS_NM")
						.when(header("CamelFileName").startsWith("MT")).to("jms:queue:BALLOTS_MT")
						.when(header("CamelFileName").startsWith("WY")).to("jms:queue:BALLOTS_WY")
						.when(header("CamelFileName").startsWith("NB")).to("jms:queue:BALLOTS_NB")
						.when(header("CamelFileName").startsWith("KS")).to("jms:queue:BALLOTS_KS")
						.when(header("CamelFileName").startsWith("OK")).to("jms:queue:BALLOTS_OK")
						.when(header("CamelFileName").startsWith("TX")).to("jms:queue:BALLOTS_TX")
						.when(header("CamelFileName").startsWith("LA")).to("jms:queue:BALLOTS_LA")
						.when(header("CamelFileName").startsWith("AK")).to("jms:queue:BALLOTS_AK")
						.when(header("CamelFileName").startsWith("MO")).to("jms:queue:BALLOTS_MO")
						.when(header("CamelFileName").startsWith("IA")).to("jms:queue:BALLOTS_IA")
						.when(header("CamelFileName").startsWith("MN")).to("jms:queue:BALLOTS_MN")
						.when(header("CamelFileName").startsWith("MS")).to("jms:queue:BALLOTS_MS")
						.when(header("CamelFileName").startsWith("TN")).to("jms:queue:BALLOTS_TN")
						.when(header("CamelFileName").startsWith("KY")).to("jms:queue:BALLOTS_KY")
						.when(header("CamelFileName").startsWith("IL")).to("jms:queue:BALLOTS_IL")
						.when(header("CamelFileName").startsWith("IN")).to("jms:queue:BALLOTS_IN")
						.when(header("CamelFileName").startsWith("WI")).to("jms:queue:BALLOTS_WI")
						.when(header("CamelFileName").startsWith("MI")).to("jms:queue:BALLOTS_MI")
						.when(header("CamelFileName").startsWith("OH")).to("jms:queue:BALLOTS_OH")
						.when(header("CamelFileName").startsWith("AL")).to("jms:queue:BALLOTS_AL")
						.when(header("CamelFileName").startsWith("WV")).to("jms:queue:BALLOTS_WV")
						.when(header("CamelFileName").startsWith("ME")).to("jms:queue:BALLOTS_ME")
						.when(header("CamelFileName").startsWith("NH")).to("jms:queue:BALLOTS_NH")
						.when(header("CamelFileName").startsWith("VT")).to("jms:queue:BALLOTS_VT")
						.when(header("CamelFileName").startsWith("NY")).to("jms:queue:BALLOTS_NY")
						.when(header("CamelFileName").startsWith("MA")).to("jms:queue:BALLOTS_MA")
						.when(header("CamelFileName").startsWith("RI")).to("jms:queue:BALLOTS_RI")
						.when(header("CamelFileName").startsWith("CT")).to("jms:queue:BALLOTS_CT")
						.when(header("CamelFileName").startsWith("PA")).to("jms:queue:BALLOTS_PA")
						.when(header("CamelFileName").startsWith("NJ")).to("jms:queue:BALLOTS_NJ")
						.when(header("CamelFileName").startsWith("DE")).to("jms:queue:BALLOTS_DE")
						.when(header("CamelFileName").startsWith("MD")).to("jms:queue:BALLOTS_MD")
						.when(header("CamelFileName").startsWith("DC")).to("jms:queue:BALLOTS_DC")
						.when(header("CamelFileName").startsWith("VA")).to("jms:queue:BALLOTS_VA")
						.when(header("CamelFileName").startsWith("NC")).to("jms:queue:BALLOTS_NC")
						.when(header("CamelFileName").startsWith("SC")).to("jms:queue:BALLOTS_SC")
						.when(header("CamelFileName").startsWith("GA")).to("jms:queue:BALLOTS_GA")
						.when(header("CamelFileName").startsWith("FL")).to("jms:queue:BALLOTS_FL")
						.when(header("CamelFileName").startsWith("AK")).to("jms:queue:BALLOTS_AK")
						.when(header("CamelFileName").startsWith("HI")).to("jms:queue:BALLOTS_HI")
						.otherwise().to("jms:queue:INVALID_BALLOTS");
			}
		});

		context.start();
		Thread.sleep(10000);

		context.stop();
	}
}
