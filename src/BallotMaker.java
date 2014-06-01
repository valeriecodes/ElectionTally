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
				from("file:data/inbox?noop=true").log("RETREIVED ${file:name}")
						.unmarshal().csv().split(body()).choice()
						.when(body().regex("^CA.*")).to("jms:queue:BALLOTS_CA")
						.when(body().regex("^OR.*")).to("jms:queue:BALLOTS_OR")
						.when(body().regex("^WA.*")).to("jms:queue:BALLOTS_WA")
						.when(body().regex("^NV.*")).to("jms:queue:BALLOTS_NV")
						.when(body().regex("^ID.*")).to("jms:queue:BALLOTS_ID")
						.when(body().regex("^AZ.*")).to("jms:queue:BALLOTS_AZ")
						.when(body().regex("^UT.*")).to("jms:queue:BALLOTS_UT")
						.when(body().regex("^ND.*")).to("jms:queue:BALLOTS_ND")
						.when(body().regex("^SD.*")).to("jms:queue:BALLOTS_SD")
						.when(body().regex("^CO.*")).to("jms:queue:BALLOTS_CO")
						.when(body().regex("^NM.*")).to("jms:queue:BALLOTS_NM")
						.when(body().regex("^MT.*")).to("jms:queue:BALLOTS_MT")
						.when(body().regex("^WY.*")).to("jms:queue:BALLOTS_WY")
						.when(body().regex("^NB.*")).to("jms:queue:BALLOTS_NB")
						.when(body().regex("^KS.*")).to("jms:queue:BALLOTS_KS")
						.when(body().regex("^OK.*")).to("jms:queue:BALLOTS_OK")
						.when(body().regex("^TX.*")).to("jms:queue:BALLOTS_TX")
						.when(body().regex("^LA.*")).to("jms:queue:BALLOTS_LA")
						.when(body().regex("^AK.*")).to("jms:queue:BALLOTS_AK")
						.when(body().regex("^MO.*")).to("jms:queue:BALLOTS_MO")
						.when(body().regex("^IA.*")).to("jms:queue:BALLOTS_IA")
						.when(body().regex("^MN.*")).to("jms:queue:BALLOTS_MN")
						.when(body().regex("^MS.*")).to("jms:queue:BALLOTS_MS")
						.when(body().regex("^TN.*")).to("jms:queue:BALLOTS_TN")
						.when(body().regex("^KY.*")).to("jms:queue:BALLOTS_KY")
						.when(body().regex("^IL.*")).to("jms:queue:BALLOTS_IL")
						.when(body().regex("^IN.*")).to("jms:queue:BALLOTS_IN")
						.when(body().regex("^WI.*")).to("jms:queue:BALLOTS_WI")
						.when(body().regex("^MI.*")).to("jms:queue:BALLOTS_MI")
						.when(body().regex("^OH.*")).to("jms:queue:BALLOTS_OH")
						.when(body().regex("^AL.*")).to("jms:queue:BALLOTS_AL")
						.when(body().regex("^WV.*")).to("jms:queue:BALLOTS_WV")
						.when(body().regex("^ME.*")).to("jms:queue:BALLOTS_ME")
						.when(body().regex("^NH.*")).to("jms:queue:BALLOTS_NH")
						.when(body().regex("^VT.*")).to("jms:queue:BALLOTS_VT")
						.when(body().regex("^NY.*")).to("jms:queue:BALLOTS_NY")
						.when(body().regex("^MA.*")).to("jms:queue:BALLOTS_MA")
						.when(body().regex("^RI.*")).to("jms:queue:BALLOTS_RI")
						.when(body().regex("^CT.*")).to("jms:queue:BALLOTS_CT")
						.when(body().regex("^PA.*")).to("jms:queue:BALLOTS_PA")
						.when(body().regex("^NJ.*")).to("jms:queue:BALLOTS_NJ")
						.when(body().regex("^DE.*")).to("jms:queue:BALLOTS_DE")
						.when(body().regex("^MD.*")).to("jms:queue:BALLOTS_MD")
						.when(body().regex("^DC.*")).to("jms:queue:BALLOTS_DC")
						.when(body().regex("^VA.*")).to("jms:queue:BALLOTS_VA")
						.when(body().regex("^NC.*")).to("jms:queue:BALLOTS_NC")
						.when(body().regex("^SC.*")).to("jms:queue:BALLOTS_SC")
						.when(body().regex("^GA.*")).to("jms:queue:BALLOTS_GA")
						.when(body().regex("^FL.*")).to("jms:queue:BALLOTS_FL")
						.when(body().regex("^AK.*")).to("jms:queue:BALLOTS_AK")
						.when(body().regex("^HI.*")).to("jms:queue:BALLOTS_HI")
						.otherwise().to("jms:queue:INVALID_BALLOTS");
			}
		});

		context.start();
		Thread.sleep(10000);

		context.stop();
	}
}
