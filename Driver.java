import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Driver {
	//Just added this line
	public static void main(String[] args) throws IOException
	{
		ServerSocket ss = new ServerSocket(1234);
		
		while(true)
		{
		System.out.println("Waiting...");
		Socket connection = ss.accept();
		ResponseThread rt = (new ResponseThread(connection));
		rt.start();
		}
		
		
	}

}
