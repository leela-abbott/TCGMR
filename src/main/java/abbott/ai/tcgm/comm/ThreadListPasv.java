

package abbott.ai.tcgm.comm;

public class ThreadListPasv extends TransferThread
{
  String host;
  int port;
  private String list = "";

  public ThreadListPasv(String host, int port)
  {
	super();

	this.host = host;
	this.port = port;
  }

  public void performTransfer()
  {
	boolean cont = true;
	BufferedConnection data = new BufferedConnection(TCPConnection.CONNECTION_CLIENT_TYPE);
	data.connect(host, port);

	try
	{
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
	}
	catch (Exception e)
	{
	  //this.logger.debug(e);
	}
	data.disconnect();
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
