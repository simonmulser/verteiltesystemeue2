import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;


public class Network extends Thread {
	
	private ServerSocket socket;
	private ExecutorService pool;
	private LinkedList<ClientBind> listClients;

	public Network(ServerSocket socket, ExecutorService pool, LinkedList<ClientBind> listClients) {
		this.socket = socket;
		this.pool = pool;
		this.listClients = listClients;	
	}

	public void run() {
		//System.out.println("run network");
		try {
			ClientBind temp;
			while(true){
				temp = new ClientBind(socket.accept());
				
				pool.execute(temp);
				
				listClients.add(temp);
				
			}
		} catch (SocketException s){
		}catch (IOException e) {
			e.printStackTrace();
		}
	//System.out.println("networkthread exit");
	}
}
