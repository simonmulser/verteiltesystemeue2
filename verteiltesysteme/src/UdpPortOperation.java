import java.util.LinkedList;

public class UdpPortOperation extends Operation {
	

	public UdpPortOperation(ClientBind clientbind) {
		super(clientbind);
	}

	@Override
	public void execute(LinkedList<String> inputs) throws CommunicationException{
		
	if(inputs.size() != 2)
		throw new CommunicationException("usage !udpPort <port>");
	
	try{
		clientBind.setUdpPort(Integer.parseInt(inputs.get(1)));
	}catch(NumberFormatException e){
		
		throw new CommunicationException("use number");
		
	}
	}

}
