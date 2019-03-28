package main;

import java.net.SocketException;

import networking.MultiserviceStealthUDPServer;

public class MainServer {
	public static void main(String[] args) {
		try {
			MultiserviceStealthUDPServer mssus = new MultiserviceStealthUDPServer(1337);
			mssus.startAllServers();
			} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
