package commandline;

import java.util.ArrayList;
import java.util.Scanner;

import networking.MultiserviceStealthUDPServer;

public class LocalCommandInterpreter{
	Scanner in;
	ArrayList<String> commands;
	ArrayList<String> storedMessages;
	public LocalCommandInterpreter(){
		in=new Scanner(System.in);
		commands=new ArrayList<String>();
		storedMessages = new ArrayList<String>();
		commands.add("list_commands");
		commands.add("clear_inbox");
		commands.add("store_inbox");
		commands.add("read_inbox");
		commands.add("get_history");
	}
	public boolean checkCommand(String command) {
		for(String c: commands) {
			if(c.equalsIgnoreCase(command)) {
				return true;
			}
		}
		return false;
	}
	public void runCommand(String command) {
		if(command.startsWith("list_commands")) {
			for(String c:commands) {
				System.out.println(c);
			}
		}else if(command.startsWith("clear_inbox")) {
			MultiserviceStealthUDPServer.totalRecieved="";
		}else if(command.startsWith("store_inbox")) {
			storedMessages.add(MultiserviceStealthUDPServer.totalRecieved);
		}else if(command.startsWith("read_inbox")) {
			System.out.println(MultiserviceStealthUDPServer.totalRecieved);
		}else if(command.startsWith("get_history")) {
			for(int i =0; i<storedMessages.size(); i++) {
				System.out.println("["+i+"] "+storedMessages.get(i));
			}
		}
	}
	
}

