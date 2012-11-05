import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;


public class ClientBind extends NetworkInTcp {

	private DatagramSocket socketUdp;
	private DatagramPacket packet;
	private int udpPort;
	private User user = null;
	private PrintWriter out;
	private Operation operation;
	private HashMap<String, Operation> operations;
	private LinkedList<String> inputs;

	public ClientBind(Socket accept) {
		super(accept, null);
		
		try {
			out = new PrintWriter(accept.getOutputStream(), true);

		} catch (IOException e) {
			shutdown();
			e.printStackTrace();
		}

		try {
			socketUdp = new DatagramSocket();
		} catch (SocketException e) {
			shutdown();
			System.out.println("error udp connection");
			e.printStackTrace();
		}

		inputs = new LinkedList<String>();
		
		operations = new HashMap<String, Operation>();
		operations.put("!udpPort", new UdpPortOperation(this));
		operations.put("!login", new LoginOperation(this));
		operations.put("!list", new ListOperation(this));
		operations.put("!create", new CreateOperation(this));
		operations.put("!logout", new LogoutOperation(this));
		operations.put("!bid", new BidOperation(this));
	}

	protected void process(String input) {
		inputs =  Helper.spaceTokenizer(input);
		
		operation = operations.get(inputs.get(0));

		if(operation != null){
			try {
					operation.execute(inputs);
			} catch (CommunicationException e) {
				sendTcp(e.getMessage());
			}
		}
		else 
			sendTcp("unsupported command");
	}

	public void sendTcp(String message) {
		out.println(message);
	}

	public void sendUdp(String message) {
		packet = new DatagramPacket(message.getBytes(), message.getBytes().length, socketTcp.getInetAddress(), udpPort);
		try {
			socketUdp.send(packet);
		} catch (IOException e) {
			System.out.println("error sending packet");
			e.printStackTrace();
		}

	}

	public void shutdown() {
		try {
			if(socketTcp != null)
				socketTcp.close();
			if(br != null)
				br.close();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		socketUdp.close();

		if(getUser() != null){
			getUser().setBind(null);
			setUser(null);
		}

	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
