package thread;

/**
 * 通过synchonized来让线程notify/wait来实现线程之间的切换。
 * 
 * 弊端：
 * 线程之间的切换是通过notify或者notifyAll来处理的，这个是没法指定线程来切换。
 * @author Administrator
 *
 */
public class WaitNotifyThread {
	
	static Thread t1 = null;
	static Thread t2 = null;
	static Thread t3 = null;

	public static void main(String[] args) throws InterruptedException {
//		for(int i=0;i<100;i++){
				doAction();
				System.out.println("");
//		}
	}

	private static void doAction() throws InterruptedException {
		final char[] a1 = "1234567".toCharArray();
		final char[] a2 = "ABCDEFG".toCharArray();
		final char[] a3 = "abcdefg".toCharArray();
		final Object o = new Object();
		t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				synchronized(o){
					for(char c : a1){
						System.out.print(c);
						o.notify();
						try {
							o.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					o.notify();
				}
			}
			
		});
		
		t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				synchronized(o){
					for(char c : a2){
						System.out.print(c);
						o.notify();
						try {
							o.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					o.notify();
				}
			}
			
		});
		
		t3 = new Thread(new Runnable(){
			
			@Override
			public void run() {
				synchronized(o){
					for(char c : a3){
						System.out.print(c);
						o.notify();
						try {
							o.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					o.notify();
				}
			}
			
		});
		
		
		t1.start();t2.start();t3.start();
		t1.join();t2.join();t3.join();
	}
}
