import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class ResponseThread extends Thread
{
	ConnectedClient theClient;
	LinkedList<ConnectedClient> allTheClients;
	private static String theFileName = "";
	private static int theSize = -1;

	public ResponseThread(ConnectedClient theClient)
	{
		this.theClient = theClient;
		this.allTheClients = Driver.theClients;
	}

	public void run()
	{
		//is the client sharing or getting?
		String clientMode = this.theClient.getMessage();

		if(clientMode.equals("share"))
		{
			//Wait for the client to tell us the name of the file he is about to send
			ResponseThread.theFileName = this.theClient.getMessage();
			ResponseThread.theSize = Integer.parseInt(this.theClient.getMessage());
			this.theClient.initBytes(theSize, true);
			System.out.println("Sharing Mode");

			//we need to request bytes from our connectClient that
			//other connected clients need
			//*****Write Code HERE****


		}
		else
		{
			//do get stuff
			System.out.println("Getting Mode");

			//send the client the name of the file
			this.theClient.sendMessage(ResponseThread.theFileName);
			this.theClient.sendMessage("" + ResponseThread.theSize);
			this.theClient.initBytes(theSize, false);

		}

		while(true)
		{
			//wait for client requests for receiving a byte
			String request = this.theClient.getMessage();
			System.out.println(request);
			String whichByte = request.substring(8);
			for(ConnectedClient cc : Driver.theNotBusyClients)
			{
				if(!cc.equals(this.theClient) && cc.hasByte(Integer.parseInt(whichByte)))
				{
					boolean successful = false;
					while(!successful) //loop through until we're able to complete the action
					{
						while(!Driver.searching)//set the boolean so that another thread doesn't try to search through the LinkedList
						{
							Driver.searching = true;
							Driver.theNotBusyClients.remove(cc); //remove and make this client busy
							Driver.theBusyClients.add(cc);
							//share the byte with the guy who asked
							cc.sendMessage(request); //request byte
							System.out.println("Receiving response");
							String response = cc.getMessage();   //receive byte ****Program gets stuck here****
							System.out.println("Sending response");
							this.theClient.sendMessage(response);//send byte to requester
							Driver.theBusyClients.remove(cc);//remove and make this client not busy
							Driver.theNotBusyClients.add(cc);
							Driver.searching = false;
							successful = true;
							System.out.println("End");
						}
					}

				}
			}
		}
	}


}
