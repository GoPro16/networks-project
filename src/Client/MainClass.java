import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.Thread;

/*
 * Main class for the client side of the program
 */
public class MainClass {

    private static Client[] clients;
    private static int numUsers;
    private static String str;
    private static boolean done;

    public static void main(String[] args) {
        System.out.println("How many users would you like to simulate at one time?");

        Scanner users = new Scanner(System.in);

        boolean correctUsers = false;
        numUsers = 1;

        while (!correctUsers) {
            if (users.hasNextInt()) {
                numUsers = users.nextInt();
                correctUsers = true;
            } else {
                System.out.print("Input was not a number, please try again");
            }
        } //end while

        if (numUsers == 1)
            singleUser(args[0]);
        else
            multipleUsers(numUsers, args[0]);
    }//end main

    public static void singleUser(String address) {
        long startT = 0;
        try (Socket kkSocket = new Socket(address, 8080);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {
            //Begin I/O Loop
            done = false;
            while (!done) {
                getInput();
                if (done == false) {
                    startT = System.currentTimeMillis();
                    out.println(str);
                    String receivedData = in.readLine();
                    System.out.println("Total Time: " + (System.currentTimeMillis() - startT));
                    System.out.println(receivedData);
                } else {
                    kkSocket.close();
                }
            } //while not done

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O off the connection to ");
            System.exit(1);
        } //catch io
    }//end Singleuser

    public static void multipleUsers(int numUsers, String address) {
        done = false;
        while (!done) {
            getInput();//get Input
            if (done == false) {
                runClients(address);//start the clients
                printResults();//get the results
            } //if the program isn't done
        } //while the program isn't done
    }//end MultipleUsers

    private static void printResults() {
        long avgTime = TimeKeeper.returnAverage();
        System.out.println("Average time for thread execuation was " + avgTime);
        System.out.println("");
        TimeKeeper.reset();
    }

    private static void getInput() {
        System.out.println("1. Host current Date and Time");
        System.out.println("2. Host uptime");
        System.out.println("3. Host memory use");
        System.out.println("4. Host Netstat");
        System.out.println("5. Host current users");
        System.out.println("6. Host running processes");
        System.out.println("7. Quit\n");

        System.out.println("Choose an option from the above menu.");

        Scanner s = new Scanner(System.in);

        int userInpt = 0;
        boolean correctInpt = false;

        while (!correctInpt) {
            try {
                userInpt = s.nextInt();
            } catch (Exception e) {
                userInpt = -2;
                correctInpt = true;
            }
            if ((userInpt == 1) || (userInpt == 2) || (userInpt == 3) || (userInpt == 4) || (userInpt == 5)
                    || (userInpt == 6) || (userInpt == 7)) {
                correctInpt = true;
            } else {
                System.out.println("Incorrect Input: Please try again");
            }
        }
        str = "err";
        switch (userInpt) {
        case 1:
            str = "time";
            break;
        case 2:
            str = "uptime";
            break;
        case 3:
            str = "memory";
            break;
        case 4:
            str = "netstat";
            break;
        case 5:
            str = "users";
            break;
        case 6:
            str = "process";
            break;
        case 7:
            done = true;
            break;
        }//end switch
    }//end getInput

    /**
     * Runs the clients until they all are finished
     */
    private static void runClients(String address) {
        clients = new Client[numUsers];
        int i;
        for (i = 0; i < clients.length; i++) {
            clients[i] = new Client(str, address);
        } //init clients
        for (i = 0; i < clients.length; i++) {
            clients[i].start();
        } //start clients

        boolean running = true;
        while (running) {
            running = false;
            for (Client client : clients) {
                if (client.isAlive()) {
                    running = true;
                } //if one is still running then continue running
            } //for each client
        } //while the threads are running
    }
}//end MainClass
