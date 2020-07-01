package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockThread {
	
	static ReentrantLock lock = null;
	static Condition condition = null;
	static Thread t1 = null;
	static Thread t2 = null;
	static Thread t3 = null;
	
	
	public static void main(String[] args) throws InterruptedException {
		Object o = new Object();
//			synchronized(o){
				for(int i=0;i<20;i++){
				System.out.println("第"+i+"次进来");
				doAction();
				System.out.println("");
			}
//		}
	}


	private static void doAction() throws InterruptedException {
		final char[] a1 = "1234567".toCharArray();
		final char[] a2 = "ABCDEFG".toCharArray();
		final char[] a3 = "abcdefg".toCharArray();
		
		lock = new ReentrantLock();
		condition = lock.newCondition();
		
		t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					lock.lock();
					for(char c : a1){
						System.out.print(c);
						condition.signal();
						condition.await();
					}
					condition.signal();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
			
		});
		
		t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					lock.lock();
					for(char c : a2){
						System.out.print(c);
						condition.signal();
						condition.await();
					}
					condition.signal();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
			
		});
		
		t3 = new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					lock.lock();
					for(char c : a3){
						System.out.print(c);
						condition.signal();
						condition.await();
					}
					condition.signal();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
			
		});
		
		t1.start();t2.start();t3.start();
		t1.join();t2.join();t3.join();
	}
	
}
