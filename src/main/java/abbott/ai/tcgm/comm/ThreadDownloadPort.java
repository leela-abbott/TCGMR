

package abbott.ai.tcgm.comm;

import java.io.*;
import java.net.*;

public class ThreadDownloadPort extends TransferThread
{
  private int port;
  private String fileName;
  private ServerSocket serverSocket = null;
  private long restartByte;

  public ThreadDownloadPort(String fileName, long restartByte)
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
	  RandomAccessFile fos = new RandomAccessFile(fileName, "rw");
	  fos.setLength(restartByte);
	  if (restartByte > 0) fos.seek(restartByte);

	  while (cont)
	  {
		byte[] tab = data.read();
		if (tab == null) cont = false;
		else
		{
		  transferredSize += tab.length;
		  fos.write(tab);
		}
	  }
	  fos.close();
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
	return TransferThread.TYPE_DOWNLOAD;
  }
}
