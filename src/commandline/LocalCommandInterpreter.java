package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
		commands.add("/help");
		commands.add("/clear: Clears current UDP buffer");
		commands.add("/save: Saves current UDP buffer");
		commands.add("/read: Prints current UDP buffer to screen");
		commands.add("/history: Show past messages and their indexes");
		commands.add("/file <index> <filename>: Saves a file out of history");
		commands.add("/quit: Stop the server");
	}
	public boolean checkCommand(String command) {
		if(command.startsWith("/")) {
			return true;
		}
		return false;
	}
	public void runCommand(String command) throws IOException {
		if(command.startsWith("/help")) {
			for(String c:commands) {
				System.out.println(c);
			}
			System.out.println("Anything not on the list above will be sent to the client as a shell command");
		}else if(command.startsWith("/clear")) {
			MultiserviceStealthUDPServer.totalRecieved="";
		}else if(command.startsWith("/save")) {
			storedMessages.add(MultiserviceStealthUDPServer.totalRecieved);
		}else if(command.startsWith("/read")) {
			System.out.println(MultiserviceStealthUDPServer.totalRecieved);
		}else if(command.startsWith("/history")) {
			for(int i =0; i<storedMessages.size(); i++) {
				System.out.print("["+i+"] "+storedMessages.get(i).substring(0, Math.min(storedMessages.get(i).length(), 30)));
				if(storedMessages.get(i).length()>30){
					System.out.print("...");
				}
				System.out.println("");
			}
		}else if(command.startsWith("/file")){
			System.out.println("[+] Starting File Save");
			try{
				String[] commandParts = command.split(" ");
				int entryNumber=Integer.parseInt(commandParts[1]);
				String fileName=commandParts[2];
				File file = new File(fileName);
				System.out.println("[+] Saving File "+entryNumber+" to ./"+fileName);
				FileOutputStream fileWriter = new FileOutputStream(file);
				fileWriter.write(storedMessages.get(entryNumber).getBytes());
				fileWriter.close();
				System.out.println("[+] File Write Complete.");
			}catch(Exception e){
				System.out.println("Error: Proper Syntax:");
				System.out.println("\n/file <index> <filename>");
			}
			
		}else if(command.equals("/quit")){
			System.exit(0);
		}else{
			System.out.println("Unrecognized Command. Available Commands: \n\n");
			for(String c:commands) {
				System.out.println(c);
			}
			System.out.println("Anything not on the list above will be sent to the client as a shell command");
			
		}
	}
	
}

