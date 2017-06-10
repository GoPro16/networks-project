
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class Client extends Thread {
    private String str;
    private long startTime;
    private long totalTime;
    private String address;

    public Client(String str, String address) {
        this.str = str;
        this.address = address;
    }

    public void run() {
        try (Socket kkSocket = new Socket(address, 8080);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {
            startTime = System.currentTimeMillis();
            out.println(str);

            String receivedData = in.readLine();
            totalTime = System.currentTimeMillis() - startTime;
            out.println("quit");
            TimeKeeper.sumTime += totalTime;
            TimeKeeper.numTimes += 1;
            kkSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        }
    }
}