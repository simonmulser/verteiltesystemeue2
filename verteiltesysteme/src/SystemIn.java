import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SystemIn extends Thread {

	private SystemInInterface out;
	private BufferedReader br;

	public SystemIn(SystemInInterface out) {
		this.out = out;			
	}

	public void run() {
		//System.out.println("run system.in");
		br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		try {
			while( !isInterrupted() ){
				input = br.readLine();
				out.systemInInput(input);
			}

		}catch (IOException e) {
		}
		//System.out.println("systeminthread exit");
	}

	public void shutdown() {
		try {
			if(br != null)
				br.close();
		} catch (IOException e) {
			System.out.println("error closing br");
		}

	}
}
