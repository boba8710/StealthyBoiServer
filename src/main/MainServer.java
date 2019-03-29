package main;

import java.net.SocketException;

import commandline.CommandInterpreter;
import networking.MultiserviceStealthUDPServer;

public class MainServer {
	public static void main(String[] args) {
		try {
			CommandInterpreter ci = new CommandInterpreter();
			Thread t = new Thread(ci);
			t.start();
			MultiserviceStealthUDPServer mssus = new MultiserviceStealthUDPServer(1337);
			mssus.startAllServers();
			} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
