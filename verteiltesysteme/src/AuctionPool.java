import java.util.LinkedList;
import java.util.Timer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class AuctionPool {

	private static AuctionPool auctionpool = null;
	private LinkedList<User> users;
	private LinkedList<Auction> auctions;
	private LinkedList<Auction> expiredAuctions;
	private Timer timer;
	private final ReentrantReadWriteLock lock;
	private final Lock readLock;
	private final Lock writeLock;

	public AuctionPool(){
		users = new LinkedList<User>();
		auctions = new LinkedList<Auction>();

		timer = new Timer();

		timer.schedule(new NotificationsTask(), 1000, 1000);

		lock = new ReentrantReadWriteLock(true);
		readLock = lock.readLock();
		writeLock = lock.writeLock();
		
		expiredAuctions = new LinkedList<Auction>();
		
	}

	public static AuctionPool getInstance(){
		if (auctionpool == null)
			auctionpool = new AuctionPool();
		return auctionpool;
	}

	public User login(String name, ClientBind clientBind){
		writeLock.lock();

		try{
			User user = containsUser(name);
			if(user == null){
				user = new User(name, clientBind);
				users.add(user);
				return user;
			}
			if(user.getBind() == null){
				user.setBind(clientBind);
				return user;
			}
			else
				return null;
		} finally {
			writeLock.unlock();

		}
	}

	public int createAuction(Long date, String name, User user){
		writeLock.lock();
		try{
			Auction auction = new Auction(date, name, user);
			auctions.add(auction);
			return auction.getId();
		} finally {
			writeLock.unlock();
		}
	}

	public boolean bid(Auction auction, Double amount, User user) {
		writeLock.lock();
		try{
			if(auction.getHighestBid() < amount){
				if(auction.getHighestBider() != null)
					auction.getHighestBider().addNotification("!new-bid " + auction.getName());
				auction.setHighestBid(amount);
				auction.setHighestBider(user);
				return true;
			}
			return false;
		} finally {
			writeLock.unlock();
		}
	}

	public String getList() {
		readLock.lock();
		try{
			String ret = "";
			for(Auction auction : auctions){
				ret += auction.info() + "\n";
			}
			return ret;
		} finally {
			readLock.unlock();
		}
	}

	public void expiredAuctions() {
		writeLock.lock();

		for (Auction auction : auctions){
			if(auction.isExpired())
				expiredAuctions.add(auction);

		}
		auctions.removeAll(expiredAuctions);

		expiredAuctions.clear();
		
		writeLock.unlock();	

	}

	public LinkedList<Auction> getAuctions(){
		return auctions;
	}

	private User containsUser(String name) {
		for(User user : users){
			if(user.getName().equals(name))
				return user;
		}
		return null;
	}

	public Auction getAuction(int id) {
		for(Auction auction : auctions){
			if(auction.getId() == id)
				return auction;
		}
		return null;
	}
	public void shutdown() {
		timer.cancel();
	}

}
