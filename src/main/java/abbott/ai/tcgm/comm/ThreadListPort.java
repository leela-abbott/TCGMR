

package abbott.ai.tcgm.comm;

import java.net.*;

public class ThreadListPort extends TransferThread
{
  private int port;
  private ServerSocket serverSocket = null;
  private String list = "";

  public ThreadListPort()
  {
	super();

	this.port = port;

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

	  while (cont)
	  {
		byte[] tab = data.read();
		if (tab == null) cont = false;
		else
		{
		  transferredSize += tab.length;
		  list += new String(tab);
		}
	  }
	  data.disconnect();
	}
	catch (Exception e)
	{
	  //this.logger.debug(e);
	}
  }

  public int getLocalPort()
  {
	return(port);
  }

  public String getList()
  {
	return(list);
  }

  public int getTransferType()
  {
	return TransferThread.TYPE_LIST;
  }
}
