
package benchmarking;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NetworkServer {
	 private static ServerSocket ss=null;
	 private static boolean serverlive=false;
	 private static final int threadnum=8;
	  static int buffersize=0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args[0].equals("tcp")){
		buffersize=Integer.parseInt(args[1]);	
		System.out.println(buffersize);
		new NetworkServer().starttcp();// TODO Auto-generated method stub
		}
		else if(args[0]=="udp"){
			
		}
	}
	public void starttcp(){
		try {
			ss=new ServerSocket(8885);
			serverlive=true;
			Thread[] threads=new Thread[threadnum];
			while(serverlive){
				 
				Socket socket=ss.accept();
				
				TCPThread tcpthread =new TCPThread(socket);
				
				for(int i=0;i<threadnum;i++){
					
					threads[i]=new Thread(tcpthread);
					threads[i].start();
					
				}
			}
		} catch (IOException e) {
			System.out.println("excpetion");// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
class TCPThread implements Runnable{
	private static int buffersize = 0;
	private DataInputStream inputstream=null;
	private PrintStream printstream = null;
	private Socket clientsocket=null;
	private BufferedReader infromclient=null;
	public TCPThread(Socket clientSocket){
		this.clientsocket=clientSocket;
	}
	@Override
	public void run() {
		buffersize=NetworkServer.buffersize;
		// System.out.println("server start...."+buffersize);
		try {
			infromclient=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()),1024);
			//System.out.println(infromclient.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
