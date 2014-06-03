import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.commons.lang.StringUtils;

public class MyAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		String newBody = newExchange.getIn().getBody(String.class);
		System.out.println("New body: " + newBody);
		String joinedBody;
		if (oldExchange == null){
			joinedBody = newBody + ":1";
		} else {
			String oldBody = oldExchange.getIn().getBody(String.class);
			System.out.println("Old body: " + oldBody);
			boolean inBody = false;
			String[] candidates;
			if (oldBody.contains(",")){
				 candidates = oldBody.split(",");
			} else {
				candidates = new String[1];
				candidates[0] = oldBody;
			}
			for(int i = 0; i < candidates.length; i ++){
				System.out.println(candidates[i]);
				String[] electionInfo = candidates[i].split(":");
				if(electionInfo[0].equals(newBody)){
					inBody = true;
					int newCount = Integer.parseInt(electionInfo[1]) + 1;
					candidates[i] = electionInfo[0] + ":" + newCount;
				}
			}
			joinedBody = StringUtils.join(candidates, ",");
			if(!inBody){
				joinedBody = joinedBody + "," + newBody + ":1";
			}
		}
		System.out.println(joinedBody);
		newExchange.getIn().setBody(joinedBody);
		return newExchange;
	}

}
