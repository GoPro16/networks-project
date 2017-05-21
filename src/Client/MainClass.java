package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * Main class for the client side of the program
 */
public class MainClass
{
	public static void main(String[] args)
   {
		boolean done = false;
      
      while(!done)
      {
         System.out.println("1. Host current Date and Time");
         System.out.println("2. Host uptime");
         System.out.println("3. Host memory use");
         System.out.println("4. Host Netstat");
         System.out.println("5. Host current users");
         System.out.println("6. Host running processes");
         System.out.println("7. Quit");
         
         System.out.println("");
         
         System.out.println("Choose an option from the above menu.");
         
         Scanner s = new Scanner(System.in);
         
         int userInpt = 0;
         boolean correctInpt = false;
         
         while(!correctInpt)
         {
            userInpt = s.nextInt();
            
            if((userInpt == 1) || (userInpt == 2) || (userInpt == 3) || (userInpt == 4) || (userInpt == 5) || (userInpt == 6) || (userInpt == 7))
            {
               correctInpt = true;
            }
            else
            {
               System.out.println("Incorrect Input: Please try again");
            }
         }
         
         String str;
         
         switch(userInpt)
         {
            case 1:
               str = "time"; 
               sendRequest(str);
               break;
            case 2:
               str = "uptime";
               sendRequest(str);
               break;
            case 3:
               str = "memory";
               sendRequest(str);
               break;
            case 4:
               str = "netstat";
               sendRequest(str);
               break;
            case 5:
               str = "users";
               sendRequest(str);
               break;
            case 6:
               str = "process";
               sendRequest(str);
               break;
            case 7: 
               done = true;
               break;           
         }
         
      }
   }
   
   public static void sendRequest(String s)
   {   
	 
	    try 
       (
	       Socket kkSocket = new Socket("127.0.0.1", 8080);
	       PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
	       BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
	    ) 
       {
	       if(in.readLine().equals("1"))
          {
              out.println(s); 
          }
          
          String receivedData = in.readLine();
          
          System.out.println(receivedData);
          
          kkSocket.close();
	    } 
       catch (UnknownHostException e) 
       {
	        System.err.println("Don't know about host ");
	        System.exit(1);
	    } 
       catch (IOException e) 
       {
	        System.err.println("Couldn't get I/O for the connection to ");
	        System.exit(1);
	    }   
	}
}
