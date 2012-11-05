import java.util.LinkedList;

public class LoginOperation extends Operation {



	public LoginOperation(ClientBind clientBind) {
		super(clientBind);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException {

		if(inputs.size() != 2)
			throw new CommunicationException("usage !login <name>");

		if(clientBind.getUser() != null)
			clientBind.sendTcp("You are logged in.");
		else{
			User user = AuctionPool.getInstance().login(inputs.get(1), clientBind);
			if(user != null){
				clientBind.setUser(user);
				clientBind.sendTcp("Successfully logged in as " + inputs.get(1) + "!");
				clientBind.sendTcp("!name " + inputs.get(1));
				user.getNotifications();
			}
			else{
				clientBind.sendTcp("Someone else is logged in with " + inputs.get(1) + ".");
			}

		}

	}

}
