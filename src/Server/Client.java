package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	private String request;
	
	public Client(String request){
		this.request = request;
	}
	
	
	public void run(){
		System.out.println("Thread Start");
        try (
            Socket kkSocket = new Socket("127.0.0.1", 8080);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
 
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("quit")){
                	kkSocket.close();
                    break;
                }
                out.println("time");
     
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to Server");
            System.exit(1);
        }
        System.out.println("End Thread");
	}//Thread Runner
	
}//end Client
