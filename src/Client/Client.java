
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

    public Client(String str) {
        this.str = str;
    }

    public void run() {
        try (Socket kkSocket = new Socket("127.0.0.1", 8080);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {
            startTime = System.currentTimeMillis();

            if (in.readLine().equals("1")) {
                out.println(str);
            }

            String receivedData = in.readLine();
            totalTime = System.currentTimeMillis() - startTime;

            TimeKeeper.sumTime += totalTime;
            TimeKeeper.numTimes += 1;
            System.out.println(TimeKeeper.numTimes);
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