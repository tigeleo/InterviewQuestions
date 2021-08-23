package dave.interview;


import java.util.List;
import java.util.Random;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;

public class TestCashFilter {
	private static TestCashFilter filter =null;
	protected Cache cache;
	private static Configuration cacheConfig = new Configuration().name("TestCashFilter");
	private static int getNumOfMaxAllowedRequests=40;
	
	private long timeCounter=0;
	
	
	public synchronized void addTime(long time){
		this.setTimeCounter(this.getTimeCounter() + time);
	}
	
	
	
	private TestCashFilter(){
		CacheManager singletonManager = CacheManager.newInstance(cacheConfig);
		if (singletonManager.cacheExists(cacheConfig.getName())) {
			singletonManager.removeCache(cacheConfig.getName());
		}
		Cache memoryOnlyCache = new Cache(cacheConfig.getName(), 5000, false, false, 5, 5);
		singletonManager.addCache(memoryOnlyCache);
		cache = singletonManager.getCache(cacheConfig.getName());
		
	}
	
	
	public synchronized static TestCashFilter factory(){
		if(filter==null){
			filter=new TestCashFilter();
		}
		return filter;
	}
	
	public static int getNumOfMaxAllowedRequests(){
		return getNumOfMaxAllowedRequests;
	}
	
	/**
	 * will return true if blocked
	 * 
	 * @param filterKey
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean doCacheBasedFilteringNew(String filterKey, int userId) {
		//List<String> keyList = cache.getKeysWithExpiryCheck();
		Element element = cache.removeAndReturnElement(filterKey);

		Integer reqCounter = 0;
		if (element != null) {
			if (!element.isExpired()) {
				
				reqCounter = (Integer) element.getObjectValue();
			}else{
				System.out.println("Element expired: " + element.getObjectKey() + ": " + element.getObjectValue());
			}
		}
		//System.out.println("User: " + userId + ", key:" + filterKey + ", value:  "+ reqCounter);
		element = new Element(filterKey,++reqCounter );
		cache.put(element);

		if (reqCounter > getNumOfMaxAllowedRequests()) {
			//System.out.println("Blocked request: " + filterKey + ", reqCounter = " + reqCounter + " > " + getNumOfMaxAllowedRequests());

			return true;
		}

		return false;
	}
	
	
	/**
	 * will return true if blocked
	 * @param filterKey
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean doCacheBasedFilteringBad(String filterKey, int userId){
		List<String> keyList = cache.getKeysWithExpiryCheck();
		Element element = new Element(filterKey+"_"+System.currentTimeMillis(), filterKey);
		cache.put(element);

		int reqCounter = 1; // current request
		for(String id : keyList){
			if(id.startsWith(filterKey+"_")){
				reqCounter++;
			}
			if(reqCounter>getNumOfMaxAllowedRequests()){
				//System.out.println("Blocked request: of user:"+userId+":" +id + ", reqCounter = "+reqCounter+" > "+getNumOfMaxAllowedRequests());


				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		for(int u=0;u<1000;u++){
			TestUser user=new TestUser(u);
			user.run();
		}
		
		long timeStart=System.currentTimeMillis();
		System.out.println("Run time: " + TestUser.isHaveOneLive() + " , Start: " + timeStart );

		while(TestUser.isHaveOneLive()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long timeEnd=System.currentTimeMillis();
		
		System.out.println("Run time: " + TestCashFilter.factory().getTimeCounter() + " , Delta: " + (timeEnd-timeStart) );
	}
	
	public long getTimeCounter() {
		return timeCounter;
	}



	public void setTimeCounter(long timeCounter) {
		this.timeCounter = timeCounter;
	}

	public static int  requestCountPerUser=10000;
	
	private static class TestUser extends Thread{
		private int userId;
		private Random rd;
		
		private static int threads=0;
		
		public static boolean isHaveOneLive(){
			return threads > 0;
		}
		
		public TestUser(int userId) {
			this.userId=userId;
			this.rd=new Random();
			threads++;
		}
		
		@Override
		public void run() {
			long timeStart=System.currentTimeMillis();
			
			for(int z=0;z<4;z++){
				for(int i=0;i<requestCountPerUser/4;i++){
					int r=rd.nextInt(255);
					if(TestCashFilter.factory().doCacheBasedFilteringNew(""+r, userId)){
						//System.out.println("User" + userId + " retqest number i="+ i +" is blocked");
					}
				}
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			long timeEnd=System.currentTimeMillis();
			
			TestCashFilter.factory().addTime(timeEnd-timeStart);
			threads--;
		}
	}
	
}

