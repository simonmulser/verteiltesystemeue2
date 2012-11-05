import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class ClientInUdp extends Thread{

	private DatagramPacket packet;
	private DatagramSocket socket;
	private Client client;
	private HashMap<String, OperationClient> operations;
	private LinkedList<String> inputs;
	private OperationClient operation;


	public ClientInUdp(int udpPort, Client client){

		try {
			socket = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			System.out.println("error udpSocket");
			client.shutdown();
		}
		
		this.client = client;
		
		operations = new HashMap<String, OperationClient>();
		operations.put("!auction-ended", new AuctionEndedOperationClient(client));
		operations.put("!new-bid", new NewBidOperationClient());
	}


	public void run() {
		
		byte[] buf = new byte[256];
		packet = new DatagramPacket(buf, buf.length);
		try {
			while(true){
				if(socket == null)
					break;
				socket.receive(packet);
				process(new String(packet.getData(), 0, packet.getLength()));
			}

		} catch (IOException e) {
		}

	}

	private void process(String input) {
	
		inputs = Helper.spaceTokenizer(input);
		operation = operations.get(inputs.get(0));
		
		if(operation != null){
			try {
				operation.execute(inputs);
			} catch (CommunicationException e) {
				System.out.println(e.getMessage());
			}
		}
		else{
			System.out.println(input);
		}
		
		Scanner scanner = new Scanner(input);
		if (scanner.hasNext()){
			String command = scanner.next();
			if(command.equals("!lis")){
				System.out.println(scanner.next() + ". '" + scanner.next() + "' " + scanner.next() + " " + new Date(new Long(scanner.next())) + " " + scanner.next() + " " + scanner.next());
			}else{
				if(command.equals("!auction-ende")){
					String output = "The auction '";
					String name = scanner.next();
					Double amount = new Double(scanner.next());
					while(scanner.hasNext()){
						output += scanner.next();
						if (scanner.hasNext()) 
							output += " ";
					}
					output += "' has ended. ";
					if(name.equals(client.getName()))
						output += "You won with " + amount + "!";
					else 
						output += name + " won with " + amount + ".";
					input = output;
				}
			}
		}
	}


	public void shutdown(){
		if(socket != null)
			socket.close();
	}

}
