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
	
	public void countVotes(){
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62060");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		ConsumerTemplate consumer = context.createConsumerTemplate();
		while(true){
			String vote = consumer.receiveBody("jms:queue:BALLOTS_" + state, String.class);
			vote.split(",");
		}
	}
}