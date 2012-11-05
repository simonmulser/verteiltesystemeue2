import java.util.LinkedList;

public class ListOperation extends Operation {

	public ListOperation(ClientBind clientBind) {
		super(clientBind);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		
		if(inputs.size() != 1)
			throw new CommunicationException("usage !list");
		
		clientBind.sendTcp(AuctionPool.getInstance().getList());

	}

}
