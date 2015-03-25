import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class ResponseThread extends Thread 
{
	ConnectedClient theClient;
	LinkedList<ConnectedClient> allTheClients;
	
	public ResponseThread(ConnectedClient theClient)
	{
		this.theClient = theClient;
		this.allTheClients = Driver.theClients;
	}
	
	public void run()
	{
		this.theClient.sendMessage("Do you want to share or download?");
		String theAnswer = this.theClient.getMessage();
		this.theClient.sendMessage(theAnswer);
	}
}