package networking;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiserviceStealthUDPServer extends Thread {
    
    List<Integer> availablePorts;
    
    private byte[] buf = new byte[1024];
    Random rand;
    public static String totalRecieved = "";
    public MultiserviceStealthUDPServer(int randSeed) throws SocketException {
        rand = new Random(randSeed);
        
        availablePorts = new ArrayList<Integer>();
        availablePorts.add(53);
        availablePorts.add(5355);
        availablePorts.add(67);
        availablePorts.add(123);
    }
    public MultiserviceStealthUDPServer() throws SocketException {
        rand = new Random();
        
        availablePorts = new ArrayList<Integer>();
        availablePorts.add(53);
        availablePorts.add(5355);
        availablePorts.add(67);
        availablePorts.add(123);
    }
    public void startAllServers(){
    	final ExecutorService executor = Executors.newCachedThreadPool();
    	final List<Future<?>> futures = new ArrayList<>();
    	for(int port : availablePorts){
    		Future<?> future = executor.submit(() -> {
				try {
					run(port);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
    		futures.add(future);
    	}
    	for (Future<?> future : futures) {
            try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    }
    private int[] port2DataStartEnd(int port){
    	int[] temp = new int[2];
    	if(port==53){
    		temp[0]=0;
    		temp[1]=2;
    	}else if(port==5355){
    		temp[0]=0;
    		temp[1]=2;
    	}else if(port == 67){
    		temp[0]=4;
    		temp[1]=8;
    	}else if(port == 123) {
    		temp[0]=12;
    		temp[1]=16;
    	}else{
    		temp[0]=0;
    		temp[1]=-1;
    	}
    	return temp;
    }
    public void run(int port) throws SocketException {
    	System.out.println("[!] About to start a new server on port: "+port);
    	DatagramSocket socket = null;
    	try{
    		socket = new DatagramSocket(port);
    	}catch(BindException be){
    		System.out.println("Encountered an error binding to port "+port);
    		be.printStackTrace();
    	}
    	
    	int[] dataBounds = port2DataStartEnd(port);
    	System.out.println("[+] Server Run Started for port: "+port);
        while (true) {
        	System.out.println("[~] Awaiting new packets...");
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
            	
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String recvMessage = "";
            System.out.println("[!] Packet Recieved on port "+port);
            if(dataBounds[1]==-1){
            	recvMessage = new String(buf);
            }
            else{
            	for(int i = dataBounds[0]; i < dataBounds[1]; i++){
            		recvMessage+=(char)buf[i];
            	}
            }
            totalRecieved+=recvMessage;
            System.out.println("	Recieved message: "+recvMessage);
            Arrays.fill(buf,(byte)0);
        }
    }
}