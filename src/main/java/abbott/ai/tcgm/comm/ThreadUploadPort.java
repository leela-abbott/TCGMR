
package abbott.ai.tcgm.comm;

import java.io.*;
import java.net.*;

public class ThreadUploadPort extends TransferThread
{
  public static final int BUFFER_SIZE = 65536;  // 64KBytes

  private int port;
  private String fileName;
  private ServerSocket serverSocket = null;
  private long restartByte = 0;

  public ThreadUploadPort(String fileName, long restartByte)
  {
	super();

	this.port = port;
	this.fileName = fileName;
	this.restartByte = restartByte;
	this.offsetSize = restartByte;

	try
	{
	  serverSocket = new ServerSocket(0);
	  port = serverSocket.getLocalPort();
	}
	catch(Exception e){}
  }

  public void performTransfer()
  {
	boolean cont = true;

	try
	{
	  BufferedConnection data = new BufferedConnection(serverSocket.accept());
	  RandomAccessFile fis = new RandomAccessFile(fileName, "r");
	  if (restartByte > 0) fis.seek(restartByte);

	  byte[] buffer = new byte[BUFFER_SIZE];
	  while (cont)
	  {
	int lu = fis.read(buffer, 0, BUFFER_SIZE);
	if (lu == -1) cont = false;
	else
	{
	  transferredSize += lu;
	  data.write(buffer, 0, lu);
	}
	  }
	  fis.close();
	  data.disconnect();
	}
	catch (Exception e)
	{
	  //this.logger.debug(e);
	}
  }

  public void closeServerSocket()
  {
	try
	{
	  serverSocket.close();
	}
	catch(Exception e){}
  }

  public int getLocalPort()
  {
	return(port);
  }

  public int getTransferType()
  {
	return TransferThread.TYPE_UPLOAD;
  }
}
