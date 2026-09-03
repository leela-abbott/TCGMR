

package abbott.ai.tcgm.comm;

import java.io.*;

public class ThreadDownloadPasv extends TransferThread
{
  String host;
  int port;
  String fileName;
  long restartByte = 0;

  public ThreadDownloadPasv(String host, int port, String fileName, long restartByte)
  {
	super();
	this.host = host;
	this.port = port;
	this.fileName = fileName;
	this.restartByte = restartByte;
	this.offsetSize = restartByte;
  }

  public void performTransfer()
  {
	boolean cont = true;
	BufferedConnection data = new BufferedConnection(TCPConnection.CONNECTION_CLIENT_TYPE);
	data.connect(host, port);

	try
	{
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
	}
	catch (Exception e)
	{
	  //this.logger.debug(e);
	}
	data.disconnect();
  }

  public int getTransferType()
  {
	return TransferThread.TYPE_DOWNLOAD;
  }
}
