import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class ClientInTcp extends NetworkInTcp{

	//private Client client;
	private HashMap<String, OperationClient> messages;
	private LinkedList<String> inputs;

	public ClientInTcp(Socket acceptTcp, Client client) {
		super(acceptTcp, client);
		//this.client = client;

		messages = new HashMap<String, OperationClient>();
		messages.put("!list", new ListOperationClient(client));
		messages.put("!name", new NameOperationClient(client));

	}

	protected void process(String input){

		inputs = Helper.spaceTokenizer(input);

		OperationClient message = messages.get(inputs.get(0));

		if(message != null){
			try {
				message.execute(inputs);
			} catch (CommunicationException e) {
				System.out.println(e.getMessage());
			}
		}
		else
			System.out.println(input);
	}


}
