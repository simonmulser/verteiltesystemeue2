import java.util.LinkedList;
import java.util.Scanner;


public class Helper {
	
	public static LinkedList<String> spaceTokenizer(String input){
		Scanner sc = new Scanner(input);
		LinkedList<String> ret = new LinkedList<String>();
		while(sc.hasNext()){
			ret.add(sc.next());
		}
		return ret;
	}

	public static String getDescription(LinkedList<String> inputs, int from) {
		String ret ="";
		for(int i = from; i < inputs.size(); i++){
			ret += inputs.get(i) + " ";
		}
		return ret.substring(0,ret.length() - 1);
	}

}
