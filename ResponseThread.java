import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;


public class ResponseThread extends Thread
{
	ConnectedClient theClient;
	LinkedList<ConnectedClient> allTheClients;
	private int fileLength = 0;
	private String fileName = "";
	int bytesRead = 0;
	int current = 0;

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

		//share
		if(theAnswer.equalsIgnoreCase("share"))
		{
			//get the length of the file
			//I tried reading from the client, but it would send the picture instead
			//fileLength = Integer.parseInt(this.theClient.getMessage());
			fileLength = Integer.parseInt("60000"); //hard-coded to the size of the picture used for now
			
			//get the name of the file
			//again, tried to read from the client, but no luck, hard-coded for now
			fileName = /*this.theClient.getMessage();*/ "downloaded-file.jpg";

			//receive the file
			try {
				byte[] theByteArray = new byte[fileLength];
				InputStream is = theClient.getTheSocket().getInputStream();
				FileOutputStream fos = new FileOutputStream("src\\myFiles\\" + fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bytesRead = is.read(theByteArray, 0, theByteArray.length);
				current = bytesRead;
				
				do
				{
					bytesRead = is.read(theByteArray, current, (theByteArray.length-current));
					if(bytesRead >= 0)
					{
						current += bytesRead;
					}
				}while(bytesRead > -1);
				
				bos.write(theByteArray, 0, theByteArray.length);
				bos.flush();
				System.out.println("File " + fileName + " downloaded (" + current + " bytes read)");
				if (fos != null) fos.close();
			    if (bos != null) bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
