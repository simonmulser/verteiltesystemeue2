
public class CommunicationException extends Exception {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6950508920489567454L;	
	/**
	 * The standard constructor
	 * 
	 */
	
	public CommunicationException(){
		super();
	}
	
	/**
	 * The constructor to save also error message
	 * 
	 * @param message
	 * 			the error message 
	 */
	
	public CommunicationException(String message){
		super(message);
	}

}
