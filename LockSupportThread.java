package thread;

import java.util.concurrent.locks.LockSupport;

public class LockSupportThread {

	static Thread t1 = null;
	static Thread t2 = null;
	static Thread t3 = null;
	
	
	public static void main(String[] args) throws RuntimeException, InterruptedException{
		Object o = new Object();
//		synchronized(o){
			for(int i=0;i<20;i++){
			System.out.println("第"+i+"次进来");
			doAction();
			System.out.println("");
		}
//	}
	}


	private static void doAction() throws InterruptedException {
		
		final char[] a1 = "1234567".toCharArray();
		final char[] a2 = "ABCDEFG".toCharArray();
		final char[] a3 = "!@#$%^&".toCharArray();
		
		t1 = new Thread(new Runnable(){
			@Override
			public void run() {
				for(char c : a1){
					System.out.print(c);
					LockSupport.unpark(t3);
					LockSupport.park();
				}
			}
		},"t1");
		
		t2 = new Thread(new Runnable(){
			@Override
			public void run() {
				for(char c : a2){
					LockSupport.park();
					System.out.print(c);
					LockSupport.unpark(t1);
				}
				
			}
		},"t2");
		
		t3 = new Thread(new Runnable(){
			@Override
			public void run() {
				for(char c : a3){
					LockSupport.park();
					System.out.print(c);
					LockSupport.unpark(t2);
				}
				
			}
		},"t3");
		
		t2.start();t3.start();t1.start();
		t2.join();t3.join();t1.join();
	}

}
