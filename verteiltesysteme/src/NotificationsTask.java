import java.util.TimerTask;



public class NotificationsTask extends TimerTask{
	
	@Override
	public void run() {
		AuctionPool.getInstance().expiredAuctions();
	}

}
