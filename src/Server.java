/* 
 * 		DISTRIBUTED SYSTEMS LAB 1
 * Name : Karteek P Gole
 * SID : 1001553522
 * NID : kpg3522
 * 
 * Code partly taken from this website and modified: http://cs.lmu.edu/~ray/notes/javanetexamples/

									SERVER CLASS WITH UI
 * */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class Server {

	
	
	private  JFrame frame = new JFrame("Server UI");
	private static JTextArea messageArea = new JTextArea(8, 60);
	
	
	public Server()
	{
		messageArea.setEditable(false);
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		
	}
	
	 public static void main(String[] args) throws Exception {
	        System.out.println("The wordfinder server is running.");
	        messageArea.append("\n\tHi! The Server is running. Run the Client now. You can Run as many clients as you wish..\n\t");
	        messageArea.append("\n\tWhen the client asks for IP address enter 127.0.0.1. And make sure you \n\topen this window again to view incoming requests\n\t");
	        Server server = new Server();
	        server.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        server.frame.setVisible(true);
	        server.frame.pack();

	        int clientNumber = 1;
	        ServerSocket listener = new ServerSocket(9898);
	        try {
	            while (true) {
	                new Wordfinder(listener.accept(), clientNumber++).start();
	            }
	        } finally {
	            listener.close();
	        }
	    }
	 
	 
	 
	 private static class Wordfinder extends Thread {
	        private Socket socket;
	        private int clientNumber;

	        public Wordfinder(Socket socket, int clientNumber) {
	            this.socket = socket;
	            this.clientNumber = clientNumber;
	            log("\n\tNew connection with client# " + clientNumber + " at " + socket);
	            messageArea.append("\n\tNew connection with client# " + clientNumber + " at " + socket);
	        }
	        
	        public void run() {
	            try {

	                // Decorate the streams so we can send characters
	                // and not just bytes.  Ensure output is flushed
	                // after every newline.
	                BufferedReader in = new BufferedReader(
	                        new InputStreamReader(socket.getInputStream()));
	                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	                // Send a welcome message to the client.
	                out.println("Hello, you are client #" + clientNumber + ".");
	                //messageArea.append("\n\tHello, you are client #" + clientNumber + ".");
	                out.println("Enter a line with only a period (.) to quit\n");
	                
	                
	                

	                // Get messages from the client, search synonyms from files and return them
	                 
	                while (true) {
	                    String input = in.readLine();       

	                    if(input.equals("idiot"))
	        			{
	                    	messageArea.append("\n\tThe word received is:- "+input);

	                    	FileInputStream fin;
	                    	fin = new FileInputStream ("idiot.txt");
	                    	
	                    	out.println(new DataInputStream(fin).readLine());
	    
	            		    fin.close();
	        				
	        			}
	                    
	                    else if(input.equals("clever"))
	                    
	                    {
	                    	messageArea.append("\n\tThe word received is:- "+input);
	          
	                    	FileInputStream fin;
	                    	fin = new FileInputStream ("clever.txt");
	                    	
	                    	out.println(new DataInputStream(fin).readLine());
	            		  
	            		    fin.close();
	                    }
	                    
	                   else if (input == null || input.equals(".")) {
	                      break;
	                    }
	                    
	                    else
	                    {
	                    	messageArea.append("\n\tThe Word received is:- "+input);
	                    	messageArea.append("\n\tThis word is not in the thesaurus");
	                    	out.println("Only enter either 'idiot' or 'clever'");
	                    	
	                    }
	                }
	            } catch (IOException e) {
	                log("Error handling client# " + clientNumber + ": " + e);
	            } finally {
	                try {
	                    socket.close();
	                } catch (IOException e) {
	                    log("Couldn't close a socket, what's going on?");
	                }
	                log("Connection with client# " + clientNumber + " closed");
	            }
	        }

	        /**
	         * Logs a simple message.  In this case we just write the
	         * message to the server applications standard output.
	         */
	        private void log(String message) {
	            System.out.println(message);
	        }
	 }
}
