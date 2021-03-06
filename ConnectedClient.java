import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;


public class ConnectedClient 
{
	private Socket theSocket;
	private PrintWriter output;
	private Scanner input;
	private boolean[] myBytes;
	public ConnectedClient(Socket theSocket)
	{
		try
		{
			this.theSocket = theSocket;
			this.output = new PrintWriter(this.theSocket.getOutputStream(), true);
			this.input = new Scanner(this.theSocket.getInputStream());
		}
		catch(Exception e)
		{
			 //yea right
		}
	}
	
	public boolean hasByte(int pos) //return true if the byte is not -1
	{
		return this.myBytes[pos];
	}
	
	public void initBytes(int size, boolean b)
	{
		this.myBytes = new boolean[size];
		Arrays.fill(this.myBytes, b);
	}
	
	public void displayBytes()
	{
		for(boolean b : this.myBytes)
		{
			System.out.println(b);
		}
	}
	
	public void addedByte(int index)
	{
		this.myBytes[index] = true;
	}
	
	public void sendMessage(String msg)
	{
		this.output.println(msg);
	}
	
	public String getMessage()
	{
		boolean receivedRequest = false;
		String response = "";
		while(!receivedRequest) //force input.nextLine() to wait for a message
		{
			try
			{
				response = this.input.nextLine();
			}
			catch(Exception e)
			{
				//there was an error
			}
			if(!response.equals(""))
			{
				receivedRequest = true;
			}
		}
		return response; 
	}
	
	public int readByte() throws Exception
	{
		return this.theSocket.getInputStream().read();
	}
}
