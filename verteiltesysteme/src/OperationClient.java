import java.util.LinkedList;


public abstract class OperationClient {
	
	protected Client client;

	public OperationClient(Client client) {
		this.client = client;
	}

	public abstract void execute(LinkedList<String> inputs) throws CommunicationException;

}
