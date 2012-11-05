import java.util.LinkedList;


public class NewBidOperationClient extends OperationClient {
	
	public NewBidOperationClient(){
		super(null);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		if(inputs.size() < 2)
			throw new CommunicationException("!new-bid <description>");
		
		String description = Helper.getDescription(inputs, 1);

		System.out.println("You have been overbid on '" + description +"'.");
	}

}
