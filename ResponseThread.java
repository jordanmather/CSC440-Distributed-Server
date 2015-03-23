import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ResponseThread extends Thread 
{
	private Socket s;
	private PrintWriter output;
	private Scanner input;
	private boolean connection = true;
	public ResponseThread(Socket s)
	{
		this.s = s;
	}
	public void run()
	{
		try
		{
			while(connection)
			{
			input = new Scanner(this.s.getInputStream());
			String req = input.nextLine();
			if(req.equalsIgnoreCase("quit"))
			{
				connection = false;
			}
			
			System.out.println(req);
			output = new PrintWriter(this.s.getOutputStream());
			output.println(req);
			
			output.flush();
			}
			output.close();
		}
		catch(Exception e)
		{
			//im sure nothing will ever go wrong :)
		}
	}
}
