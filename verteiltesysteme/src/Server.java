import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements SystemInInterface{
	
	private ServerSocket socket;
	private ExecutorService pool;
	private Network network;
	private SystemIn systemIn;
	private LinkedList<ClientBind> listClients;
	
	public Server(String port) {
		
		try {
			listClients = new LinkedList<ClientBind>();
			socket = new ServerSocket(new Integer(port));
			pool = Executors.newCachedThreadPool();
			network = new Network(socket, pool, listClients);
			
			systemIn = new SystemIn(this);

			network.start();
			systemIn.start();
			
		} catch (NumberFormatException e) {
			System.err.println("tcpPort must be a number");
			shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("io error");
			shutdown();
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("Usage: Server tcpPort");
			System.exit(1);
		}
		
		new Server(args[0]);

	}

	public void shutdown() {

		systemIn.interrupt();
		
		AuctionPool.getInstance().shutdown();
			
		pool.shutdown();
		for(ClientBind client : listClients){
			client.shutdown();
		}
		pool.shutdownNow();
		
		try {
			if(socket != null)
				socket.close();
		} catch (IOException e) {
			System.err.println("error socket close");
			e.printStackTrace();
		}


	
	}

	public void systemInInput(String input) {
		if(input.equals(""))
			shutdown();
	}

}
