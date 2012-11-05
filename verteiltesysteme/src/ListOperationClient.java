import java.util.Date;
import java.util.LinkedList;


public class ListOperationClient extends OperationClient {

	public ListOperationClient(Client client) {
		super(client);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {

		if(inputs.size() < 7)
			throw new CommunicationException("usage !list <id> <seller> <date> <highestBid> <bider> <description>");

		String description = Helper.getDescription(inputs, 6);
		
		if(inputs.get(2).equals(client.getName()))
			System.out.println(inputs.get(1) + ". '" + description + "' " + new Date(Long.parseLong(inputs.get(3))) + " " + inputs.get(4) + " " + inputs.get(5));	
		else
			System.out.println(inputs.get(1) + ". '" + description + "' " + inputs.get(2) + " " + new Date(Long.parseLong(inputs.get(3))) + " " + inputs.get(4) + " " + inputs.get(5));	
	}
}
