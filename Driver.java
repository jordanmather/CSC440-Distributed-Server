import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class Driver {
	
	static LinkedList<ConnectedClient> theClients = new LinkedList<ConnectedClient>();
	
	public static void main(String[] args) throws IOException
	{
		ServerSocket ss = new ServerSocket(1234);
		
		while(true)
		{
		System.out.println("Waiting...");
		Socket connection = ss.accept();
		ConnectedClient cc = new ConnectedClient(connection);
		Driver.theClients.add(cc);
		ResponseThread rt = new ResponseThread(cc);
		rt.start();
		System.out.println("Connected!");
		}
		
		
	}

}
