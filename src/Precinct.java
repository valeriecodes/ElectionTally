import java.util.HashMap;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class Precinct {
	String state;
	HashMap<String, CandidateTally> results;
	public Precinct(String myState){
		state = myState;
	}
	
	public void countVotes() throws Exception{
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62060");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {
			public void configure () throws Exception{
				from("jms:queue:BALLOTS_" + state)
					.log("Counting ballot")
					.aggregate(header("Election"), new MyAggregationStrategy())
						.completionSize(20)
					.completionTimeout(3000)
					.to("jms:queue:ELECTION_CENTER");
			}
		});
	}
}