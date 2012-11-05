import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements SystemInInterface {
		
	private Socket socket;
	private SystemIn systemIn;
	private PrintWriter out;
	private ClientInTcp clientInTcp;
	private String name = "";

	public Client(String host, String tcpPort, String udpPort) {
		
		try {
			
			socket = new Socket(host, Integer.parseInt(tcpPort));
			out = new PrintWriter(socket.getOutputStream(), true);
						
			systemIn = new SystemIn(this);
			
			clientInTcp = new ClientInTcp(socket, this);

			systemIn.start();
			clientInTcp.start();
			
		} catch (UnknownHostException e) {
			System.err.println("unkown host");
			shutdown();
		} catch (IOException e) {
			System.err.println("io/server error");
			e.printStackTrace();
			shutdown();
		} 
	}

	/**
	 *	@params		  
	 *  host: host name or IP of the auction server
     *	tcpPort: TCP connection port on which the auction server is listening for incoming connections
     *	udpPort: this port will be used for instantiating a java.net.DatagramSocket (handling UDP notifications from the auction server). 
	 */
	public static void main(String[] args){
		
		if(args.length != 3){
			System.out.println("Usage: Client host tcpPort udpPort");
			System.exit(1);
		}
		
		new Client(args[0], args[1], args[2]);
	}
	

	public void shutdown() {
		if(systemIn != null)
			systemIn.shutdown();
		
		if(out != null)
			out.close();
		
			try {
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				System.err.println("io error");
				e.printStackTrace();
			}		
	}

	public void systemInInput(String input) {
		if(input.equals("!end"))
			shutdown();
		else
			out.println(input);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
