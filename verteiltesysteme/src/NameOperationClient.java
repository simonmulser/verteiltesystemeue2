import java.util.LinkedList;


public class NameOperationClient extends OperationClient {

	public NameOperationClient(Client client) {
		super(client);
		}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		if(inputs.size() != 2)
			throw new CommunicationException("usage !name <name>");
		
		client.setName(inputs.get(1));

	}

}
