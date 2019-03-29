package commandline;

import java.util.ArrayList;
import java.util.Scanner;

import networking.MultiserviceStealthUDPServer;

public class CommandInterpreter implements Runnable{
	Scanner in;
	ArrayList<String> commands;
	ArrayList<String> storedMessages;
	public CommandInterpreter(){
		in=new Scanner(System.in);
		commands=new ArrayList<String>();
		storedMessages = new ArrayList<String>();
		commands.add("help");
		commands.add("clear");
		commands.add("store");
		commands.add("read");
		commands.add("history");
	}
	public void run() {
		while(true) {
			System.out.print("ζ>");
			String command = in.nextLine();
			if(command.startsWith("help")) {
				for(String c:commands) {
					System.out.println(c);
				}
			}else if(command.startsWith("clear")) {
				MultiserviceStealthUDPServer.totalRecieved="";
			}else if(command.startsWith("store")) {
				storedMessages.add(MultiserviceStealthUDPServer.totalRecieved);
			}else if(command.startsWith("read")) {
				System.out.println(MultiserviceStealthUDPServer.totalRecieved);
			}else if(command.startsWith("history")) {
				for(int i =0; i<storedMessages.size(); i++) {
					System.out.println("["+i+"] "+storedMessages.get(i));
				}
			}
		}
	}
}

