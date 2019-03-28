package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class StealthyUDPServerSocket extends Thread {
	 
    private DatagramSocket socket;
    private byte[] buf = new byte[1024];
    int dataStartIndex = 0;
    int dataEndIndex = -1;
    public StealthyUDPServerSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("[+] Server Socket Instantiated On Port "+port);
        System.out.println("[+] Server Buffer Size Is "+buf.length);
        if(port==53 || port == 5355){ //DNS and LLMNR Handler
        	System.out.println("[s] Looks like you're impersonating DNS or LLMNR");
        	dataStartIndex = 0;
        	dataEndIndex = 2;
        }
        
    }
    public void run() {
    	System.out.println("[+] Server Run Started");
    	String totalRecieved = "";
        while (true) {
        	System.out.println("[~] Awaiting new packets...");
        	System.out.println(totalRecieved);
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String recvMessage = "";
            System.out.println("[!] Packet Recieved!");
            if(dataEndIndex==-1){
            	recvMessage = new String(buf);
            }
            else{
            	for(int i = dataStartIndex; i < dataEndIndex; i++){
            		recvMessage+=(char)buf[i];
            	}
            }
            totalRecieved+=recvMessage;
            System.out.println("	Recieved message: "+recvMessage);
            Arrays.fill(buf,(byte)0);
        }
    }
}