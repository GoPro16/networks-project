
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ResponseThread extends Thread {
    private Socket kkSocket;
    private boolean done;

    public ResponseThread(Socket kkSocket) {
        this.kkSocket = kkSocket;
        done = false;
    }

    @Override
    public void run() {
        try {
            System.out.println("Client Accepted");
            String inputLine, outputLine;
            PrintWriter out = new PrintWriter(this.kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            while (!kkSocket.isClosed() && ((inputLine = in.readLine()) != null)) {
                outputLine = processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("quit")) {
                    out.close();
                    in.close();
                    kkSocket.close();
                    break;
                }
            }
            System.out.println("Client Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public String processInput(String theInput) {
        String theOutput = null;
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
            theOutput = "Incorrect Input";
            break;
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
            input.close();
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