package thread;

import java.util.concurrent.LinkedTransferQueue;
/**
 * 手递手队列
 * 相当于套筒transfer
 * @author Administrator
 *
 */
public class TransferQueue {
	
	static Thread t1 = null;
	static Thread t2 = null;

	public static void main(String[] args) throws InterruptedException {
		Object o = new Object();
		for(int i=0;i<1;i++){
			doAction();
			System.out.println("");
		}
	}

	private static void doAction() throws InterruptedException {
		final char[] a1 = "1234567".toCharArray();
		final char[] a2 = "ABCDEFG".toCharArray();
		
		final LinkedTransferQueue<Character> tf = new LinkedTransferQueue<Character>();
		t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					for(char c : a1){
						tf.transfer(c);
						System.out.print(tf.take());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		});
		
		t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					for(char c : a2){
						System.out.print(tf.take());
						tf.transfer(c);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		});
		
		
		t1.start();t2.start();
		t1.join();t2.join();
	}
}
