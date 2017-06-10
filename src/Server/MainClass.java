
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.*;
import java.io.*;


/*
 * Main class for the server side of the program
 */
public class MainClass {

	private static ServerSocket serverSocket;
	private static boolean isRunning;
	private static ArrayList<ResponseThread> responseThreads = new ArrayList<ResponseThread>();
        
	public static void main(String[] args) {
		isRunning = true;

		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			ResponseThread newThread;
			String inputLine, outputLine;
			while (isRunning) {
				newThread = new ResponseThread(serverSocket.accept());
				newThread.start();
				responseThreads.add(newThread);
			}
		} catch (Exception e) {
			System.out.println(
					"Exception caught when trying to listen on port " + 8080 + " or listening for a connection");
			e.printStackTrace();
		}
	}
}