import java.util.Date;
import java.util.LinkedList;

public class CreateOperation extends Operation {

	private Long time;
	
	public CreateOperation(ClientBind clientBind) {
		super(clientBind);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		if(clientBind.getUser() == null)
			throw new CommunicationException("You have to log in first!");
		
		
		if(inputs.size() < 3)
			throw new CommunicationException("usage !create <date> <name>");
		
		String description = Helper.getDescription(inputs, 2);

		
		try{
			time = Long.parseLong(inputs.get(1));
		}catch(NumberFormatException e){
			throw new CommunicationException("use number");
			
		}
		
		int id = AuctionPool.getInstance().createAuction(time, description, clientBind.getUser());
		
		//int id = AuctionPool.getInstance().createAuction(System.currentTimeMillis() + 50000, description, clientBind.getUser());
		clientBind.sendTcp("An auction '" + description + "' with id " + id + " has been created and will end on " +  new Date(System.currentTimeMillis() + time*1000) +".");

	}

}
