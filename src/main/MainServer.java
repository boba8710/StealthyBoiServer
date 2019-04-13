package main;

import java.io.IOException;
import java.net.SocketException;

import commandline.LocalCommandInterpreter;
import networking.CommandServer;
import networking.MultiserviceStealthUDPServer;

public class MainServer {
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
		try {
			CommandServer cs = new CommandServer();
			Thread t2 = new Thread(cs);
			t2.start();
			MultiserviceStealthUDPServer mssus = new MultiserviceStealthUDPServer();
			mssus.startAllServers();
			} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
