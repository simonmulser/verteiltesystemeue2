import java.util.LinkedList;


public class BidOperation extends Operation {

	public BidOperation(ClientBind clientBind) {
		super(clientBind);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		if(clientBind.getUser() == null)
			throw new CommunicationException("You are not logged in.");

		if(inputs.size() != 3)
			throw new CommunicationException("usage !bid <auction-id> <amount>");

		double amount;
		int id;
		try{
			id = Integer.parseInt(inputs.get(1));
			amount = Double.parseDouble(inputs.get(2));
		}catch(NumberFormatException e){
			throw new CommunicationException("use number");

		}

		Auction auction = AuctionPool.getInstance().getAuction(id);
		
		if(auction != null){
			boolean success = AuctionPool.getInstance().bid(auction, amount, clientBind.getUser());
			if(success)
				clientBind.sendTcp("You successfully bid with " + amount + " on '" + auction.getName() + "'.");
			else
				clientBind.sendTcp("You unsuccessfully bid with " + amount + " on '" + auction.getName() + "'. Current higest bid is " + auction.getHighestBid() + ".");				
		}
		else
			throw new CommunicationException("usage !bid <auction-id> <amount>");

	}

}
