import java.util.LinkedList;


public class AuctionEndedOperationClient extends OperationClient {

	public AuctionEndedOperationClient(Client client) {
		super(client);
		}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		
		if(inputs.size() < 4)
			throw new CommunicationException("usage !auction-ended <winner> <amount> <description>");
		
		String name ="";
		for(int i = 3; i<inputs.size();i++){
			name += inputs.get(i) + " ";				
		}
		
		name = name.substring(0,name.length() - 1);
		System.out.print("The auction '" + name + "' has ended. ");
				
		if(client.getName().equals(inputs.get(1)))
			 System.out.println("You won with " + inputs.get(2) + ".");
		else
			 System.out.println(inputs.get(1) + " won with " + inputs.get(2) + ".");	
		

	}

}
