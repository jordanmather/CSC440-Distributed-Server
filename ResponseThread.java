import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ResponseThread extends Thread 
{
	private Socket s;
	private String hello = "<html><body>Hello World</body></html>";
	private String index = "<html><body>Homepage</body></html>";
	private String error = "<html><body>404: Page not found</body></html>";

	public ResponseThread(Socket s)
	{
		this.s = s;
	}
	public void run()
	{
		try
		{
			Scanner input = new Scanner(this.s.getInputStream());
			String req = input.nextLine();
			System.out.println(req);
			String[] parts = req.split(" ");
			String theFunction = parts[0];
			String thePage = parts[1];
			String theProtocol = parts[2];

			System.out.println(thePage);

			PrintWriter output = new PrintWriter(this.s.getOutputStream());
			if(thePage.equalsIgnoreCase("/index.html") || thePage.equalsIgnoreCase("/"))
			{
				output.println(index);
			}
			else if(thePage.equalsIgnoreCase("/hello.html"))
			{
				output.println(hello);
			}
			else
			{
				output.println(error);
			}
			output.flush();
			output.close();
			
		}

		catch(Exception e)
		{
			//I'm sure nothing will ever go wrong
		}


	}
}
