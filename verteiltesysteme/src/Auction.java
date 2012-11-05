import java.util.Date;


public class Auction {

	private long date;
	private String name;
	private User seller;
	private User highestBider;
	private double highestBid;
	private final int id;
	private static int counter = 1;
	
	
	public Auction(Long date, String name, User user) {
		this.date = System.currentTimeMillis() + date * 1000;
		this.name = name;
		this.seller = user;
		id = counter++;
		setHighestBid(0.00);
		setHighestBider(null);
	}
	
	
	
	public String info() {
		String info = "!list " + getId() + " " 
					+ seller.getName() + " " + this.date + " " + getHighestBid() + " ";
		if(getHighestBider() == null)
			info += "none";
		else
			info += getHighestBider().getName();
		
		return info + " " + getName();
	}

	public boolean isExpired() {
		if(this.date < System.currentTimeMillis()){
			if(getHighestBider() != null){
				getHighestBider().addNotification("!auction-ended " + getHighestBider().getName() + " " + getHighestBid() + " " + getName());
				seller.addNotification("!auction-ended " + getHighestBider().getName() + " " + getHighestBid() + " " + getName());
			}
			else
				seller.addNotification("!auction-ended none " + getHighestBid() + " " + getName());
			return true;
		}
		return false;
	}


	public int getId() {
		return id;
	}


	public User getHighestBider() {
		return highestBider;
	}


	public void setHighestBider(User highestBider) {
		this.highestBider = highestBider;
	}


	public double getHighestBid() {
		return highestBid;
	}


	public void setHighestBid(double highestBid) {
		this.highestBid = highestBid;
	}


	public String getName() {
		return name;
	}

}
