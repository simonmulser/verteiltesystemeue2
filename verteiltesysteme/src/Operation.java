import java.util.LinkedList;


public abstract class Operation {

	protected ClientBind clientBind;

	public Operation(ClientBind clientBind) {
		this.clientBind = clientBind;
	}

	public abstract void execute(LinkedList<String> input) throws CommunicationException;
}