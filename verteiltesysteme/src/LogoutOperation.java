import java.util.LinkedList;


public class LogoutOperation extends Operation {

	public LogoutOperation(ClientBind clientBind) {
		super(clientBind);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {
		if(clientBind.getUser() == null)
			throw new CommunicationException("You have to log in first!");

		if(inputs.size() != 1)
			throw new CommunicationException("usage !logout");
		
		User user = clientBind.getUser();
		
		user.setBind(null);
		
		clientBind.sendTcp("Successfully logged out as " + clientBind.getUser().getName() + "!");
		clientBind.setUser(null);
	}

}
