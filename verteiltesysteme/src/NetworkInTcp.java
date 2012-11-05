import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NetworkInTcp extends Thread{
	
	protected BufferedReader br;
	protected Socket socketTcp;
	protected String input = "";
	protected Client client;
	
	public NetworkInTcp(Socket acceptTcp, Client client) {
		this.client = client;
		socketTcp = acceptTcp;
		try {
			br = new BufferedReader(new InputStreamReader(socketTcp.getInputStream()));
		} catch (IOException e) {
			System.out.println("error socket");
			client.shutdown();
		}
	}
	
	public void run() {
		try {
			while(input != null){
				if(!input.equals(""))
					process(input);
				if(br != null)
					input = br.readLine();
				else
					input = null;
			}

		} catch (IOException e) {
		} finally {	
			shutdown();
		} 
	}
	
	protected void process(String input){
		System.out.println(input);
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
	}
}
