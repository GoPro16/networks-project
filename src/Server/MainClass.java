
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
	private static boolean isRunning;

	public static void main(String[] args) {
		isRunning = true;
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			ServerProtocol kkp = new ServerProtocol();
			String inputLine, outputLine;
			while (isRunning) {
				clientSocket = serverSocket.accept();
				System.out.println("Client Connected");
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				// Initiate conversation with client
				outputLine = kkp.processInput("WAITING");
				out.println(outputLine);
				while (!clientSocket.isClosed() && (inputLine = in.readLine()) != null) {
					outputLine = kkp.processInput(inputLine);
					out.println(outputLine);
					if (outputLine.equals("quit")) {
						clientSocket.close();
						break;
					}

				}
				System.out.println("Client Disconnected");
			}
		} catch (Exception e) {
			System.out.println(
					"Exception caught when trying to listen on port " + 8080 + " or listening for a connection");
			e.printStackTrace();
		}
	}
}

class ServerProtocol {
	private static final int WAITING = 0;
	private static final int SENTREQUEST = 1;
	private static final int NUMJOKES = 5;

	private int state = WAITING;

	public String processInput(String theInput) {
		String theOutput = null;

		if (state == WAITING) {
			theOutput = "1";
			state = SENTREQUEST;
		} else if (state == SENTREQUEST) {
			switch (theInput) {
			case "time":
				theOutput = getOutput("date");
				break;
			case "uptime":
				theOutput = getOutput("uptime");
				break;
			case "memory":
				theOutput = getOutput("free");
				break;
			case "netstat":
				theOutput = getOutput("netstat");
				break;
			case "users":
				theOutput = getOutput("users");
				break;
			case "process":
				theOutput = getOutput("ps aux");
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

	public String getOutput(String s) {
		String line = "";
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(s);

			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String temp = "";
			while ((temp = input.readLine()) != null) {
				line += temp;
			}
			process.waitFor();
			return line;
		}

		catch (Exception e) {
			System.out.println("Runtime Catch");
			System.out.println(e.toString());
		}
		return line;
	}
}
