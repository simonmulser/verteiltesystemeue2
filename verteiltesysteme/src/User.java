import java.util.LinkedList;

public class User {

	private String name;
	private LinkedList<String> notifications;
	private ClientBind bind;

	public User(String name, ClientBind bind) {
		this.name = name;
		notifications = new LinkedList<String>();
		this.bind = bind;

	}

	public String getName() {
		return name;
	}

	public void addNotification(String notification) {
		if(bind != null)
			bind.sendUdp(notification);
		else
			notifications.add(notification);
	}

	public ClientBind getBind() {
		return bind;
	}

	public void setBind(ClientBind bind) {
		this.bind = bind;
	}

	public void getNotifications() {
		for(String notification : notifications){
			bind.sendUdp(notification);
		}
	}


}
