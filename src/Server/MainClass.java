package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.net.*;
import java.io.*;
 
/*
 * Main class for the server side of the program
 */
public class MainClass {
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static PrintWriter out;
	private static BufferedReader in;
	
	public static void main(String[] args){
		try{
			ServerSocket serverSocket = new ServerSocket(8080);
			while(true){
				
				clientSocket = serverSocket.accept();
				out = new PrintWriter(clientSocket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	            String inputLine, outputLine;
	             
	            // Initiate conversation with client
	            ServerProtocol kkp = new ServerProtocol();
	            outputLine = kkp.processInput("WAITING");
	            out.println(outputLine);
	 
	            while ((inputLine = in.readLine()) != null) {
	                outputLine = kkp.processInput(inputLine);
	                out.println(outputLine);
	                if (outputLine.equals("quit")){
	                	out.println("quit");
	                	break;
	                }
	                    
	            }
			}
		}catch(Exception e){
			 System.out.println("Exception caught when trying to listen on port "
		                + 8080 + " or listening for a connection");
		            e.printStackTrace();
		}
	}
}
class ServerProtocol {
    private static final int WAITING = 0;
    private static final int SENTREQUEST = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;
 
    private static final int NUMJOKES = 5;
 
    private int state = WAITING;

 
    public String processInput(String theInput) {
        String theOutput = null;
 
        if (state == WAITING) {
            theOutput = "1";
            state = SENTREQUEST;
        } else if (state == SENTREQUEST) {
            switch(theInput){
            case "time":
            	theOutput = "quit";
            	break;
            case "uptime":
            	theOutput = "test";
            break;
            case "memory":
            	theOutput = "test";
            	break;
            case "netstat":
            	theOutput = "test";
            	break;
            case "users":
            	theOutput = "test";
            	break;
            case "process":
            	theOutput = "test";
            	break;
            case "quit":
            	theOutput = "quit";
            	break;
            default:
            	
            	break;
            }
        	/*
        	if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke];
                state = SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                "Try again. Knock! Knock!";
            }*/
        }
        return theOutput;
    }
}
